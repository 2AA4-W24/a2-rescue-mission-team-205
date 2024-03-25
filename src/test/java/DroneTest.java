import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Information;
import ca.mcmaster.se2aa4.island.team205.Point;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

class DroneTest {

    private Drone testDrone;;

    private Information info;

    @BeforeEach
    void setUp() {
        info = new UsingJSON();
        String east = "{\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
        info.results(east);
        Point point = new Point(0,0);
        testDrone = new Drone(info, point);
        testDrone.initialize();
    }

    @Test
    void westStart(){
        String west = "{\"heading\":\"W\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
        info.results(west);
        testDrone.initialize();
        Assertions.assertEquals(Drone.Direction.W, testDrone.getDirection());
    }

    @Test
    void northStart(){
        String north = "{\"heading\":\"N\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
        info.results(north);
        testDrone.initialize();
        Assertions.assertEquals(Drone.Direction.N, testDrone.getDirection());
    }

    @Test
    void southStart(){
        String south = "{\"heading\":\"S\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
        info.results(south);
        testDrone.initialize();
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
        testDrone.initialize();
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
