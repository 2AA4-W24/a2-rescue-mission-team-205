import ca.mcmaster.se2aa4.island.team205.CommandCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.Information;
import ca.mcmaster.se2aa4.island.team205.Movement;
import ca.mcmaster.se2aa4.island.team205.Point;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;


class CommandCenterTest {

    private CommandCenter testCommands;

    private final String expectedFly = "{\"action\":\"fly\",\"parameters\":{\"direction\":\"E\"}}";

    private final String unexpectedFly = "{\"action\":\"fly\",\"parameters\":{\"direction\":\"N\"}}";

    private Information info;
    @BeforeEach
    void setUp() {
        String s = "{\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
        info = new UsingJSON();
        info.results(s);
        testCommands = new CommandCenter(s);
    }

    @Test
    void updateInfo(){
    }



}
