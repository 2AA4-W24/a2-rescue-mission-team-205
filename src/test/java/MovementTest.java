import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Information;
import ca.mcmaster.se2aa4.island.team205.Movement;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovementTest {

    private Drone testDrone;

    @BeforeEach
    public void setUp() {
        Information info = new UsingJSON();
        testDrone = new Drone(info);
        Movement move = new Movement(testDrone, info);
        testDrone.setDirection(Drone.Direction.E);
        move.turnLeft();
    }

    @Test
    public void sampleTest() {
        Drone.Direction curDir = testDrone.getDirection();
        Drone.Direction expectedDir = Drone.Direction.N;
        assertEquals(curDir, expectedDir);
    }
}
