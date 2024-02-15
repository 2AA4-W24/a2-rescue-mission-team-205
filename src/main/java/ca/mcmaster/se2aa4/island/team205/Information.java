package ca.mcmaster.se2aa4.island.team205;

import java.util.List;

public interface Information {


    boolean echo(Drone.Direction direction);

    List<String> scan();

    Integer batteryLevel();

    Drone.Direction direction();

    void goHome();

    void turnDrone(Drone.Direction direction);



    void fly();
}
