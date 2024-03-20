import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Information;
import ca.mcmaster.se2aa4.island.team205.Point;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

class DroneTest {

    private Drone testDrone;

    private static final String s = "{\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";

    private Information info;

    @BeforeEach
    void setUp() {
        info = new UsingJSON();
        Point point = new Point();
        testDrone = new Drone(info, point);
        testDrone.initialize(s);
    }


    @Test
    void turnLeftTest() {
        testDrone.turnLeft();
        Drone.Direction realDir = testDrone.getDirection();
        Drone.Direction expectedDir = Drone.Direction.N;
        Assertions.assertEquals(realDir, expectedDir);
    }

    @Test
    void turnRightTest() {
        testDrone.turnRight();
        Drone.Direction realDir = testDrone.getDirection();
        Drone.Direction expectedDir = Drone.Direction.S;
        Assertions.assertEquals(realDir, expectedDir);
    }

    @Test
    void getDirectionTest() {
        Drone.Direction realDir = testDrone.getDirection();
        Drone.Direction expectedDir = Drone.Direction.E;
        Assertions.assertEquals(realDir, expectedDir);
    }

    @Test
    void initializeTest() {
        testDrone.initialize(s);
        Integer expectedBattery = info.budget();
        Assertions.assertEquals(expectedBattery, testDrone.getBattery(), "InitializeTest");
    }
    @Test
    void drainTest() {
        Integer initialBattery = testDrone.getBattery();
        Integer expectedDrain = initialBattery - 15;
        testDrone.drain(15);
        Assertions.assertEquals(expectedDrain, testDrone.getBattery(), "DrainTest");
    }

    @Test
    void testSequentialTurns() {
        testDrone.setDirection(Drone.Direction.N);
        testDrone.turnRight(); 
        testDrone.turnLeft(); 
        testDrone.turnLeft();
        Assertions.assertEquals(Drone.Direction.W, testDrone.getDirection(), "Direction after sequential turns");
    }

}
