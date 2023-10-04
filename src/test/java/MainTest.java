import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import simu.eduni.distributions.Normal;
import simu.framework.IMoottori;
import simu.model.TapahtumanTyyppi;
import simu.framework.Tapahtuma;
import simu.test.*;

public class MainTest {

    private IMoottori moottori;
    private PalvelupisteForTest palvelupiste;
    private TapahtumalistaForTest tapahtumalista;

    @BeforeEach
    public void setUp() {
        moottori = new OmaMoottoriForTest();
        tapahtumalista = new TapahtumalistaForTest();
        palvelupiste = new PalvelupisteForTest(new Normal(15, 3), tapahtumalista, TapahtumanTyyppi.DEP1);
    }

    @Test
    public void testSimulointiaika() {
        moottori.setSimulointiaika(1440);
        assertEquals(1440, ((OmaMoottoriForTest) moottori).getSimulointiaika());
    }

    @Test
    public void testViive() {
        moottori.setViive(100);
        assertEquals(100, moottori.getViive());
    }

    @Test
    public void testStartStop() {
        assertFalse(((OmaMoottoriForTest) moottori).isRunning());

        ((Thread) moottori).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(((OmaMoottoriForTest) moottori).isRunning());
        ((Thread) moottori).interrupt();
        ((OmaMoottoriForTest) moottori).setRunning(false);
        assertFalse(((OmaMoottoriForTest) moottori).isRunning());
    }

    @Test
    public void testPalvelupiste() {
        palvelupiste.lisaaJonoon(new AsiakasForTest(TapahtumanTyyppi.ARR1));
        palvelupiste.lisaaJonoon(new AsiakasForTest(TapahtumanTyyppi.ARR2));
        palvelupiste.lisaaJonoon(new AsiakasForTest(TapahtumanTyyppi.ARR1));
        assertEquals(3, palvelupiste.getAsiakasJono().size());
        palvelupiste.otaJonosta();
        assertEquals(2, palvelupiste.getAsiakasJono().size());
        palvelupiste.otaJonosta();
        assertEquals(1, palvelupiste.getAsiakasJono().size());
        palvelupiste.otaJonosta();
        assertEquals(0, palvelupiste.getAsiakasJono().size());
        palvelupiste.lisaaJonoon(new AsiakasForTest(TapahtumanTyyppi.ARR1));
        palvelupiste.aloitaPalvelu();
        assertTrue(palvelupiste.onVarattu());
        palvelupiste.otaJonosta();
        assertFalse(palvelupiste.onVarattu());
    }

    @Test
    public void testTapahtumalista() {
        tapahtumalista.lisaa(new Tapahtuma(TapahtumanTyyppi.ARR1, 24.00, true));
        tapahtumalista.lisaa(new Tapahtuma(TapahtumanTyyppi.ARR2, 29.00, false));
        tapahtumalista.lisaa(new Tapahtuma(TapahtumanTyyppi.ARR1, 39.00, true));
        assertEquals(3, tapahtumalista.getKoko());
        assertTrue(tapahtumalista.getSeuraava().isUlkomaanTyyppi());
        assertEquals(24.00, tapahtumalista.getSeuraavanAika());
        tapahtumalista.poista();
        assertEquals(2, tapahtumalista.getKoko());
    }
}
