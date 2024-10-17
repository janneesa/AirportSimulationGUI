package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtumalista;

/**
 * <p>Represents the self-check-in point of the simulation model.</p>
 *
 */

public class SelfCheckIn extends Palvelupiste{
    private static int aloitetutPalvelut = 0;
    private static double totalPalveluaika = 0.0;
    private static double keskiPalveluaika = 0.0;
    private static double kayttoAste = 0.0;
    private static double lapimeno = 0.0;

    public SelfCheckIn(String nimi , ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
        super(nimi, generator, tapahtumalista, tyyppi);
    }

    @Override
    public void paivitaKeskiPalveluaika(double palveluaika){
        aloitetutPalvelut++;
        totalPalveluaika += palveluaika;
        keskiPalveluaika = totalPalveluaika / aloitetutPalvelut;
    }

    public static int getAloitetutPalvelut() {
        return aloitetutPalvelut;
    }

    public static double getKeskiPalveluaika() {
        return keskiPalveluaika;
    }

    public static double getTotalPalveluaika() {
        return totalPalveluaika;
    }

    public static double getKayttoAste() {
        if (Kello.getInstance().getAika() == 0 || totalPalveluaika == 0) {
            return 0.0;
        }else {
            kayttoAste = totalPalveluaika / Kello.getInstance().getAika();
            return kayttoAste;
        }
    }

    public static double getLapimeno() {
        if (Kello.getInstance().getAika() == 0 || aloitetutPalvelut == 0) {
            return 0.0;
        } else {
            lapimeno = aloitetutPalvelut / Kello.getInstance().getAika();
            return lapimeno;
        }
    }

    @Override
    public void resetPoint(){
        aloitetutPalvelut = 0;
        totalPalveluaika = 0.0;
        keskiPalveluaika = 0.0;
    }
}