package simu.model;

import simu.framework.*;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private double palvelunPaattymisaika;
	private int id;
	private static int i = 1;
	private static double sum = 0;
	private static double valmiitAsiakkaat = 0;
	private static double saapuneetAsiakkaat = 0;

	public Asiakas(){
		id = i++;
		saapuneetAsiakkaat++;

		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+saapumisaika);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	public double getPalvelunPaattymisaika() {
		return palvelunPaattymisaika;
	}

	public void setPalvelunPaattymisaika(double palvelunPaattymisaika) {
		this.palvelunPaattymisaika = palvelunPaattymisaika;
	}

	public static double getValmiitAsiakkaat() {
		return valmiitAsiakkaat;
	}

	public int getId() {
		return id;
	}

	public double getProsessiAika() {
		return poistumisaika-saapumisaika;
	}

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

	public static double getKeskiViipyminen(){
		return sum/valmiitAsiakkaat;
	}

	public static double getSaapuneetAsiakkaat() {
		return saapuneetAsiakkaat;
	}

	public static void resetAsiakkaat() {
		i = 1;
		sum = 0;
		valmiitAsiakkaat = 0;
		saapuneetAsiakkaat = 0;
	}

}