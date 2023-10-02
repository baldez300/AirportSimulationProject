
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import simu.framework.IMoottori;
import simu.framework.MoottoriForTest;
import simu.model.OmaMoottoriForTest;

public class MainTest {
    private IMoottori moottori;
    @Test
    public void test() {
        moottori = new OmaMoottoriForTest();
        moottori.setSimulointiaika(1440);
        moottori.setViive(100);
        ((Thread) moottori).start();

        // Wait for the condition to become true with a timeout
        long startTime = System.currentTimeMillis();
        long timeoutMillis = 5000; // Set an appropriate timeout
        while (!((MoottoriForTest)moottori).generoidaanUusiaLentoja) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > timeoutMillis) {
                // Timeout: condition not met within the specified time
                fail("Timeout waiting for the condition");
            }
        }
        // The condition is met, perform assertions
        assertEquals(1, 1 + 1);
        assertTrue(((OmaMoottoriForTest)moottori).jarjestelmaOnTyhja());
    }
}
