package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtumalista;

public class CheckIn extends Palvelupiste{
    private static int aloitetutPalvelut = 0;
    private static double totalPalveluaika = 0.0;
    private static double keskiPalveluaika = 0.0;

    public CheckIn(String nimi , ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
        super(nimi, generator, tapahtumalista, tyyppi);
    }

    @Override
    public void paivitaKeskiPalveluaika(double palveluaika){
        aloitetutPalvelut++;
        totalPalveluaika += palveluaika;
        keskiPalveluaika = totalPalveluaika / aloitetutPalvelut;
    }

    public static double getKeskiPalveluaika() {
        return keskiPalveluaika;
    }

    public static double getTotalPalveluaika() {
        return totalPalveluaika;
    }

    public static double getKayttoAste() {
        return totalPalveluaika / Kello.getInstance().getAika();
    }

    public static double getLapimeno() {
        return aloitetutPalvelut / Kello.getInstance().getAika();
    }

    @Override
    public void resetPoint(){
        aloitetutPalvelut = 0;
        totalPalveluaika = 0.0;
        keskiPalveluaika = 0.0;
    }
}