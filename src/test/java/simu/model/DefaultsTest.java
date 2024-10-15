package simu.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultsTest {

    private Defaults defaults;

    @BeforeEach
    public void setUp() {
        defaults = new Defaults(10, 5, 3, 4, 2, 1, 15, 5, 20, 10, 25, 12, 30, 15, 35, 18, 0.75);
    }

    @Test
    public void testGetters() {
        assertEquals(10, defaults.getAika());
        assertEquals(5, defaults.getViive());
        assertEquals(3, defaults.getCheckInKoko());
        assertEquals(4, defaults.getSelfCheckInKoko());
        assertEquals(2, defaults.getTurvatarkastusKoko());
        assertEquals(1, defaults.getPorttiKoko());
        assertEquals(15, defaults.getMeanCheckIn());
        assertEquals(5, defaults.getVarianceCheckIn());
        assertEquals(20, defaults.getMeanSelfCheckIn());
        assertEquals(10, defaults.getVarianceSelfCheckIn());
        assertEquals(25, defaults.getMeanTurvatarkastus());
        assertEquals(12, defaults.getVarianceTurvatarkastus());
        assertEquals(30, defaults.getMeanPortti());
        assertEquals(15, defaults.getVariancePortti());
        assertEquals(35, defaults.getMeanSaapumisvali());
        assertEquals(18, defaults.getVarianceSaapumisvali());
        assertEquals(0.75, defaults.getSelfCheckInTodennakoisyys());
    }

    @Test
    public void testSetters() {
        defaults.setAika(20);
        defaults.setViive(10);
        defaults.setCheckInKoko(6);
        defaults.setSelfCheckInKoko(8);
        defaults.setTurvatarkastusKoko(4);
        defaults.setPorttiKoko(2);
        defaults.setMeanCheckIn(30);
        defaults.setVarianceCheckIn(15);
        defaults.setMeanSelfCheckIn(40);
        defaults.setVarianceSelfCheckIn(20);
        defaults.setMeanTurvatarkastus(50);
        defaults.setVarianceTurvatarkastus(24);
        defaults.setMeanPortti(60);
        defaults.setVariancePortti(30);
        defaults.setMeanSaapumisvali(70);
        defaults.setVarianceSaapumisvali(36);
        defaults.setSelfCheckInTodennakoisyys(0.85);

        assertEquals(20, defaults.getAika());
        assertEquals(10, defaults.getViive());
        assertEquals(6, defaults.getCheckInKoko());
        assertEquals(8, defaults.getSelfCheckInKoko());
        assertEquals(4, defaults.getTurvatarkastusKoko());
        assertEquals(2, defaults.getPorttiKoko());
        assertEquals(30, defaults.getMeanCheckIn());
        assertEquals(15, defaults.getVarianceCheckIn());
        assertEquals(40, defaults.getMeanSelfCheckIn());
        assertEquals(20, defaults.getVarianceSelfCheckIn());
        assertEquals(50, defaults.getMeanTurvatarkastus());
        assertEquals(24, defaults.getVarianceTurvatarkastus());
        assertEquals(60, defaults.getMeanPortti());
        assertEquals(30, defaults.getVariancePortti());
        assertEquals(70, defaults.getMeanSaapumisvali());
        assertEquals(36, defaults.getVarianceSaapumisvali());
        assertEquals(0.85, defaults.getSelfCheckInTodennakoisyys());
    }
}