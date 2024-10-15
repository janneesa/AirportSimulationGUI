package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.Kello;
import simu.framework.Tapahtumalista;
import simu.model.TapahtumanTyyppi;

import static org.junit.jupiter.api.Assertions.*;

public class SelfCheckInTest {

    private SelfCheckIn selfCheckIn;
    private ContinuousGenerator generator;
    private Tapahtumalista tapahtumalista;

    @BeforeEach
    public void setUp() {
        generator = new Normal(10, 2);
        tapahtumalista = new Tapahtumalista();
        selfCheckIn = new SelfCheckIn("SelfCheckIn", generator, tapahtumalista, TapahtumanTyyppi.ARRIVAL);
        Kello.getInstance().setAika(0); // Reset the clock for each test
        selfCheckIn.resetPoint(); // Reset static variables for each test
    }

    @Test
    public void testPaivitaKeskiPalveluaika() {
        selfCheckIn.paivitaKeskiPalveluaika(5.0);
        assertEquals(1, SelfCheckIn.getAloitetutPalvelut());
        assertEquals(5.0, SelfCheckIn.getTotalPalveluaika());
        assertEquals(5.0, SelfCheckIn.getKeskiPalveluaika());
    }

    @Test
    public void testGetKayttoAste() {
        Kello.getInstance().setAika(10);
        selfCheckIn.paivitaKeskiPalveluaika(5.0);
        assertEquals(0.5, SelfCheckIn.getKayttoAste());
    }

    @Test
    public void testGetLapimeno() {
        Kello.getInstance().setAika(10);
        selfCheckIn.paivitaKeskiPalveluaika(5.0);
        assertEquals(0.1, SelfCheckIn.getLapimeno());
    }

    @Test
    public void testResetPoint() {
        selfCheckIn.paivitaKeskiPalveluaika(5.0);
        selfCheckIn.resetPoint();
        assertEquals(0, SelfCheckIn.getAloitetutPalvelut());
        assertEquals(0.0, SelfCheckIn.getTotalPalveluaika());
        assertEquals(0.0, SelfCheckIn.getKeskiPalveluaika());
    }
}