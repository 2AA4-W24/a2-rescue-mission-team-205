package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONObject;

import java.util.List;

public class UsingJSON implements Information{

    private final JSONObject decision = new JSONObject();
    private final JSONObject parameter = new JSONObject();
    @Override
    public boolean echo(Drone.Direction direction) {
        return false;
    }

    @Override
    public List<String> scan() {
        return null;
    }

    @Override
    public Integer batteryLevel() {

        return null;
    }

    @Override
    public void turnDrone(Drone.Direction direction) {

    }

    @Override
    public void goHome() {
        decision.put("action", "stop");
    }

    @Override
    public void fly() {
        decision.put("action", "fly");
    }

    public Drone.Direction direction(){
        return null;
    }
}
