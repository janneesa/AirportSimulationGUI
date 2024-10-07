package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;

public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV {

	private IMoottori moottori;
	private ISimulaattorinUI ui;

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
}