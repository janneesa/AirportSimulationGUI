package controller;

import simu.model.Defaults;

public interface IKontrolleriForV {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();
    public void nopeuta();
    public void hidasta();
    public void visualisoiAsiakas(int palvelupiste, int asiakasMaara, double kayttoaste, int palvelupisteMaara);
    public void persistDef(Defaults defaults);
    public Defaults haeEdellinen();

    void resetSimulation();
}
