import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.PhotoScanner;
import ca.mcmaster.se2aa4.island.team205.CreekLocations;
import ca.mcmaster.se2aa4.island.team205.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;


class PhotoScannerTest {

    private PhotoScanner photoScanner;
    private UsingJSON usingJSON;
    private CreekLocations creeks;

    @BeforeEach
    void setUp() {
        usingJSON = new UsingJSON();
        Drone drone = new Drone(usingJSON, new Point(0, 0));
        creeks = new CreekLocations();
        photoScanner = new PhotoScanner(usingJSON, drone, creeks);
    }

    @Test
    void testScanResultsWhenOceanIsNotPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"MOUNTAIN\"]}}");
        Assertions.assertFalse(photoScanner.scanOcean());
    }

    @Test
    void testScanResultsWhenOceanIsPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"OCEAN\"]}}");
        Assertions.assertTrue(photoScanner.scanOcean());
    }

    @Test
    void testScanResultsWithSingleOceanTerrain() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"OCEAN\"]}}");
        assertFalse(photoScanner.scanResults());
    }

    @Test
    void testScanResultsWithMultipleBiomesExcludingOcean() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"FOREST\", \"MOUNTAIN\"]}}");
        assertTrue(photoScanner.scanResults());
    }

    
    @Test
    void testScanResultsWithMultipleBiomesIncludingOcean() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"OCEAN\", \"FOREST\"]}}");
        assertTrue(photoScanner.scanResults());
    }

    @Test
    void testSiteNotFound() {
        usingJSON.results("{\"extras\":{\"sites\":[]}}"); 
        assertFalse(photoScanner.siteFound());
    }

    @Test
    void testOverCoastWhenPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"OCEAN\"]}}");
        assertTrue(photoScanner.overCoast());
    }

    @Test
    void testOverCoastWhenAbsent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\"]}}");
        assertFalse(photoScanner.overCoast());
    }

    @Test
    void testGetSiteWhenFound() {
        usingJSON.results("{\"extras\":{\"sites\":[\"Site1\"]}}");
        photoScanner.siteFound();
        assertNotNull(photoScanner.getSite());
    }

    @Test
    void testScanTerrainUpdatesInfo() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\"]}}");
        assertFalse(photoScanner.scanOcean());
        
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"OCEAN\"]}}");
        photoScanner.scanTerrain();
        
        assertTrue(photoScanner.scanOcean());
    }

    @Test
    void testCreekScanWithNewCreek() {
        usingJSON.results("{\"extras\":{\"creeks\":[\"Creek1\"]}}");
        photoScanner.creekScan();

        assertEquals(1, creeks.numberOfCreeks());

        String expectedIdentifier = "Creek1";
        List<String> creekIdentifiers = creeks.identifiers();
        assertTrue(creekIdentifiers.contains(expectedIdentifier));
    }
    
    @Test
    void testCreekScanWithNoNewCreek() {
        usingJSON.results("{\"extras\":{\"creeks\":[\"Creek1\"]}}");
        photoScanner.creekScan();
        int initialCreekCount = creeks.numberOfCreeks();
        
        photoScanner.creekScan();
        assertEquals(initialCreekCount, creeks.numberOfCreeks());
    }


    @Test
    void testGetSiteWhenNotFound() {
        Assertions.assertNull(photoScanner.getSite());
    }
}
