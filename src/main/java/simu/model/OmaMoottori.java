package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

import java.util.Arrays;

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
                double selfCheckInAsiakas = Math.random();
                if (selfCheckInAsiakas < selfCheckInTodennakoisyys) {
                    getShortestQueue(selfCheckInPisteet).lisaaJonoon(new Asiakas());
                } else {
                    getShortestQueue(checkInPisteet).lisaaJonoon(new Asiakas());
                }
                saapumisprosessi.generoiSeuraava();
                break;
            case CHECK_IN_VALMIS:
                for (Palvelupiste p : checkInPisteet) {
                    if (p.onVarattu() && p.onJonossa()) {
                        if (p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
                            a = p.otaJonosta();
                            getShortestQueue(turvatarkastusPisteet).lisaaJonoon(a);
                            break;
                        }
                    }
                }
                break;
            case SELF_CHECK_IN_VALMIS:
                for (Palvelupiste p : selfCheckInPisteet) {
                    if (p.onVarattu() && p.onJonossa()) {
                        if (p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
                            a = p.otaJonosta();
                            getShortestQueue(turvatarkastusPisteet).lisaaJonoon(a);
                            break;
                        }
                    }
                }
                break;
            case TURVATARKASTUS_VALMIS:
                for (Palvelupiste p : turvatarkastusPisteet) {
                    if (p.onVarattu() && p.onJonossa()) {
                        if (p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
                            a = p.otaJonosta();
                            getShortestQueue(porttiPisteet).lisaaJonoon(a);
                            break;
                        }
                    }
                }
                break;
            case PORTTI_VALMIS:
                for (Palvelupiste p : porttiPisteet) {
                    if (p.onVarattu() && p.onJonossa()) {
                        if (p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
                            a = p.otaJonosta();
                            a.setPoistumisaika(Kello.getInstance().getAika());
                            a.raportti();
                            laskeKeskiProsessiAika(a.getValmiitAsiakkaat(), a.getProsessiAika());
                            break;
                        }
                    }
                }
        }
        paivitaGUI();
    }

    @Override
    protected void yritaCTapahtumat() {
        for (Palvelupiste p : checkInPisteet) {
            if (!p.onVarattu() && p.onJonossa()) {
                p.aloitaPalvelu();

                // Päivitetään Check-in pisteiden palveluajan keskiarvoa
                p.paivitaKeskiPalveluaika(p.getHetkenPalveluaika());
            } else {
                Trace.out(Trace.Level.INFO, p.getNimi() + " piste on varattu tai ei jonoa. Jonon koko: " + p.jononKoko());
            }
        }
        for (Palvelupiste p : selfCheckInPisteet) {
            if (!p.onVarattu() && p.onJonossa()) {
                p.aloitaPalvelu();

                // Päivitetään Self-Checkin pisteiden palveluajan keskiarvoa
                p.paivitaKeskiPalveluaika(p.getHetkenPalveluaika());
            } else {
                Trace.out(Trace.Level.INFO, p.getNimi() + " piste on varattu tai ei jonoa. Jonon koko: " + p.jononKoko());
            }
        }
        for (Palvelupiste p : turvatarkastusPisteet) {
            if (!p.onVarattu() && p.onJonossa()) {
                p.aloitaPalvelu();

                // Päivitetään Turvatarkastus pisteiden palveluajan keskiarvoa
                p.paivitaKeskiPalveluaika(p.getHetkenPalveluaika());
            } else {
                Trace.out(Trace.Level.INFO, p.getNimi() + " Turvatarkastus piste on varattu tai ei jonoa. Jonon koko: " + p.jononKoko());
            }
        }
        for (Palvelupiste p : porttiPisteet) {
            if (!p.onVarattu() && p.onJonossa()) {
                p.aloitaPalvelu();

                // Päivitetään Portti pisteiden palveluajan keskiarvoa
                p.paivitaKeskiPalveluaika(p.getHetkenPalveluaika());
            } else {
                Trace.out(Trace.Level.INFO, p.getNimi() + " Portti piste on varattu tai ei jonoa. Jonon koko: " + p.jononKoko());
            }
        }
        paivitaGUI();
    }

    @Override
    protected void tulokset() {
        //Luodaan tulokset
        int loppuaika = (int)Kello.getInstance().getAika();
        int valmiitAsiakkaat = (int)Asiakas.getValmiitAsiakkaat();
        int meanLapimenoaika = (int)this.prosessiAika / (int)Asiakas.getValmiitAsiakkaat();
        int checkInMeanPalveluaika = (int)checkInPisteet[0].getKeskiPalveluaika();
        int selfCheckInMeanPalveluaika = (int)selfCheckInPisteet[0].getKeskiPalveluaika();
        int turvatarkastusMeanPalveluaika = (int)turvatarkastusPisteet[0].getKeskiPalveluaika();
        int porttiMeanPalveluaika = (int)porttiPisteet[0].getKeskiPalveluaika();

        // Tallennetaan tulokset tietokantaan
        Results results = new Results(loppuaika, valmiitAsiakkaat, meanLapimenoaika, checkInMeanPalveluaika, selfCheckInMeanPalveluaika, turvatarkastusMeanPalveluaika, porttiMeanPalveluaika);
        kontrolleri.persistRes(results);

        //Tulostetaan tulokset konsoliin
        System.out.println("Simulointi päättyi kello " + loppuaika);
        System.out.println("Tulokset ... kesken.....");
        System.out.println("Valmiit asiakkaat: " + valmiitAsiakkaat);
        System.out.println("Keskimääräinen läpimenoaika: " + meanLapimenoaika);
        System.out.println("Check-in pisteiden keskimääräinen palveluaika: " + checkInMeanPalveluaika);
        System.out.println("Self-Check-in pisteiden keskimääräinen palveluaika: " + selfCheckInMeanPalveluaika);
        System.out.println("Turvatarkastus pisteiden keskimääräinen palveluaika: " + turvatarkastusMeanPalveluaika);
        System.out.println("Portti pisteiden keskimääräinen palveluaika: " + porttiMeanPalveluaika);

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