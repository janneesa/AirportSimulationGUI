package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.Kello;
import simu.framework.Tapahtumalista;
import simu.model.TapahtumanTyyppi;

import static org.junit.jupiter.api.Assertions.*;

public class CheckInTest {

    private CheckIn checkIn;
    private ContinuousGenerator generator;
    private Tapahtumalista tapahtumalista;

    @BeforeEach
    public void setUp() {
        generator = new Normal(10, 2);
        tapahtumalista = new Tapahtumalista();
        checkIn = new CheckIn("CheckIn", generator, tapahtumalista, TapahtumanTyyppi.ARRIVAL);
        Kello.getInstance().setAika(0); // Reset the clock for each test
        checkIn.resetPoint(); // Reset static variables for each test
    }

    @Test
    public void testPaivitaKeskiPalveluaika() {
        checkIn.paivitaKeskiPalveluaika(5.0);
        assertEquals(1, CheckIn.getAloitetutPalvelut());
        assertEquals(5.0, CheckIn.getTotalPalveluaika());
        assertEquals(5.0, CheckIn.getKeskiPalveluaika());
    }

    @Test
    public void testGetKayttoAste() {
        Kello.getInstance().setAika(10);
        checkIn.paivitaKeskiPalveluaika(5.0);
        assertEquals(0.5, CheckIn.getKayttoAste());
    }

    @Test
    public void testGetLapimeno() {
        Kello.getInstance().setAika(10);
        checkIn.paivitaKeskiPalveluaika(5.0);
        assertEquals(0.1, CheckIn.getLapimeno());
    }

    @Test
    public void testResetPoint() {
        checkIn.paivitaKeskiPalveluaika(5.0);
        checkIn.resetPoint();
        assertEquals(0, CheckIn.getAloitetutPalvelut());
        assertEquals(0.0, CheckIn.getTotalPalveluaika());
        assertEquals(0.0, CheckIn.getKeskiPalveluaika());
    }
}