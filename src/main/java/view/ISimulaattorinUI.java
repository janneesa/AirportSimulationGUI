package view;

public interface ISimulaattorinUI {

	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	double getAika();
	long getViive();
	int getCheckInKoko();
	int getSelfCheckInKoko();
	int getTurvatarkastusKoko();
	int getPorttiKoko();
	int getMeanPalveluaika();
	int getVariancePalveluaika();
	int getMeanSaapumisvali();
	int getVarianceSaapumisvali();

	// Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa
	void setLoppuaika(double aika);

	// Kontrolleri tarvitsee
	IVisualisointi getVisualisointi();
}