import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Information;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DroneTest {

    private Drone testDrone;

    private final String s = "{\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";

    private Information info;

    @BeforeEach
    public void setUp() {
        info = new UsingJSON();
        testDrone = new Drone(info);
        testDrone.initialize(s);
    }


    @Test
    public void turnLeftTest() {
        testDrone.turnLeft();
        Drone.Direction realDir = testDrone.getDirection();
        Drone.Direction expectedDir = Drone.Direction.N;
        assertEquals(realDir, expectedDir);
    }

    @Test
    public void turnRightTest() {
        testDrone.turnRight();
        Drone.Direction realDir = testDrone.getDirection();
        Drone.Direction expectedDir = Drone.Direction.S;
        assertEquals(realDir, expectedDir);
    }

    @Test
    public void getDirectionTest() {
        Drone.Direction realDir = testDrone.getDirection();
        Drone.Direction expectedDir = Drone.Direction.E;
        assertEquals(realDir, expectedDir);
    }

    @Test
    public void initializeTest() {
        testDrone.initialize(s);
        Integer expectedBattery = info.budget();
        assertEquals(expectedBattery, testDrone.battery, "InitializeTest");
    }
    @Test
    public void drainTest() {
        Integer initialBattery = testDrone.battery;
        Integer expectedDrain = initialBattery - 15;
        testDrone.drain(15);
        assertEquals(expectedDrain, testDrone.battery, "DrainTest");
    }



}
