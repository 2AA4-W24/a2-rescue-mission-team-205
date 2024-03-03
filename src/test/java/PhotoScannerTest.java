import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import ca.mcmaster.se2aa4.island.team205.PhotoScanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhotoScannerTest {

    private PhotoScanner photoScanner;
    private UsingJSON usingJSON;

    private Drone drone;

    @BeforeEach
    void setUp() {
        usingJSON = new UsingJSON();
        drone = new Drone(usingJSON);
        photoScanner = new PhotoScanner(usingJSON, drone);
    }

    @Test
    void testScanResultsWhenOceanIsNotPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"MOUNTAIN\"]}}");
        assertTrue(photoScanner.scanResults());
    }

    @Test
    void testScanResultsWhenOceanIsPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"OCEAN\"]}}");
        assertFalse(photoScanner.scanResults());
    }

}
