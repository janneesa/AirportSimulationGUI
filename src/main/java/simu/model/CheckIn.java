package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtumalista;

/**
 * <p>Represents the check-in point of the simulation model.</p>
 *
 */

public class CheckIn extends Palvelupiste{
    private static int aloitetutPalvelut = 0;
    private static double totalPalveluaika = 0.0;
    private static double keskiPalveluaika = 0.0;
    private static double kayttoAste = 0.0;
    private static double lapimeno = 0.0;

    /**
     * <p>Constructor for the check-in point of the simulation model.</p>
     *
     * @param nimi The name of the check-in point.
     * @param generator The generator for the check-in point.
     * @param tapahtumalista The event list for the check-in point.
     * @param tyyppi The type of the event for the check-in point.
     */
    public CheckIn(String nimi , ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
        super(nimi, generator, tapahtumalista, tyyppi);
    }

    /**
     * <p>Updates the average service time.</p>
     *
     * @param palveluaika The service time.
     */
    @Override
    public void paivitaKeskiPalveluaika(double palveluaika){
        aloitetutPalvelut++;
        totalPalveluaika += palveluaika;
        keskiPalveluaika = totalPalveluaika / aloitetutPalvelut;
    }

    /**
     * Returns the number of services that have been started.
     *
     * @return the number of services that have been started.
     */
    public static int getAloitetutPalvelut() {
        return aloitetutPalvelut;
    }

    /**
     * Returns the average service time.
     *
     * @return the average service time.
     */
    public static double getKeskiPalveluaika() {
        return keskiPalveluaika;
    }

    /**
     * Returns the total service time.
     *
     * @return the total service time.
     */
    public static double getTotalPalveluaika() {
        return totalPalveluaika;
    }

    /**
     * Returns the utilization rate.
     *
     * @return the utilization rate.
     */
    public static double getKayttoAste() {
        if (Kello.getInstance().getAika() == 0 || totalPalveluaika == 0) {
            return 0.0;
        }else {
            kayttoAste = totalPalveluaika / Kello.getInstance().getAika();
            return kayttoAste;
        }
    }

    /**
     * Returns the throughput.
     *
     * @return the throughput.
     */
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