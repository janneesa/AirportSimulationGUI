package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OmaMoottori extends Moottori {

    private Saapumisprosessi saapumisprosessi;

    // Todennäköisyys, että asiakas menee itsepalvelu check-iniin
    private double selfCheckInTodennakoisyys = 0.5; // 50%

    private Palvelupiste[] checkInPisteet;
    private Palvelupiste[] selfCheckInPisteet;
    private Palvelupiste[] turvatarkastusPisteet;
    private Palvelupiste[] porttiPisteet;

    // Palvelupisteiden palveluajat
    private int meanPalveluaika;
    private int variancePalveluaika;

    // Saapumisprosessin saapumisväli
    private int meanSaapumisvali;
    private int varianceSaapumisvali;

    // Palavelupisteiden asiakasmäärät
    private int checkInAsiakasMaara;
    private int selfCheckInAsiakasMaara;
    private int turvatarkastusAsiakasMaara;
    private int porttiAsiakasMaara;

    private double prosessiAika = 0.0;

    public OmaMoottori(IKontrolleriForM kontrolleri, int checkInKoko, int selfCheckInKoko, int turvatarkastusKoko, int porttiKoko, int meanPalveluaika, int variancePalveluaika, int meanSaapumisvali, int varianceSaapumisvali, double selfCheckInTodennakoisyys) {
        super(kontrolleri);
        this.meanPalveluaika = meanPalveluaika;
        this.variancePalveluaika = variancePalveluaika;
        this.meanSaapumisvali = meanSaapumisvali;
        this.varianceSaapumisvali = varianceSaapumisvali;
        this.selfCheckInTodennakoisyys = selfCheckInTodennakoisyys;
        System.out.println(selfCheckInTodennakoisyys);
        checkInPisteet = new Palvelupiste[checkInKoko];
        selfCheckInPisteet = new Palvelupiste[selfCheckInKoko];
        turvatarkastusPisteet = new Palvelupiste[turvatarkastusKoko];
        porttiPisteet = new Palvelupiste[porttiKoko];


        // Luo check-in pisteet
        for (int i = 0; i < checkInKoko; i++) {
            checkInPisteet[i] = new CheckIn("Check-in " + (i + 1), new Normal(meanPalveluaika, variancePalveluaika), tapahtumalista, TapahtumanTyyppi.CHECK_IN_VALMIS);
        }

        // Luo self-check-in pisteet
        for (int i = 0; i < selfCheckInKoko; i++) {
            selfCheckInPisteet[i] = new SelfCheckIn("Self-check-in " + (i + 1), new Normal(meanPalveluaika, variancePalveluaika), tapahtumalista, TapahtumanTyyppi.SELF_CHECK_IN_VALMIS);
        }

        // Luo turvatarkastus pisteet
        for (int i = 0; i < turvatarkastusKoko; i++) {
            turvatarkastusPisteet[i] = new Turvatarkastus("Turvatarkastus " + (i + 1), new Normal(meanPalveluaika, variancePalveluaika), tapahtumalista, TapahtumanTyyppi.TURVATARKASTUS_VALMIS);
        }

        // Luo portti pisteet
        for (int i = 0; i < porttiKoko; i++) {
            porttiPisteet[i] = new Portti("Portti " + (i + 1), new Normal(meanPalveluaika, variancePalveluaika), tapahtumalista, TapahtumanTyyppi.PORTTI_VALMIS);
        }

        saapumisprosessi = new Saapumisprosessi(new Negexp(meanSaapumisvali, varianceSaapumisvali), tapahtumalista, TapahtumanTyyppi.ARRIVAL);
    }

    @Override
    protected void alustukset() {
        saapumisprosessi.generoiSeuraava();
    }

    @Override
    protected void suoritaTapahtuma(Tapahtuma t) {
        Asiakas a;
        switch ((TapahtumanTyyppi) t.getTyyppi()) {
            case ARRIVAL:
                if (Math.random() < selfCheckInTodennakoisyys) {
                    getShortestQueue(selfCheckInPisteet).lisaaJonoon(new Asiakas());
                } else {
                    getShortestQueue(checkInPisteet).lisaaJonoon(new Asiakas());
                }
                saapumisprosessi.generoiSeuraava();
                break;
            case CHECK_IN_VALMIS:
                siirraAsiaks(checkInPisteet, turvatarkastusPisteet);
                break;
            case SELF_CHECK_IN_VALMIS:
                siirraAsiaks(selfCheckInPisteet, turvatarkastusPisteet);
                break;
            case TURVATARKASTUS_VALMIS:
                siirraAsiaks(turvatarkastusPisteet, porttiPisteet);
                break;
            case PORTTI_VALMIS:
                for (Palvelupiste p : porttiPisteet) {
                    if (p.onVarattu() && p.onJonossa() && p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
                        a = p.otaJonosta();
                        a.setPoistumisaika(Kello.getInstance().getAika());
                        a.raportti();
                        laskeKeskiProsessiAika(a.getValmiitAsiakkaat(), a.getProsessiAika());
                        break;
                    }
                }
                break;
        }
        paivitaGUI();
    }

    private void siirraAsiaks(Palvelupiste[] mista, Palvelupiste[] minne) {
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
        System.out.println("Simulointi päättyi kello (T): " + Kello.getInstance().getAika());
        System.out.println("Saapuneet asiakkaat (A): " + Asiakas.getSaapuneetAsiakkaat());
        System.out.println("Valmiit asiakkaat (C): " + Asiakas.getValmiitAsiakkaat());
        System.out.println("Keskimääräinen läpimenoaika: " + Asiakas.getKeskiViipyminen());
        System.out.println("Check-in keski palveluaika (S): " + CheckIn.getKeskiPalveluaika() + ", koko palveluaika (B): " + CheckIn.getTotalPalveluaika() + ", käyttöaste (U): " + CheckIn.getKayttoAste() + ", suorituskyky (X): " + CheckIn.getLapimeno());
        System.out.println("Self-Check-in keski palveluaika (S): " + SelfCheckIn.getKeskiPalveluaika() + ", koko palveluaika (B): " + SelfCheckIn.getTotalPalveluaika() + ", käyttöaste (U): " + SelfCheckIn.getKayttoAste() + ", suorituskyky (X): " + SelfCheckIn.getLapimeno());
        System.out.println("Turvatarkastus keski palveluaika (S): " + Turvatarkastus.getKeskiPalveluaika() + ", koko palveluaika (B): " + Turvatarkastus.getTotalPalveluaika() + ", käyttöaste (U): " + Turvatarkastus.getKayttoAste() + ", suorituskyky (X): " + Turvatarkastus.getLapimeno());
        System.out.println("Portti keski palveluaika (S): " + Portti.getKeskiPalveluaika() + ", koko palveluaika (B): " + Portti.getTotalPalveluaika() + ", käyttöaste (U): " + Portti.getKayttoAste() + ", suorituskyky (X): " + Portti.getLapimeno());

        kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
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

        if (kayttoaste > 100) {
            return 100;
        }
        return ((double) varatut / pisteet.length) * 100;
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

    private void laskeKeskiProsessiAika(double valmiitAsiakkaat, double prosessiAika) {
        this.prosessiAika += prosessiAika;
        double keskiProsessiAika = this.prosessiAika / valmiitAsiakkaat;
        System.out.println();
        System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti: " + keskiProsessiAika);
        System.out.println();
    }

}