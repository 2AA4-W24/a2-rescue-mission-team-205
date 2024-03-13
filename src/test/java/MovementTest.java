import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Movement;
import ca.mcmaster.se2aa4.island.team205.Point;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class MovementTest {

    private Drone testDrone;
    private Movement movement;
    private UsingJSON usingJSON;

    private Point point;

    @BeforeEach
    void setUp() {
        point = new Point();
        usingJSON = new UsingJSON();
        testDrone = new Drone(usingJSON, point);
        movement = new Movement(testDrone, usingJSON, point);
    }

    @Test
    void testFlyMethod(){
        int [] initialCoordinates = {0,0};
        testDrone.setDirection(Drone.Direction.N);
        Assertions.assertArrayEquals(initialCoordinates, point.getCoordinates());
        movement.fly();
        int [] expectedCoordinates = {0, 1};
        Assertions.assertArrayEquals(expectedCoordinates, point.getCoordinates());
    }

    @Test
    void testReturnHome() {
        movement.returnHome();
        String decision = usingJSON.decision();
        Assertions.assertTrue(decision.contains("\"action\":\"stop\"")); 
    }

    @Test
    void testTurnRight() {
        testDrone.setDirection(Drone.Direction.N);
        movement.turnRight();
        Assertions.assertEquals(Drone.Direction.E, testDrone.getDirection());
    }

    @Test
    void testTurnLeft() {
        testDrone.setDirection(Drone.Direction.N);
        movement.turnLeft();
        Assertions.assertEquals(Drone.Direction.W, testDrone.getDirection());
    }

    @Test
    void testAdjustPosition() {
        testDrone.setDirection(Drone.Direction.N);
        movement.fly(); // incremets y coord
        Assertions.assertArrayEquals(new int[]{0, 1}, point.getCoordinates());
    }

}
