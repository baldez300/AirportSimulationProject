import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import simu.model.OmaMoottoriForTest;
import simu.framework.IMoottori;

public class MainTest {
    private IMoottori moottori;

    @BeforeEach
    public void setUp() {
        moottori = new OmaMoottoriForTest();
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
}
