package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.Kello;
import simu.framework.Tapahtumalista;
import simu.model.TapahtumanTyyppi;

import static org.junit.jupiter.api.Assertions.*;

public class PorttiTest {

    private Portti portti;
    private ContinuousGenerator generator;
    private Tapahtumalista tapahtumalista;

    @BeforeEach
    public void setUp() {
        generator = new Normal(10, 2);
        tapahtumalista = new Tapahtumalista();
        portti = new Portti("Portti", generator, tapahtumalista, TapahtumanTyyppi.ARRIVAL);
        Kello.getInstance().setAika(0); // Reset the clock for each test
        portti.resetPoint(); // Reset static variables for each test
    }

    @Test
    public void testPaivitaKeskiPalveluaika() {
        portti.paivitaKeskiPalveluaika(5.0);
        assertEquals(1, Portti.getAloitetutPalvelut());
        assertEquals(5.0, Portti.getTotalPalveluaika());
        assertEquals(5.0, Portti.getKeskiPalveluaika());
    }

    @Test
    public void testGetKayttoAste() {
        Kello.getInstance().setAika(10);
        portti.paivitaKeskiPalveluaika(5.0);
        assertEquals(0.5, Portti.getKayttoAste());
    }

    @Test
    public void testGetLapimeno() {
        Kello.getInstance().setAika(10);
        portti.paivitaKeskiPalveluaika(5.0);
        assertEquals(0.1, Portti.getLapimeno());
    }

    @Test
    public void testResetPoint() {
        portti.paivitaKeskiPalveluaika(5.0);
        portti.resetPoint();
        assertEquals(0, Portti.getAloitetutPalvelut());
        assertEquals(0.0, Portti.getTotalPalveluaika());
        assertEquals(0.0, Portti.getKeskiPalveluaika());
    }
}