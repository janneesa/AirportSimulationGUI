package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Tapahtumalista;

public class Portti extends Palvelupiste {
    private static int aloitetutPalvelut = 0;
    private static double totalPalveluaika = 0.0;
    private static double keskiPalveluaika = 0.0;

    public Portti(String nimi , ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
        super(nimi, generator, tapahtumalista, tyyppi);
    }

    @Override
    public void paivitaKeskiPalveluaika(double palveluaika){
        aloitetutPalvelut++;
        totalPalveluaika += palveluaika;
        keskiPalveluaika = totalPalveluaika / aloitetutPalvelut;
        System.out.println();
        System.out.println("Keskimääräinen Portti pisteiden palveluaika: " + keskiPalveluaika);
        System.out.println();
    }
}