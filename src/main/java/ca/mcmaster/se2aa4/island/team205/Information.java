package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public interface Information {


    HashMap<String, String> echo(Drone.Direction direction);

    void scan();

    Integer batteryLevel();

    String direction();

    void goHome();

    Integer cost();

    void turnDrone(Drone.Direction direction);

    void results(String s);

    Integer budget();

    String status();

    void fly();

    String decision();

    JSONArray terrian();
}
