package view;

import java.awt.*;

public interface ISimulaattorinUI {

	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	double getAika();
	long getViive();
	int getCheckInKoko();
	int getSelfCheckInKoko();
	int getTurvatarkastusKoko();
	int getPorttiKoko();
	// int getMeanPalveluaika();
	// int getVariancePalveluaika();
	int getMeanCheckIn();
	int getVarianceCheckIn();
	int getMeanSelfCheckIn();
	int getVarianceSelfCheckIn();
	int getMeanTurvatarkastus();
	int getVarianceTurvatarkastus();
	int getMeanPortti();
	int getVariancePortti();
	int getMeanSaapumisvali();
	int getVarianceSaapumisvali();
	double getSelfCheckInTodennakoisyys();

	// Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa
	void setLoppuaika(double aika);

	// Kontrolleri tarvitsee
	IVisualisointi getVisualisointi();

}