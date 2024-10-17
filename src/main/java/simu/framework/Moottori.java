package simu.framework;


import controller.IKontrolleriForM; // UUSI

/**
 * <p>Represents the simulation engine.</p>
 *
 * <p>Includes the main loop of the simulation engine, which is responsible for running the simulation.</p>
 */

public abstract class Moottori extends Thread implements IMoottori{  // UUDET MÄÄRITYKSET
	
	private double simulointiaika = 0;
	private long viive = 0;
	
	private Kello kello;
	
	protected Tapahtumalista tapahtumalista;

	protected IKontrolleriForM kontrolleri; // UUSI

	/**
	 * <p>Constructor for the simulation engine.</p>
	 *
	 * @param kontrolleri The controller for the simulation engine.
	 */

	public Moottori(IKontrolleriForM kontrolleri){  // UUSITTU
		
		this.kontrolleri = kontrolleri;  //UUSI

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia
		
		tapahtumalista = new Tapahtumalista();
		
		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa 
		
		
	}

	@Override
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}
	
	@Override // UUSI
	public void setViive(long viive) {
		this.viive = viive;
	}
	
	@Override // UUSI 
	public long getViive() {
		return viive;
	}

	/**
	 * <p>Runs the simulation engine loop.</p>
	 *
	 */
	
	@Override
	public void run(){ // Entinen aja()
		alustukset(); // luodaan mm. ensimmäinen tapahtuma
		while (simuloidaan()){
			viive(); // UUSI
			kello.setAika(nykyaika());
			suoritaBTapahtumat();
			yritaCTapahtumat();
		}
		tulokset();
		
	}

	/**
	 * <p>Executes the so-called B events of the simulation engine.</p>
	 *
	 * <p>Executes the events which are to be executed at this clock time.</p>
	 */
	
	private void suoritaBTapahtumat(){
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	protected abstract void yritaCTapahtumat();

	/**
	 * <p>Gets the current time of the simulation engine.</p>
	 *
	 * @return The current time of the simulation engine.
	 */
	private double nykyaika(){
		return tapahtumalista.getSeuraavanAika();
	}

	/**
	 * <p>Checks if the simulation should still be run by comparing the current time to the initially set simulation time.</p>
	 *
	 * @return True if the simulation is still running, false otherwise.
	 */
	private boolean simuloidaan(){
		Trace.out(Trace.Level.INFO, "Kello on: " + kello.getAika());
		return kello.getAika() < simulointiaika;
	}

	/**
	 * <p>Pauses the simulation engine for the time set by the user.</p>
 	 */
	private void viive() { // UUSI
		Trace.out(Trace.Level.INFO, "Viive " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void suoritaTapahtuma(Tapahtuma t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
}