package controller;

import simu.model.Results;

/**
 * The controller interface that is provided to the simulation engine.
 *
 */

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:

    public void naytaLoppuaika(double aika);
    public void visualisoiAsiakas(int palvelupiste, int asiakasMaara, double kayttoaste, int palvelupisteMaara);
    public void persistRes(Results res);

    void setResults(Results results);

    void showTallennaButton();
}
