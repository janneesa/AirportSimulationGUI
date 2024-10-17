package simu.model;

import simu.framework.*;

/**
 * <p>Represents a customer in the simulation.</p>
 *
 * <p>Tracks the arrival, departure, and service end times of the customer.</p>
 *
 * <p>Static variables for tracking total customers arrived and serviced.</p>
 */

public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private double palvelunPaattymisaika;
	private int id;
	private static int i = 1;
	private static double sum = 0;
	private static double valmiitAsiakkaat = 0;
	private static double saapuneetAsiakkaat = 0;

	/**
	 * <p>Constructor for the customer class.</p>
	 *
	 * <p>Initializes the customer with an automatically generated id and arrival time.</p>
	 */
	public Asiakas(){
		id = i++;
		saapuneetAsiakkaat++;

		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+saapumisaika);
	}

	/**
	 * Getter for the departure time of the customer.
	 *
	 * @return Departure time of the customer.
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/**
	 * Setter for the departure time of the customer.
	 *
	 * @param poistumisaika Departure time of the customer.
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/**
	 * Getter for the arrival time of the customer.
	 *
	 * @return Arrival time of the customer.
	 */
	public double getSaapumisaika() {
		return saapumisaika;
	}

	/**
	 * Setter for the arrival time of the customer.
	 *
	 * @param saapumisaika Arrival time of the customer.
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	/**
	 * Getter for the service end time of the customer.
	 *
	 * @return Service end time of the customer.
	 */
	public double getPalvelunPaattymisaika() {
		return palvelunPaattymisaika;
	}

	/**
	 * Setter for the service end time of the customer.
	 *
	 * @param palvelunPaattymisaika Service end time of the customer.
	 */
	public void setPalvelunPaattymisaika(double palvelunPaattymisaika) {
		this.palvelunPaattymisaika = palvelunPaattymisaika;
	}

	/**
	 * Getter for the total number of customers that have arrived.
	 *
	 * @return Total number of customers that have arrived.
	 */
	public static double getValmiitAsiakkaat() {
		return valmiitAsiakkaat;
	}

	/**
	 * Getter for the id of the customer.
	 *
	 * @return Id of the customer.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter for the service time of the customer.
	 *
	 * @return Service time of the customer.
	 */
	public double getProsessiAika() {
		return poistumisaika-saapumisaika;
	}

	/**
	 * <p>Prints a report of the customer's arrival and departure times.</p>
	 *
	 * <p>Calculates and prints the mean service time of all customers.</p>
	 */
	public void raportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		valmiitAsiakkaat++;
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/valmiitAsiakkaat;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}

	/**
	 * <p>Calculates the mean service time of all customers.</p>
	 *
	 * @return Mean service time of all customers.
	 */
	public static double getKeskiViipyminen(){
		if (valmiitAsiakkaat == 0 || sum == 0){
			return 0.0;
		} else {
			return sum/valmiitAsiakkaat;
		}
	}

	/**
	 * <p>Getter for the total number of customers that have arrived.</p>
	 *
	 * @return Total number of customers that have arrived.
	 */
	public static double getSaapuneetAsiakkaat() {
		return saapuneetAsiakkaat;
	}

	/**
	 * <p>Resets the static variables tracking the total number of customers arrived and serviced.</p>
	 *
	 * <p>Used when resetting the simulation.</p>
	 */
	public static void resetAsiakkaat() {
		i = 1;
		sum = 0;
		valmiitAsiakkaat = 0;
		saapuneetAsiakkaat = 0;
	}

}