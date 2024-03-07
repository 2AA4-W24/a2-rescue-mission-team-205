import ca.mcmaster.se2aa4.island.team205.CommandCenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CommandCenterTest {

    private CommandCenter testCommands;

    private final String expectedFly = "{\"action\":\"fly\",\"parameters\":{\"direction\":\"E\"}}";

    private final String unexpectedFly = "{\"action\":\"fly\",\"parameters\":{\"direction\":\"N\"}}";

    @BeforeEach
    void setUp() {
        String s = "{\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
        testCommands = new CommandCenter(s);
    }

/*
    @Test
    void takeCommandTest() {
        testCommands.takeCommand();
        String actualFly = testCommands.decision();
        assertEquals(expectedFly, actualFly);
        assertNotEquals(unexpectedFly, actualFly);
    }

*/
}
