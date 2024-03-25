import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Information;
import ca.mcmaster.se2aa4.island.team205.Point;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

class DroneTest {

    private Drone testDrone;

    private final String s = "{\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
    private final String x = "{\"heading\":\"W\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
    private final String y = "{\"heading\":\"S\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
    private final String z = "{\"heading\":\"N\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";

    private Information info;

    @BeforeEach
    void setUp() {
        info = new UsingJSON();
        info.results(s);
        Point point = new Point(0,0);
        testDrone = new Drone(info, point);
        testDrone.initialize(east);
    }

    @Test
    void initialDirectionWestTest() {
        testDrone.initialize(x);
        Assertions.assertEquals(Drone.Direction.W, testDrone.getDirection());
    }

    
    @Test
    void initialDirectionNorthTest() {
        testDrone.initialize(z);
        Assertions.assertEquals(Drone.Direction.N, testDrone.getDirection());
    }


    @Test
    void initialDirectionEastTest() {
        testDrone.initialize(s);
        Assertions.assertEquals(Drone.Direction.E, testDrone.getDirection());
    }

    @Test
    void initialDirectionSouthTest() {
        testDrone.initialize(y);
        Assertions.assertEquals(Drone.Direction.S, testDrone.getDirection());
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
        testDrone.initialize(east);
        Integer expectedBattery = info.budget();
        Assertions.assertEquals(expectedBattery, testDrone.getBattery());
    }
    @Test
    void drainTest() {
        Integer initialBattery = testDrone.getBattery();
        Integer expectedDrain = initialBattery - 15;
        testDrone.drain(15);
        Assertions.assertEquals(expectedDrain, testDrone.getBattery());
    }

    @Test
    void testSequentialTurns() {
        testDrone.setDirection(Drone.Direction.N);
        testDrone.turnRight(); 
        testDrone.turnLeft(); 
        testDrone.turnLeft();
        Assertions.assertEquals(Drone.Direction.W, testDrone.getDirection());
    }

    @Test
    void testLeftAndRight() {
        Drone.Direction left;
        Drone.Direction right;
        testDrone.setDirection(Drone.Direction.N);
        left = testDrone.getLeftDirection();
        right = testDrone.getRightDirection();
        Assertions.assertEquals(Drone.Direction.W, left);
        Assertions.assertEquals(Drone.Direction.E, right);
        testDrone.setDirection(Drone.Direction.E);
        left = testDrone.getLeftDirection();
        right = testDrone.getRightDirection();
        Assertions.assertEquals(Drone.Direction.N, left);
        Assertions.assertEquals(Drone.Direction.S, right);
        testDrone.setDirection(Drone.Direction.S);
        left = testDrone.getLeftDirection();
        right = testDrone.getRightDirection();
        Assertions.assertEquals(Drone.Direction.E, left);
        Assertions.assertEquals(Drone.Direction.W, right);
        testDrone.setDirection(Drone.Direction.W);
        left = testDrone.getLeftDirection();
        right = testDrone.getRightDirection();
        Assertions.assertEquals(Drone.Direction.S, left);
        Assertions.assertEquals(Drone.Direction.N, right);
    }

}
