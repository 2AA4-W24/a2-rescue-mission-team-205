import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Movement;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MovementTest {

    private Drone testDrone;
    private Movement movement;
    private UsingJSON usingJSON;

    @BeforeEach
    void setUp() {
        usingJSON = new UsingJSON();
        testDrone = new Drone(usingJSON);
        movement = new Movement(testDrone, usingJSON);
    }

    @Test
    void testFlyMethod(){
        int [] initialCoordinates = {0,0};
        testDrone.setDirection(Drone.Direction.N);
        assertArrayEquals(initialCoordinates, movement.getCoordinates());
        movement.fly();
        int [] expectedCoordinates = {0, 1};
        assertArrayEquals(expectedCoordinates, movement.getCoordinates());
    }

    @Test
    void testReturnHome() {
        movement.returnHome();
        String decision = usingJSON.decision();
        assertTrue(decision.contains("\"action\":\"stop\"")); 
    }

    @Test
    void testTurnRight() {
        testDrone.setDirection(Drone.Direction.N);
        movement.turnRight();
        assertEquals(Drone.Direction.E, testDrone.getDirection());
    }

    @Test
    void testTurnLeft() {
        testDrone.setDirection(Drone.Direction.N);
        movement.turnLeft();
        assertEquals(Drone.Direction.W, testDrone.getDirection());
    }

    @Test
    void testAdjustPosition() {
        testDrone.setDirection(Drone.Direction.N);
        movement.fly(); // incremets y coord
        assertArrayEquals(new int[]{0, 1}, movement.getCoordinates());
    }

}
