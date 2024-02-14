package ca.mcmaster.se2aa4.island.team205;

import org.json.JSONObject;

import java.util.List;

public class Drone {

    private final Integer battery;

    private Location location;

    private final Radar radar = new Radar();

    private final PhotoScanner scanner = new PhotoScanner();


    private Direction direction;

    public Drone(Integer batteryLevel, String initial_direction){
        battery = batteryLevel;
        direction = initialDirection(initial_direction);
    }

    public void takeCommand(){
        int count = 0;
        Movement move = new Movement(this);
        while(!batteryTooLow()){
            if(true){
                move.returnHome();
                return;
            }
            else{
                if(count % 3 ==0){
                    chooseDirection();
                }
                else{
                    move.fly();
                    count++;
                }
            }
        }
        move.returnHome();
    }

    private void chooseDirection(){

    }

    private boolean batteryTooLow(){
        return battery <= 15;
    }

    private void updateBattery(){}
    public List<String> takePhoto(){
        PhotoScanner scanner = new PhotoScanner();
        //return scanner.photo();
        return null;
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

