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
        assertEquals(prevAction, Action.NONE);
        prevAction = testLog.getPrev(1);
        assertEquals(prevAction, Action.NONE);
    }

    @Test
    void testLastLog(){
        testLog.addLog(Action.FLY);
        prevAction = testLog.getPrev();
        assertEquals(prevAction, Action.FLY);
    }

    @Test
    void testPrevLogs(){
        testLog.addLog(Action.FLY);
        testLog.addLog(Action.ECHOF);
        testLog.addLog(Action.SCAN);
        prevAction = testLog.getPrev(2);
        assertEquals(prevAction, Action.FLY);
        prevAction = testLog.getPrev(1);
        assertEquals(prevAction, Action.ECHOF);
        prevAction = testLog.getPrev(0);
        assertEquals(prevAction, Action.SCAN);
    }
}
