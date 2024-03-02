import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import ca.mcmaster.se2aa4.island.team205.PhotoScanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhotoScannerTest {

    private PhotoScanner photoScanner;
    private UsingJSON usingJSON;

    @BeforeEach
    void setUp() {
        usingJSON = new UsingJSON();
        photoScanner = new PhotoScanner(usingJSON);
    }

    @Test
    void testScanResultsWhenOceanIsNotPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"MOUNTAIN\"]}}");
        assertFalse(photoScanner.scanOcean());
    }

    @Test
    void testScanResultsWhenOceanIsPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"OCEAN\"]}}");
        assertTrue(photoScanner.scanOcean());
    }

}
