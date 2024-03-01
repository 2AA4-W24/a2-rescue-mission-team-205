import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Information;
import ca.mcmaster.se2aa4.island.team205.Movement;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DroneTest {

    private Drone testDrone;

    @BeforeEach
    public void setUp() {
        Information info = new UsingJSON();
        testDrone = new Drone(info);
        testDrone.setDirection(Drone.Direction.N);
    }


    @Test
    public void turnLeftTest() {
        testDrone.turnLeft();
        Drone.Direction realDir = testDrone.getDirection();
        Drone.Direction expectedDir = Drone.Direction.W;
        assertEquals(realDir, expectedDir);
    }

    @Test
    public void turnRightTest() {
        testDrone.turnRight();
        Drone.Direction realDir = testDrone.getDirection();
        Drone.Direction expectedDir = Drone.Direction.E;
        assertEquals(realDir, expectedDir);
    }


}
