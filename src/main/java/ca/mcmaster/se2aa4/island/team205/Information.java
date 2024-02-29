package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONArray;

public interface Information {


    void echo(Drone.Direction direction);

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

    JSONArray terrain();

    String echoReceived();

    int range();
}
