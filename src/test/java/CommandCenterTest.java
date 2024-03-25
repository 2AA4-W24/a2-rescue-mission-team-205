import ca.mcmaster.se2aa4.island.team205.CommandCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Information;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CommandCenterTest {

    private CommandCenter testCommands;

    private final String echoF = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}";

    private final String ground = "{ \"cost\": 5, \"extras\": {\"found\":\"GROUND\",\"range\":2}}";

    private final String echoR = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}";

    private final String noGround = "{ \"cost\": 5, \"extras\": {\"found\":\"OUT_OF_RANGE\",\"range\":2}}";

    @BeforeEach
    void setUp() {
        String s = "{\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
        Information info = new UsingJSON();
        info.results(s);
        testCommands = new CommandCenter(s);
    }

    @Test
    void updateInfo(){
        String res = "{ \"cost\": 5, \"extras\": {},\"status\": \"OK\",\"part\": \"Engine\",\"time\": 1711327499013,\"meth\": \"takeDecision\"}";
        testCommands.updateInformation(res);
        assertEquals(6995, testCommands.getDrone().getBattery());
    }

    @Test
    void phaseOne(){
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.takeCommand();
        assertEquals(echoF, testCommands.decision());
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.updateInformation(noGround);
        testCommands.takeCommand();
        assertEquals(echoR, testCommands.decision());
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.updateInformation(noGround);
        testCommands.takeCommand();
        String echoL = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
        assertEquals(echoL, testCommands.decision());
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.updateInformation(noGround);
        testCommands.takeCommand();
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
    }

    @Test
    void phaseOneFoundFront(){
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.takeCommand();
        assertEquals(echoF, testCommands.decision());
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.updateInformation(ground);
        testCommands.takeCommand();
        String expectedFlyE = "{\"action\":\"fly\",\"parameters\":{\"direction\":\"E\"}}";
        assertEquals(expectedFlyE, testCommands.decision());
        testCommands.takeCommand();
        assertEquals(expectedFlyE, testCommands.decision());
        testCommands.takeCommand();
        String turn = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}";
        assertEquals(turn, testCommands.decision());
        testCommands.takeCommand();
        String scan = "{\"action\":\"scan\",\"parameters\":{\"direction\":\"N\"}}";
        assertEquals(scan, testCommands.decision());
    }

    @Test
    void phaseOneRight(){
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.takeCommand();
        assertEquals(echoF, testCommands.decision());
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.updateInformation(noGround);
        testCommands.takeCommand();
        assertEquals(echoR, testCommands.decision());
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.updateInformation(ground);
        testCommands.takeCommand();
        String expectedFlyS = "{\"action\":\"fly\",\"parameters\":{\"direction\":\"S\"}}";
        assertEquals(expectedFlyS, testCommands.decision());
        testCommands.takeCommand();
        assertEquals(Drone.Direction.S, testCommands.getDrone().getDirection());
        testCommands.takeCommand();
        assertEquals(Drone.Direction.W, testCommands.getDrone().getDirection());
        testCommands.takeCommand();
        assertEquals(Drone.Direction.S, testCommands.getDrone().getDirection());
    }

    @Test
    void phaseOneLeft(){
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.takeCommand();
        assertEquals(echoF, testCommands.decision());
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.updateInformation(noGround);
        testCommands.takeCommand();
        assertEquals(echoR, testCommands.decision());
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.updateInformation(noGround);
        testCommands.takeCommand();
        String echoL = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
        assertEquals(echoL, testCommands.decision());
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.updateInformation(ground);
        testCommands.takeCommand();
        assertEquals(Drone.Direction.E, testCommands.getDrone().getDirection());
        testCommands.takeCommand();
        assertEquals(Drone.Direction.N, testCommands.getDrone().getDirection());
        testCommands.takeCommand();
        assertEquals(Drone.Direction.W, testCommands.getDrone().getDirection());
        testCommands.takeCommand();
        assertEquals(Drone.Direction.N, testCommands.getDrone().getDirection());
    }
    @Test
    void report(){
       assertEquals("", testCommands.finalReport());
    }


}
