package simu.model;

import controller.IKontrolleriForM;
import eduni.distributions.RandomGenerator;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

/**
 * <p>Represents the simulation engine.</p>
 *
 * <p>Has the main simulation logic, which is executed in the run() method.</p>
 */

public class OmaMoottori extends Moottori {

    private Saapumisprosessi saapumisprosessi;
    private double selfCheckInTodennakoisyys;
    private Palvelupiste[] checkInPisteet, selfCheckInPisteet, turvatarkastusPisteet, porttiPisteet;
    private int meanCheckIn, varianceCheckIn, meanSelfCheckIn, varianceSelfCheckIn, meanTurvatarkastus, varianceTurvatarkastus, meanPortti, variancePortti;
    private int meanSaapumisvali, varianceSaapumisvali;
    private int checkInAsiakasMaara, selfCheckInAsiakasMaara, turvatarkastusAsiakasMaara, porttiAsiakasMaara;
    private Normal selfCheckInGeneraattori;

    /**
     * <p>Constructor for the simulation engine.</p>
     *
     * <p>Creates the needed amount of service points of each type.</p>
     *
     * <p>Initializes the first arrival process.</p>
     *
     * @param kontrolleri The controller for the simulation engine
     * @param checkInKoko The amount of the check-in points
     * @param selfCheckInKoko The amount of the self-check-in points
     * @param turvatarkastusKoko The amount of the security check points
     * @param porttiKoko The amount of gates
     * @param meanCheckIn The mean service time for the check-in points
     * @param varianceCheckIn The variance of the service time for the check-in points
     * @param meanSelfCheckIn The mean service time for the self-check-in points
     * @param varianceSelfCheckIn The variance of the service time for the self-check-in points
     * @param meanTurvatarkastus The mean service time for the security check points
     * @param varianceTurvatarkastus The variance of the service time for the security check points
     * @param meanPortti The mean service time for the gates
     * @param variancePortti The variance of the service time for the gates
     * @param meanSaapumisvali The mean arrival interval
     * @param varianceSaapumisvali The variance of the arrival interval
     * @param selfCheckInTodennakoisyys The probability of the self-check-in
     */
    public OmaMoottori(IKontrolleriForM kontrolleri, int checkInKoko, int selfCheckInKoko, int turvatarkastusKoko, int porttiKoko, int meanCheckIn, int varianceCheckIn, int meanSelfCheckIn, int varianceSelfCheckIn, int meanTurvatarkastus, int varianceTurvatarkastus, int meanPortti, int variancePortti, int meanSaapumisvali, int varianceSaapumisvali, double selfCheckInTodennakoisyys) {
        super(kontrolleri);
        this.meanCheckIn = meanCheckIn;
        this.varianceCheckIn = varianceCheckIn;
        this.meanSelfCheckIn = meanSelfCheckIn;
        this.varianceSelfCheckIn = varianceSelfCheckIn;
        this.meanTurvatarkastus = meanTurvatarkastus;
        this.varianceTurvatarkastus = varianceTurvatarkastus;
        this.meanPortti = meanPortti;
        this.variancePortti = variancePortti;
        this.meanSaapumisvali = meanSaapumisvali;
        this.varianceSaapumisvali = varianceSaapumisvali;
        this.selfCheckInTodennakoisyys = selfCheckInTodennakoisyys;

        checkInPisteet = new Palvelupiste[checkInKoko];
        selfCheckInPisteet = new Palvelupiste[selfCheckInKoko];
        turvatarkastusPisteet = new Palvelupiste[turvatarkastusKoko];
        porttiPisteet = new Palvelupiste[porttiKoko];

        for (int i = 0; i < checkInKoko; i++) {
            checkInPisteet[i] = new CheckIn("Check-in " + (i + 1), new Normal(meanCheckIn, varianceCheckIn), tapahtumalista, TapahtumanTyyppi.CHECK_IN_VALMIS);
        }
        for (int i = 0; i < selfCheckInKoko; i++) {
            selfCheckInPisteet[i] = new SelfCheckIn("Self-check-in " + (i + 1), new Normal(meanSelfCheckIn, varianceSelfCheckIn), tapahtumalista, TapahtumanTyyppi.SELF_CHECK_IN_VALMIS);
        }
        for (int i = 0; i < turvatarkastusKoko; i++) {
            turvatarkastusPisteet[i] = new Turvatarkastus("Turvatarkastus " + (i + 1), new Normal(meanTurvatarkastus, varianceTurvatarkastus), tapahtumalista, TapahtumanTyyppi.TURVATARKASTUS_VALMIS);
        }
        for (int i = 0; i < porttiKoko; i++) {
            porttiPisteet[i] = new Portti("Portti " + (i + 1), new Normal(meanPortti, variancePortti), tapahtumalista, TapahtumanTyyppi.PORTTI_VALMIS);
        }

        saapumisprosessi = new Saapumisprosessi(new Negexp(meanSaapumisvali, varianceSaapumisvali), tapahtumalista, TapahtumanTyyppi.ARRIVAL);

        selfCheckInGeneraattori = new Normal(50,20);
    }

    @Override
    protected void alustukset() {
        saapumisprosessi.generoiSeuraava();
    }

    /**
     * <p>Executes the simulation logic.</p>
     *
     * <p>Checks the type of event and moves the customer to the correct type of service point accordingly.</p>
     * <p>Updates the GUI.</p>
     *
     * @param t
     */
    @Override
    protected void suoritaTapahtuma(Tapahtuma t) {
        switch ((TapahtumanTyyppi) t.getTyyppi()) {
            case ARRIVAL:
                handleArrival();
                break;
            case CHECK_IN_VALMIS:
                siirraAsiakas(checkInPisteet, turvatarkastusPisteet);
                break;
            case SELF_CHECK_IN_VALMIS:
                siirraAsiakas(selfCheckInPisteet, turvatarkastusPisteet);
                break;
            case TURVATARKASTUS_VALMIS:
                siirraAsiakas(turvatarkastusPisteet, porttiPisteet);
                break;
            case PORTTI_VALMIS:
                handlePorttiValmis();
                break;
        }
        paivitaGUI();
    }

    /**
     * <p>Handles the arrival of a customer.</p>
     *
     * <p>Uses the selfCheckInTodennakoisyys variable to draw if the customer goes to self check-in or check-in.</p>
     */
    private void handleArrival() {
        if (Math.random() * 100 < selfCheckInTodennakoisyys) {
            getShortestQueue(selfCheckInPisteet).lisaaJonoon(new Asiakas());
        } else {
            getShortestQueue(checkInPisteet).lisaaJonoon(new Asiakas());
        }
        saapumisprosessi.generoiSeuraava();
    }

    /**
     * <p>Handles the finish of a customers service through the system.</p>
     *
     * <p>Sets the customers departure time and prints its report.</p>
     */
    private void handlePorttiValmis() {
        for (Palvelupiste p : porttiPisteet) {
            if (p.onVarattu() && p.onJonossa() && p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
                Asiakas a = p.otaJonosta();
                a.setPoistumisaika(Kello.getInstance().getAika());
                a.raportti();
                break;
            }
        }
    }

    /**
     * <p>Handles the movement of a customer from one service point to another.</p>
     *
     * <p>Gets the shortest queue from the destination and moves the customer to it.</p>
     *
     * @param mista The source service points
     * @param minne The destination service points
     */
    private void siirraAsiakas(Palvelupiste[] mista, Palvelupiste[] minne) {
        for (Palvelupiste p : mista) {
            if (p.onVarattu() && p.onJonossa() && p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
                Asiakas a = p.otaJonosta();
                getShortestQueue(minne).lisaaJonoon(a);
                break;
            }
        }
    }

    /**
     * <p>Processes the events in the service points.</p>
     * <p>Updates the GUI.</p>
     */
    @Override
    protected void yritaCTapahtumat() {
        prosessoiTapahtumaJonot(checkInPisteet);
        prosessoiTapahtumaJonot(selfCheckInPisteet);
        prosessoiTapahtumaJonot(turvatarkastusPisteet);
        prosessoiTapahtumaJonot(porttiPisteet);
        paivitaGUI();
    }

    /**
     * <p>Processes the service points queues.</p>
     *
     * <p>Starts the service for the first customer in the queue if the service point is not reserved.</p>
     *
     * @param pisteet The service points to process
     */
    private void prosessoiTapahtumaJonot(Palvelupiste[] pisteet) {
        for (Palvelupiste p : pisteet) {
            if (!p.onVarattu() && p.onJonossa()) {
                p.aloitaPalvelu();
                p.paivitaKeskiPalveluaika(p.getHetkenPalveluaika());
            } else {
                Trace.out(Trace.Level.INFO, p.getNimi() + " piste on varattu tai ei jonoa. Jonon koko: " + p.jononKoko());
            }
        }
    }

    /**
     * Gets the results of the simulation, passes them to the controller and toggles the save button.
     */
    @Override
    protected void tulokset() {
        Results results = new Results(
                Kello.getInstance().getAika(),
                Asiakas.getValmiitAsiakkaat(),
                Asiakas.getKeskiViipyminen(),
                CheckIn.getAloitetutPalvelut(),
                CheckIn.getKeskiPalveluaika(),
                CheckIn.getTotalPalveluaika(),
                CheckIn.getKayttoAste() / checkInPisteet.length,
                CheckIn.getLapimeno(),
                SelfCheckIn.getAloitetutPalvelut(),
                SelfCheckIn.getKeskiPalveluaika(),
                SelfCheckIn.getTotalPalveluaika(),
                SelfCheckIn.getKayttoAste() / selfCheckInPisteet.length,
                SelfCheckIn.getLapimeno(),
                Turvatarkastus.getAloitetutPalvelut(),
                Turvatarkastus.getKeskiPalveluaika(),
                Turvatarkastus.getTotalPalveluaika(),
                Turvatarkastus.getKayttoAste() / turvatarkastusPisteet.length,
                Turvatarkastus.getLapimeno(),
                Portti.getAloitetutPalvelut(),
                Portti.getKeskiPalveluaika(),
                Portti.getTotalPalveluaika(),
                Portti.getKayttoAste() / porttiPisteet.length,
                Portti.getLapimeno()
        );

        kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
        kontrolleri.setResults(results);
        kontrolleri.showTallennaButton();
    }

    /**
     * Gets the service point with the shortest queue.
     *
     * @param pisteet The service points to check
     * @return The service point with the shortest queue
     */
    private Palvelupiste getShortestQueue(Palvelupiste[] pisteet) {
        Palvelupiste shortest = pisteet[0];
        for (Palvelupiste p : pisteet) {
            if (p.jononKoko() < shortest.jononKoko()) {
                shortest = p;
            }
        }
        return shortest;
    }

    /**
     * Gets the amount of customers in service and the queue of the service point type.
     *
     * @param pisteet The service point type to check
     * @return
     */
    private int getAsiakasMaara(Palvelupiste[] pisteet) {
        int jononKoko = 0;
        for (Palvelupiste p : pisteet) {
            jononKoko += p.jononKoko();
        }
        return jononKoko;
    }

    /**
     * Gets the usage rate of the service point type.
     *
     * @param pisteet The service point type to check
     * @return
     */
    private double getKayttoaste(Palvelupiste[] pisteet) {
        int varatut = 0;
        for (Palvelupiste p : pisteet) {
            if (p.jononKoko() > 0) {
                varatut += p.jononKoko();
            }
        }
        double kayttoaste = ((double) varatut / pisteet.length) * 100;
        return Math.min(kayttoaste, 100);
    }

    /**
     * Updates the GUI with the current state of the simulation.
     */
    private void paivitaGUI() {
        checkInAsiakasMaara = getAsiakasMaara(checkInPisteet);
        kontrolleri.visualisoiAsiakas(1, checkInAsiakasMaara, getKayttoaste(checkInPisteet), checkInPisteet.length);

        selfCheckInAsiakasMaara = getAsiakasMaara(selfCheckInPisteet);
        kontrolleri.visualisoiAsiakas(2, selfCheckInAsiakasMaara, getKayttoaste(selfCheckInPisteet), selfCheckInPisteet.length);

        turvatarkastusAsiakasMaara = getAsiakasMaara(turvatarkastusPisteet);
        kontrolleri.visualisoiAsiakas(3, turvatarkastusAsiakasMaara, getKayttoaste(turvatarkastusPisteet), turvatarkastusPisteet.length);

        porttiAsiakasMaara = getAsiakasMaara(porttiPisteet);
        kontrolleri.visualisoiAsiakas(4, porttiAsiakasMaara, getKayttoaste(porttiPisteet), porttiPisteet.length);
    }

    /**
     * Resets the service points to their initial state.
     */
    public void reset() {
        for (Palvelupiste p : checkInPisteet) {
            p.resetPoint();
        }
        for (Palvelupiste p : selfCheckInPisteet) {
            p.resetPoint();
        }
        for (Palvelupiste p : turvatarkastusPisteet) {
            p.resetPoint();
        }
        for (Palvelupiste p : porttiPisteet) {
            p.resetPoint();
        }
    }

}