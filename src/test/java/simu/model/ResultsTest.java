package simu.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResultsTest {

    private Results results;

    @BeforeEach
    public void setUp() {
        results = new Results(100.0, 50.0, 30.0, 20.0, 15.0, 10.0, 5.0, 25.0, 18.0, 18.0, 8.0, 8.0, 22.0, 16.0, 22.0, 7.0, 11.0, 21.0, 14.0, 21.0, 6.0, 9.0, 19.0);
    }

    @Test
    public void testGetters() {
        assertEquals(100.0, results.getLoppuaika());
        assertEquals(50.0, results.getValmiitAsiakkaat());
        assertEquals(30.0, results.getMeanLapimenoaika());
        assertEquals(15.0, results.getCheckInMeanPalveluaika());
        assertEquals(10.0, results.getCheckInKokoPalveluaika());
        assertEquals(5.0, results.getCheckInKayttoaste());
        assertEquals(25.0, results.getCheckInSuorituskyky());
        assertEquals(18.0, results.getSelfCheckInMeanPalveluaika());
        assertEquals(8.0, results.getSelfCheckInKokoPalveluaika());
        assertEquals(8.0, results.getSelfCheckInKayttoaste());
        assertEquals(22.0, results.getSelfCheckInSuorituskyky());
        assertEquals(22.0, results.getTurvatarkastusMeanPalveluaika());
        assertEquals(7.0, results.getTurvatarkastusKokoPalveluaika());
        assertEquals(11.0, results.getTurvatarkastusKayttoaste());
        assertEquals(21.0, results.getTurvatarkastusSuorituskyky());
        assertEquals(21.0, results.getPorttiMeanPalveluaika());
        assertEquals(6.0, results.getPorttiKokoPalveluaika());
        assertEquals(9.0, results.getPorttiKayttoaste());
        assertEquals(19.0, results.getPorttiSuorituskyky());
    }

    @Test
    public void testSetters() {
        results.setLoppuaika(200.0);
        results.setValmiitAsiakkaat(100.0);
        results.setMeanLapimenoaika(60.0);
        results.setCheckInMeanPalveluaika(30.0);
        results.setCheckInKokoPalveluaika(20.0);
        results.setCheckInKayttoaste(10.0);
        results.setCheckInSuorituskyky(50.0);
        results.setSelfCheckInMeanPalveluaika(36.0);
        results.setSelfCheckInKokoPalveluaika(24.0);
        results.setSelfCheckInKayttoaste(16.0);
        results.setSelfCheckInSuorituskyky(8.0);
        results.setTurvatarkastusMeanPalveluaika(44.0);
        results.setTurvatarkastusKokoPalveluaika(32.0);
        results.setTurvatarkastusKayttoaste(22.0);
        results.setTurvatarkastusSuorituskyky(14.0);
        results.setPorttiMeanPalveluaika(42.0);
        results.setPorttiKokoPalveluaika(28.0);
        results.setPorttiKayttoaste(18.0);
        results.setPorttiSuorituskyky(12.0);

        assertEquals(200.0, results.getLoppuaika());
        assertEquals(100.0, results.getValmiitAsiakkaat());
        assertEquals(60.0, results.getMeanLapimenoaika());
        assertEquals(30.0, results.getCheckInMeanPalveluaika());
        assertEquals(20.0, results.getCheckInKokoPalveluaika());
        assertEquals(10.0, results.getCheckInKayttoaste());
        assertEquals(50.0, results.getCheckInSuorituskyky());
        assertEquals(36.0, results.getSelfCheckInMeanPalveluaika());
        assertEquals(24.0, results.getSelfCheckInKokoPalveluaika());
        assertEquals(16.0, results.getSelfCheckInKayttoaste());
        assertEquals(8.0, results.getSelfCheckInSuorituskyky());
        assertEquals(44.0, results.getTurvatarkastusMeanPalveluaika());
        assertEquals(32.0, results.getTurvatarkastusKokoPalveluaika());
        assertEquals(22.0, results.getTurvatarkastusKayttoaste());
        assertEquals(14.0, results.getTurvatarkastusSuorituskyky());
        assertEquals(42.0, results.getPorttiMeanPalveluaika());
        assertEquals(28.0, results.getPorttiKokoPalveluaika());
        assertEquals(18.0, results.getPorttiKayttoaste());
        assertEquals(12.0, results.getPorttiSuorituskyky());
    }
}