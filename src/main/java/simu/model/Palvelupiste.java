package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;

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


	public Palvelupiste(String nimi ,ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.nimi = nimi;
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;

	}


	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);

	}


	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}


	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu: " + this.nimi + ", asiakkaalle " + jono.peek().getId());

		varattu = true;
		double palveluaika = generator.sample();
		hetkenPalveluaika = palveluaika;
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
		jono.peek().setPalvelunPaattymisaika(Kello.getInstance().getAika()+palveluaika);

	}



	public boolean onVarattu(){
		return varattu;
	}



	public boolean onJonossa(){
		return jono.size() != 0;
	}


	public int jononKoko(){
		return jono.size();
	}


	public String getNimi() {
		return nimi;
	}


	public LinkedList<Asiakas> getJono() {
		return jono;
	}

	public double getHetkenPalveluaika() {
		return hetkenPalveluaika;
	}

	public abstract void paivitaKeskiPalveluaika(double palveluaika);

	public void resetPoint() {
	}
}