package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Tapahtumalista;

import java.util.LinkedList;

public class Portti extends Palvelupiste {

    private final static LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus

    public Portti(String nimi , ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
        super(nimi, generator, tapahtumalista, tyyppi);
    }
}