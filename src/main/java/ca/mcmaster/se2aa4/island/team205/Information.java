package ca.mcmaster.se2aa4.island.team205;

import java.util.List;

public interface Information {

    boolean echo();

    List<String> scan();

    Integer batteryLevel();

    Drone.Direction direction();

    void turnDrone();



    void fly();
}
