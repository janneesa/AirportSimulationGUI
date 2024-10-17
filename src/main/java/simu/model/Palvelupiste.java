package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;

/**
 * <p>A class representing a service point in the simulation model.</p>
 *
 * <p>Each service point has a queue and the ability to service customers that are in the queue.</p>
 */

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public abstract class Palvelupiste {

	private final String nimi;
	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private double hetkenPalveluaika;


	//JonoStartegia strategia; //optio: asiakkaiden järjestys

	private boolean varattu = false;

	/**
	 * The constructor for the class Palvelupiste.
	 *
	 * @param nimi The name of the service point.
	 * @param generator The generator from the eduni.distributions package.
	 * @param tapahtumalista The event list.
	 * @param tyyppi The type of the event.
	 */
	public Palvelupiste(String nimi ,ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.nimi = nimi;
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;

	}


	/**
	 * Adds a customer to the queue of the service point.
	 *
	 * @param a The customer to be added to the queue.
	 */
	public void lisaaJonoon(Asiakas a) {   // Jonon 1. asiakas aina palvelussa
		jono.add(a);

	}

	/**
	 * Finishes the service of the customer that is currently being serviced.
	 *
	 * @return The customer that was removed from the service point.
	 */
	public Asiakas otaJonosta () {
		varattu = false;
		return jono.poll();
	}


	/**
	 * Starts a new service. The customer is in the queue during the service.
	 */
	public void aloitaPalvelu() {  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu: " + this.nimi + ", asiakkaalle " + jono.peek().getId());

		varattu = true;
		double palveluaika = generator.sample();
		hetkenPalveluaika = palveluaika;
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
		jono.peek().setPalvelunPaattymisaika(Kello.getInstance().getAika()+palveluaika);

	}

	/**
	 * Checks if the service point is reserved.
	 *
	 * @return True if the service point is reserved, false otherwise.
	 */
	public boolean onVarattu(){
		return varattu;
	}


	/**
	 * Checks if there are customers in the queue.
	 *
	 * @return True if there are customers in the queue, false otherwise.
	 */
	public boolean onJonossa(){
		return jono.size() != 0;
	}

	/**
	 * Returns the size of the queue.
	 *
	 * @return The size of the queue.
	 */
	public int jononKoko(){
		return jono.size();
	}

	/**
	 * Returns the name of the service point.
	 *
	 * @return The name of the service point.
	 */
	public String getNimi() {
		return nimi;
	}

	/**
	 * Returns the queue of customers of the service point.
	 *
	 * @return The queue of the service point.
	 */
	public LinkedList<Asiakas> getJono() {
		return jono;
	}

	/**
	 * Returns the current service time.
	 *
	 * @return The current service time.
	 */
	public double getHetkenPalveluaika() {
		return hetkenPalveluaika;
	}

	/**
	 * Updates the average service time.
	 *
	 * @param palveluaika The service time to be added to the average service time.
	 */
	public abstract void paivitaKeskiPalveluaika(double palveluaika);

	/**
	 * Resets the service point.
	 */
	public void resetPoint() {
	}
}