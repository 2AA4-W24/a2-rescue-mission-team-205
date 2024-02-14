package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONObject;

import java.util.List;

public class UsingJSON implements Information{

    private final JSONObject decision = new JSONObject();
    private final JSONObject parameter = new JSONObject();
    @Override
    public boolean echo() {
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
    public void turnDrone() {

    }

    @Override
    public void fly() {

    }

    public Drone.Direction direction(){
        return null;
    }
}
