package controller;

public interface IKontrolleriForV {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();
    public void nopeuta();
    public void hidasta();
    public void visualisoiAsiakas(int palvelupiste, int asiakasMaara, double kayttoaste);
}
