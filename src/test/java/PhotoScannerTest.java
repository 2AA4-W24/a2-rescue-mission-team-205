import ca.mcmaster.se2aa4.island.team205.*;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class PhotoScannerTest {

    private PhotoScanner photoScanner;
    private UsingJSON usingJSON;
    private Drone drone;
    private CreekLocations creeks;

    @BeforeEach
    void setUp() {
        usingJSON = new UsingJSON();
        drone = new Drone(usingJSON, new Point());
        creeks = new CreekLocations();
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

    @Test
    void testCreekScanAddsCreek() {
        usingJSON.results("{\"extras\":{\"creeks\":[\"Creek1\"]}}");
        boolean creekAdded = photoScanner.creekScan();
    
        assertTrue(creekAdded);
        assertEquals(1, creeks.numberOfCreeks());
    }

    @Test
    void testSiteNotFound() {
        usingJSON.results("{\"extras\":{\"sites\":[]}}"); 
        assertFalse(photoScanner.siteFound(), "siteFound should return false when no site is present.");
    }

    @Test
    void testOverCoastWhenPresent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\", \"OCEAN\"]}}");
        assertTrue(photoScanner.overCoast(), "overCoast should return true when OCEAN is present.");
    }

    @Test
    void testOverCoastWhenAbsent() {
        usingJSON.results("{\"extras\":{\"biomes\":[\"LAND\"]}}");
        assertFalse(photoScanner.overCoast(), "overCoast should return false when OCEAN is not present.");
    }

    @Test
    void testGetSiteWhenFound() {
        usingJSON.results("{\"extras\":{\"sites\":[\"Site1\"]}}"); // Simulate finding a site
        photoScanner.siteFound(); // Trigger siteFound to set the site
        assertNotNull(photoScanner.getSite(), "getSite should return a site when one is found.");
    }
/* 
    @Test
    void testGetSiteWhenNotFound() {
        usingJSON.results("{\"extras\":{\"site\":[]}}"); 
        assertFalse(photoScanner.getSite(), "getSite should return null when no site is found.");
    }
*/
}
