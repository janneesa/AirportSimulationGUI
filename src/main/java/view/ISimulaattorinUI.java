package view;

import java.awt.*;

/**
 * Interface for the graphical user interface of the simulator.
 *
 * The controller gets input from the user interface, which it forwards to the OmaMoottori class.
 *
 */

public interface ISimulaattorinUI {

	/**
	 * Gets the simulation time.
	 *
	 * @return the simulation time in minutes.
	 */
	double getAika();

	/**
	 * Gets the delay time.
	 *
	 * @return the delay time in milliseconds.
	 */
	long getViive();

	/**
	 * Gets the number of check-in points.
	 *
	 * @return the number of check-in points.
	 */
	int getCheckInKoko();

	/**
	 * Gets the number of self check-in points.
	 *
	 * @return the number of self check-in points.
	 */
	int getSelfCheckInKoko();

	/**
	 * Gets the number of security check points.
	 *
	 * @return the number of security check points.
	 */
	int getTurvatarkastusKoko();

	/**
	 * Gets the number of gates.
	 *
	 * @return the number of gates.
	 */
	int getPorttiKoko();

	/**
	 * Gets the mean service time for check-in.
	 *
	 * @return the mean service time for check-in in minutes.
	 */
	int getMeanCheckIn();

	/**
	 * Gets the variance of service time for check-in.
	 *
	 * @return the variance of service time for check-in in minutes.
	 */
	int getVarianceCheckIn();

	/**
	 * Gets the mean service time for self check-in.
	 *
	 * @return the mean service time for self check-in in minutes.
	 */
	int getMeanSelfCheckIn();

	/**
	 * Gets the variance of service time for self check-in.
	 *
	 * @return the variance of service time for self check-in in minutes.
	 */
	int getVarianceSelfCheckIn();

	/**
	 * Gets the mean service time for the security check.
	 *
	 * @return the mean service time for the security check in minutes.
	 */
	int getMeanTurvatarkastus();

	/**
	 * Gets the variance of service time for security check.
	 *
	 * @return the variance of service time for security check in minutes.
	 */
	int getVarianceTurvatarkastus();

	/**
	 * Gets the mean service time for the gates.
	 *
	 * @return the mean service time for the gate in minutes.
	 */
	int getMeanPortti();

	/**
	 * Gets the variance of service time for the gates
	 *
	 * @return the variance of service time for the gate in minutes.
	 */
	int getVariancePortti();

	/**
	 * Gets the mean arrival interval.
	 *
	 * @return the mean arrival interval in minutes.
	 */
	int getMeanSaapumisvali();

	/**
	 * Gets the variance of arrival interval.
	 *
	 * @return the variance of arrival interval in minutes.
	 */
	int getVarianceSaapumisvali();

	/**
	 * Gets the probability of using self check-in.
	 *
	 * @return the probability of using self check-in as a percentage.
	 */
	double getSelfCheckInTodennakoisyys();

	// Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa
	/**
	 * Sets the end time of the simulation.
	 *
	 * @param aika the end time in minutes.
	 */
	void setLoppuaika(double aika);

	// Kontrolleri tarvitsee

	/**
	 * Returns the visualisation object. The controller uses this to update the visualisation.
	 *
	 * @return the visualisation object.
	 */
	IVisualisointi getVisualisointi();

	/**
	 * Sets the visibility of the reset button.
	 *
	 * @param b true to make the button visible, false to hide it.
	 */
	void setResetButtonVisible(boolean b);

	/**
	 * Sets the visibility of the save button.
	 *
	 * @param b true to make the button visible, false to hide it.
	 */
	void setTallennaButtonVisible(boolean b);
}