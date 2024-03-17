import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import ca.mcmaster.se2aa4.island.team205.Radar;
import ca.mcmaster.se2aa4.island.team205.Drone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

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
        Assertions.assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}", usingJSON.decision());
    }

    @Test
    void testUseRadarFront() {
        radar.useRadarFront(Drone.Direction.N);
        Assertions.assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}", usingJSON.decision());
    }

    @Test
    void testUseRadarRight() {
        radar.useRadarRight(Drone.Direction.E);
        Assertions.assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}", usingJSON.decision());
    }

    @Test
    void testUseRadarLeft() {
        radar.useRadarLeft(Drone.Direction.W);
        Assertions.assertEquals("{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}", usingJSON.decision());
    }

    @Test
    void testDistanceToLandWhenGround() {
        usingJSON.results("{\"status\":\"OK\",\"extras\":{\"found\":\"GROUND\",\"range\":5}}");
        Assertions.assertEquals(5, radar.distanceToLand());
    }

    @Test
    void testDistanceToLandWhenNotGround() {
        usingJSON.results("{\"status\":\"OK\",\"extras\":{\"found\":\"WATER\"}}");
        Assertions.assertEquals(-1, radar.distanceToLand());
    }
}
