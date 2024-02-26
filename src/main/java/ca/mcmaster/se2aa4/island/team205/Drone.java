package ca.mcmaster.se2aa4.island.team205;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Drone {

    private Integer battery;

    private Location location;

    private final Information info = new UsingJSON();

    private final Radar radar = new Radar(info);

    private final PhotoScanner scanner = new PhotoScanner(info);
    private Direction direction;

    private int count = 0;

    public Drone(){

    }

    public void takeCommand(){


        Logger logger = LogManager.getLogger();
        Movement move = new Movement(new Drone(), info);

        if(!batteryTooLow() && Objects.equals(info.status(), "OK")){

            if(!scanner.scanTerrain()){
                move.returnHome();
            }
            else{
                if(count % 3 ==0){
                    chooseDirection();
                    fly();
                }
                else{
                    fly();
                }
                count++;
            }
        }
        else{
            fly();
        }

    }

    private void fly(){
        Movement movement = new Movement(new Drone(), info);
        movement.fly();
    }

    private void chooseDirection(){
        String direction1 = radar.chooseDirection(direction);
        Movement movement = new Movement(this, info);
        switch(direction1){
            case "Left" -> {
                movement.turnLeft();
            }
            case "Right" -> {
               movement.turnRight();
            }
        }

    }
    public String decision(){
        return info.decision();
    }

    private boolean batteryTooLow(){
        return battery <= 15;
    }


    public Location getLocation(){
        return location;
    }

    private Direction initialDirection(String new_direction){
        switch(new_direction){
            case "North" -> {
                return Direction.N;
            }
            case "South" -> {
                return Direction.S;
            }
            case "West" -> {
                return Direction.W;
            }
            default -> {
                return Direction.E;
            }
        }
    }

    public void setDirection(Direction d){
        direction = d;
        info.turnDrone(direction);
    }

    public Direction getDirection(){
        return direction;
    }

    public void update(String s){
        info.results(s);
        drain(info.cost());

    }
    private void drain(Integer cost){
        battery -= cost;
    }

    public void initialize(String s){
        info.results(s);
        battery = info.budget();
        Direction initial = initialDirection(info.direction());
        setDirection(initial);
    }

    public enum Direction{
        N,
        S,
        W,
        E
    }
}

