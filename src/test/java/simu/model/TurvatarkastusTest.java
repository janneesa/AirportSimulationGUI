package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.Kello;
import simu.framework.Tapahtumalista;
import simu.model.TapahtumanTyyppi;

import static org.junit.jupiter.api.Assertions.*;

public class TurvatarkastusTest {

    private Turvatarkastus turvatarkastus;
    private ContinuousGenerator generator;
    private Tapahtumalista tapahtumalista;

    @BeforeEach
    public void setUp() {
        generator = new Normal(10, 2);
        tapahtumalista = new Tapahtumalista();
        turvatarkastus = new Turvatarkastus("Turvatarkastus", generator, tapahtumalista, TapahtumanTyyppi.ARRIVAL);
        Kello.getInstance().setAika(0); // Reset the clock for each test
        turvatarkastus.resetPoint(); // Reset static variables for each test
    }

    @Test
    public void testPaivitaKeskiPalveluaika() {
        turvatarkastus.paivitaKeskiPalveluaika(5.0);
        assertEquals(1, Turvatarkastus.getAloitetutPalvelut());
        assertEquals(5.0, Turvatarkastus.getTotalPalveluaika());
        assertEquals(5.0, Turvatarkastus.getKeskiPalveluaika());
    }

    @Test
    public void testGetKayttoAste() {
        Kello.getInstance().setAika(10);
        turvatarkastus.paivitaKeskiPalveluaika(5.0);
        assertEquals(0.5, Turvatarkastus.getKayttoAste());
    }

    @Test
    public void testGetLapimeno() {
        Kello.getInstance().setAika(10);
        turvatarkastus.paivitaKeskiPalveluaika(5.0);
        assertEquals(0.1, Turvatarkastus.getLapimeno());
    }

    @Test
    public void testResetPoint() {
        turvatarkastus.paivitaKeskiPalveluaika(5.0);
        turvatarkastus.resetPoint();
        assertEquals(0, Turvatarkastus.getAloitetutPalvelut());
        assertEquals(0.0, Turvatarkastus.getTotalPalveluaika());
        assertEquals(0.0, Turvatarkastus.getKeskiPalveluaika());
    }
}