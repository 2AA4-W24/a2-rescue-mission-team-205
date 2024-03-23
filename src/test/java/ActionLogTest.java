import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ca.mcmaster.se2aa4.island.team205.Action;
import ca.mcmaster.se2aa4.island.team205.ActionLog;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionLogTest {

    private ActionLog testLog;

    private Action prevAction;

    @BeforeEach
    void setUp() {
        testLog = new ActionLog();
    }

    @Test
    void testEmptyLog(){
        prevAction = testLog.getPrev();
        assertEquals(Action.NONE, prevAction);
        prevAction = testLog.getPrev(1);
        assertEquals(Action.NONE, prevAction);
    }

    @Test
    void testLastLog(){
        testLog.addLog(Action.FLY);
        prevAction = testLog.getPrev();
        assertEquals(Action.FLY, prevAction);
    }

    @Test
    void testPrevLogs(){
        testLog.addLog(Action.FLY);
        testLog.addLog(Action.ECHOF);
        testLog.addLog(Action.SCAN);
        prevAction = testLog.getPrev(2);
        assertEquals(Action.FLY, prevAction);
        prevAction = testLog.getPrev(1);
        assertEquals(Action.ECHOF, prevAction);
        prevAction = testLog.getPrev(0);
        assertEquals(Action.SCAN, prevAction);
    }
}
