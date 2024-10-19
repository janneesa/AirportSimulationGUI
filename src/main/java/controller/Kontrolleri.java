package controller;

import dao.DefaultsDao;
import dao.ResultsDao;
import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.model.Asiakas;
import simu.model.Defaults;
import simu.model.OmaMoottori;
import simu.model.Results;
import view.ISimulaattorinUI;

/**
 * <p>The controller class for the simulation.</p>
 *
 * <p>Works by connecting the model, @see simu.model.OmaMoottori, and the view, @see view.SimulaattorinGUI.</p>
 *
 */

public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV {

	private IMoottori moottori;
	private ISimulaattorinUI ui;
	private ResultsDao resultsDao;
	private Results results;

	/**
	 * <p>Constructor for the controller.</p>
	 *
	 * <p>Called in SimulaattorinGUI, with the GUI itself as the argument.</p>
	 *
	 * @param ui the view
	 */
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

	/**
	 * <p>Starts the simulation by creating an instance of OmaMoottori and calling its methods.</p>
	 */
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(
				this,
				ui.getCheckInKoko(),
				ui.getSelfCheckInKoko(),
				ui.getTurvatarkastusKoko(),
				ui.getPorttiKoko(),
				ui.getMeanCheckIn(),
				ui.getVarianceCheckIn(),
				ui.getMeanSelfCheckIn(),
				ui.getVarianceSelfCheckIn(),
				ui.getMeanTurvatarkastus(),
				ui.getVarianceTurvatarkastus(),
				ui.getMeanPortti(),
				ui.getVariancePortti(),
				ui.getMeanSaapumisvali(),
				ui.getVarianceSaapumisvali(),
				ui.getSelfCheckInTodennakoisyys()
		);
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		ui.getVisualisointi().tyhjennaNaytto();
		((Thread) moottori).start();
	}

	/**
	 * Slows down the simulation by increasing the delay between each step.
	 */
	@Override
	public void hidasta() {
		moottori.setViive((long) (moottori.getViive() * 1.10));
	}

	/**
	 * Speeds up the simulation by decreasing the delay between each step.
	 */
	@Override
	public void nopeuta() {
		moottori.setViive((long) (moottori.getViive() * 0.9));
	}

	/**
	 * Sets the end time of the simulation. Called once the simulation is finished.
	 */
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(() -> ui.setLoppuaika(aika));
	}

	/**
	 * Updates the simulation view with the current state of the simulation.
	 */
	@Override
	public void visualisoiAsiakas(int palvelupiste, int asiakasMaara, double kayttoaste, int palvelupisteMaara) {
		Platform.runLater(() -> ui.getVisualisointi().paivitaVisualisointi(palvelupiste, asiakasMaara, kayttoaste, palvelupisteMaara));
	}

	/**
	 * Persists the results of the simulation to the database. Called when the simulation is finished.
	 *
	 * @param res the results to persist
	 */
	public void persistRes(Results res) {
		resultsDao = new ResultsDao();
		resultsDao.persist(res);
	}

	/**
	 * Persists the currently set parameters of the simulation to the database. Called when the Tallenna button is pressed once the simulation is finished.
	 *
	 * @param defaults the default values to persist
	 */
	public void persistDef(Defaults defaults) {
		DefaultsDao defaultsDao= new DefaultsDao();
		defaultsDao.persist(defaults);
		setResults(results);
		persistRes(results);
	}

	/**
	 * Gets the latest persisted parameters from the database. Called when the Hae edellinen button is pressed.
	 *
	 * @return the parameters from the database
	 */
	public Defaults haeEdellinen() {
		DefaultsDao defaultsDao = new DefaultsDao();
		return defaultsDao.getDefaults();
	}

	/**
	 * Sets the results of the simulation to the results variable for to be persisted to the database.
	 *
	 * @param results the results to set
	 */
	public void setResults(Results results) {
		this.results = results;
	}

	public Results getResults() {
		return results;
	}

	/**
	 * Shows the Tallenna button in the view once the simulation is finished.
	 *
	 */
	public void showTallennaButton() {
		ui.setTallennaButtonVisible(true);
		ui.setResetButtonVisible(true);
	}

	/**
	 * Resets the simulation by calling the reset method in the model.
	 */
	public void resetSimulation() {
		moottori.reset();
		Asiakas.resetAsiakkaat();
		Kello.getInstance().setAika(0.0);
	}
}