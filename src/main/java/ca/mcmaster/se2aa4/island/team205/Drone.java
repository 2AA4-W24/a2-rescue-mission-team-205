package ca.mcmaster.se2aa4.island.team205;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Drone {

    private Integer battery;

    private Location location;

    private final Information info = new UsingJSON();

    private final Radar radar = new Radar(info);

    private final PhotoScanner scanner = new PhotoScanner(info);
    private Direction direction;
    private final Movement move = new Movement(this, info);

    private final List<String> actions = new ArrayList<>();

    private int count = 1;

    public Drone(){

    }

    public void takeCommand(){

        count ++;
        Logger logger = LogManager.getLogger();
        if(batteryTooLow()){
            move.returnHome();
        }
        if (count % 5 == 0){
            scanner.scanTerrain();
            actions.add("scan");
        }
        else if(count %21 == 0){
            if(!actions.isEmpty()){
                if(actions.get(actions.size() -1).equals("fly") || actions.get(actions.size() -1).equals("turn")){
                    move.turnRight();
                    actions.add("turn");
                }
                else{
                    if(scanner.scanResults()){
                        move.returnHome();
                    }
                    else {
                        move.fly();
                        actions.add("fly");
                    }
                }
            }

        }
        else{
            if(!actions.isEmpty()){

                if(actions.get(actions.size() -1).equals("scan")){
                    if(scanner.scanResults()){
                        move.returnHome();
                    }
                    else {
                        move.fly();
                        actions.add("fly");
                    }
                }
                else{
                    move.fly();
                    actions.add("fly");
                }
            }
            else{
                move.fly();
                actions.add("fly");
            }

        }

    }

    private void chooseDirection(Direction direction){
        String direction1 = radar.chooseDirection(direction);
        switch(direction1){
            case "Left" -> {
                move.turnLeft();
                actions.add("turn");
            }
            case "Right" -> {
               move.turnRight();
               actions.add("turn");
            }
        }

    }

    public String mapping(){
        return actions.toString();
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

