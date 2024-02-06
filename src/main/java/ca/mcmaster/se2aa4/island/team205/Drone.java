package ca.mcmaster.se2aa4.island.team205;

import java.util.List;

public class Drone {

    private Integer battery;

    private Location location;

    private Direction direction;

    public Drone(Integer batteryLevel, String initial_direction){
        battery = batteryLevel;
        direction = initialDirection(initial_direction);

    }

    public List<String> takePhoto(){
        PhotoScanner scanner = new PhotoScanner();
        return scanner.photo();
    }

    public String useRadar(){
        Radar radar = new Radar();
        return "No radar";
    }

    public Location getLocation(){
        return location;
    }

    private Direction initialDirection(String new_direction){
        switch(new_direction){
            case "North" -> {
                return Direction.NORTH;
            }
            case "South" -> {
                return Direction.SOUTH;
            }
            case "West" -> {
                return Direction.WEST;
            }
            default -> {
                return Direction.EAST;
            }
        }
    }

    public void setDirection(Direction d){
        direction = d;
    }

    public Direction getDirection(){
        return direction;
    }

    public Integer getBattery(){
        return battery;
    }
    public enum Direction{
        NORTH,
        SOUTH,
        WEST,
        EAST
    }
}

