package ca.mcmaster.se2aa4.island.team205;

import java.util.ArrayList;
import java.util.List;

public class ActionLog {

    private final List<Action> log = new ArrayList<>();

    public void addLog(Action move) {
        log.add(move);
    }

    public Action getPrev() {
        if (!log.isEmpty()) {
            return log.get(log.size()-1);
        }
        else {
            return Action.NONE;
        }
    }

    public Action getPrev(int previousActions){
        if (!log.isEmpty()) {
            return log.get(log.size()-1 - previousActions);
        }
        else {
            return Action.NONE;
        }
    }
}
