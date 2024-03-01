import ca.mcmaster.se2aa4.island.team205.CommandCenter;
import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandCenterTest {

    private CommandCenter testCommands;

    private final String s = "{\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
    @BeforeEach
    void setUp() {
        testCommands = new CommandCenter(s);
    }


    @Test
    void takeCommandTest() {

    }

    @Test
    void findLand() {

    }

}
