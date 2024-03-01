import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import ca.mcmaster.se2aa4.island.team205.Radar;
import ca.mcmaster.se2aa4.island.team205.Drone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RadarTest {

    private Radar radar;
    private UsingJSON usingJSON;

    @BeforeEach
    void setUp() {
        usingJSON = new UsingJSON();
        radar = new Radar(usingJSON);
    }

    @Test
    void testUseRadar() {
        radar.useRadar(Drone.Direction.N);
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}", usingJSON.decision());
    }

    @Test
    void testUseRadarFront() {
        radar.useRadarFront(Drone.Direction.N);
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}", usingJSON.decision());
        assertEquals("front", radar.directionOfLand());
    }

    @Test
    void testUseRadarRight() {
        radar.useRadarRight(Drone.Direction.N);
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}", usingJSON.decision());
        assertEquals("right", radar.directionOfLand());
    }

    @Test
    void testUseRadarLeft() {
        radar.useRadarLeft(Drone.Direction.N);
        assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}", usingJSON.decision());
        assertEquals("left", radar.directionOfLand());
    }

    @Test
    void testDistanceToLandWhenGround() {
        usingJSON.results("{\"status\":\"OK\",\"extras\":{\"found\":\"GROUND\",\"range\":5}}");
        assertEquals(5, radar.distanceToLand());
    }

    @Test
    void testDistanceToLandWhenNotGround() {
        usingJSON.results("{\"status\":\"OK\",\"extras\":{\"found\":\"WATER\"}}");
        assertEquals(-1, radar.distanceToLand());
    }
}
