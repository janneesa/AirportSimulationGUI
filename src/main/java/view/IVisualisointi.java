package view;

/**
 * Interface for visualizing the simulation.
 */
public interface IVisualisointi {

	/**
	 * Clears the display.
	 */
	public void tyhjennaNaytto();

	/**
	 * Not used in this implementation.
	 */
	public void uusiAsiakas();

	/**
	 * Updates the visualization with the latest data.
	 *
	 * @param palvelupiste the index of the service point.
	 * @param asiakasMaara the number of customers.
	 * @param kayttoaste the usage rate of the service point.
	 * @param palvelupisteMaara the number of service points.
	 */
	void paivitaVisualisointi(int palvelupiste, int asiakasMaara, double kayttoaste, int palvelupisteMaara);
}

