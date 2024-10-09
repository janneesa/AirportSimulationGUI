package controller;

import dao.DefaultsDao;
import dao.ResultsDao;
import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.model.Defaults;
import simu.model.OmaMoottori;
import simu.model.Results;
import view.ISimulaattorinUI;


public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV {

	private IMoottori moottori;
	private ISimulaattorinUI ui;
	private ResultsDao resultsDao;
	private Results results;

	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

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

	@Override
	public void hidasta() {
		moottori.setViive((long) (moottori.getViive() * 1.10));
	}

	@Override
	public void nopeuta() {
		moottori.setViive((long) (moottori.getViive() * 0.9));
	}

	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(() -> ui.setLoppuaika(aika));
	}

	@Override
	public void visualisoiAsiakas(int palvelupiste, int asiakasMaara, double kayttoaste, int palvelupisteMaara) {
		Platform.runLater(() -> ui.getVisualisointi().paivitaVisualisointi(palvelupiste, asiakasMaara, kayttoaste, palvelupisteMaara));
	}

	public void persistRes(Results res) {
		resultsDao = new ResultsDao();
		resultsDao.persist(res);
	}

	public void persistDef(Defaults defaults) {
		DefaultsDao defaultsDao= new DefaultsDao();
		defaultsDao.persist(defaults);
		setResults(results);
		persistRes(results);
	}

	public Defaults haeEdellinen() {
		DefaultsDao defaultsDao = new DefaultsDao();
		return defaultsDao.getDefaults();
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public Results getResults() {
		return results;
	}

	public void showTallennaButton() {
		ui.setTallennaButtonVisible(true);
		ui.setResetButtonVisible(true);
	}

	public void resetSimulation() {
		moottori.reset();
		Kello.getInstance().setAika(0.0);
	}
}