import ca.mcmaster.se2aa4.island.team205.CreekLocations;
import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import ca.mcmaster.se2aa4.island.team205.PhotoScanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhotoScannerTest {

    private PhotoScanner photoScanner;
    private UsingJSON usingJSON;

    private Drone drone = new Drone(usingJSON);

    private CreekLocations creeks = new CreekLocations();
    @BeforeEach
    void setUp() {
        usingJSON = new UsingJSON();
        photoScanner = new PhotoScanner(usingJSON, drone, creeks);
    }

    @Test
    void testScanResultsWhenOceanIsNotPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"MOUNTAIN\"]}}");
        Assertions.assertFalse(photoScanner.scanOcean());
    }

    @Test
    void testScanResultsWhenOceanIsPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"OCEAN\"]}}");
        Assertions.assertTrue(photoScanner.scanOcean());
    }

}
