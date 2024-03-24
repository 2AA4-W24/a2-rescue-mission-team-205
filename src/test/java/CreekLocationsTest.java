import ca.mcmaster.se2aa4.island.team205.Point;
import ca.mcmaster.se2aa4.island.team205.PointOfInterest;
import ca.mcmaster.se2aa4.island.team205.CreekLocations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class CreekLocationsTest {

    private CreekLocations creekLocations;

    @BeforeEach
    void setUp() {
        creekLocations = new CreekLocations();
    }

    @Test
    void testClosestCreek() {
        PointOfInterest creek1 = new PointOfInterest("Creek1", new Point(10, 10));
        PointOfInterest creek2 = new PointOfInterest("Creek2", new Point(20, 20));

        creekLocations.addCreek(creek1);
        creekLocations.addCreek(creek2);

        PointOfInterest site = new PointOfInterest("Site", new Point(12, 12));

        PointOfInterest closestCreek = creekLocations.closestCreek(site);

        Assertions.assertEquals(creek1, closestCreek, "Closest creek should be Creek1");
    }
}