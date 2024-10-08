package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OmaMoottori extends Moottori {

    private Saapumisprosessi saapumisprosessi;
    private double selfCheckInTodennakoisyys = 0.5;
    private Palvelupiste[] checkInPisteet, selfCheckInPisteet, turvatarkastusPisteet, porttiPisteet;
    private int meanCheckIn, varianceCheckIn, meanSelfCheckIn, varianceSelfCheckIn, meanTurvatarkastus, varianceTurvatarkastus, meanPortti, variancePortti;
    private int meanSaapumisvali, varianceSaapumisvali;
    private int checkInAsiakasMaara, selfCheckInAsiakasMaara, turvatarkastusAsiakasMaara, porttiAsiakasMaara;

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
    }

    @Override
    protected void alustukset() {
        saapumisprosessi.generoiSeuraava();
    }

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

    private void handleArrival() {
        if (Math.random() < selfCheckInTodennakoisyys) {
            getShortestQueue(selfCheckInPisteet).lisaaJonoon(new Asiakas());
        } else {
            getShortestQueue(checkInPisteet).lisaaJonoon(new Asiakas());
        }
        saapumisprosessi.generoiSeuraava();
    }

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

    private void siirraAsiakas(Palvelupiste[] mista, Palvelupiste[] minne) {
        for (Palvelupiste p : mista) {
            if (p.onVarattu() && p.onJonossa() && p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
                Asiakas a = p.otaJonosta();
                getShortestQueue(minne).lisaaJonoon(a);
                break;
            }
        }
    }

    @Override
    protected void yritaCTapahtumat() {
        prosessoiTapahtumaJonot(checkInPisteet);
        prosessoiTapahtumaJonot(selfCheckInPisteet);
        prosessoiTapahtumaJonot(turvatarkastusPisteet);
        prosessoiTapahtumaJonot(porttiPisteet);
        paivitaGUI();
    }

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

    @Override
    protected void tulokset() {
        Results results = new Results(
                Kello.getInstance().getAika(),
                Asiakas.getValmiitAsiakkaat(),
                Asiakas.getKeskiViipyminen(),
                CheckIn.getKeskiPalveluaika(),
                CheckIn.getTotalPalveluaika(),
                CheckIn.getKayttoAste(),
                CheckIn.getLapimeno(),
                SelfCheckIn.getKeskiPalveluaika(),
                SelfCheckIn.getTotalPalveluaika(),
                SelfCheckIn.getKayttoAste(),
                SelfCheckIn.getLapimeno(),
                Turvatarkastus.getKeskiPalveluaika(),
                Turvatarkastus.getTotalPalveluaika(),
                Turvatarkastus.getKayttoAste(),
                Turvatarkastus.getLapimeno(),
                Portti.getKeskiPalveluaika(),
                Portti.getTotalPalveluaika(),
                Portti.getKayttoAste(),
                Portti.getLapimeno()
        );

        kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
        kontrolleri.setResults(results);
        kontrolleri.showTallennaButton();
    }

    private Palvelupiste getShortestQueue(Palvelupiste[] pisteet) {
        Palvelupiste shortest = pisteet[0];
        for (Palvelupiste p : pisteet) {
            if (p.jononKoko() < shortest.jononKoko()) {
                shortest = p;
            }
        }
        return shortest;
    }

    private int getAsiakasMaara(Palvelupiste[] pisteet) {
        int jononKoko = 0;
        for (Palvelupiste p : pisteet) {
            jononKoko += p.jononKoko();
        }
        return jononKoko;
    }

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

}