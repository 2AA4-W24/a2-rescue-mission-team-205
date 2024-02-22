package ca.mcmaster.se2aa4.island.team205;


import java.util.Objects;

public class Drone {

    private Integer battery;

    private Location location;



    private final Information info = new UsingJSON();

    private final Radar radar = new Radar();

    private final PhotoScanner scanner = new PhotoScanner(info);
    private Direction direction;

    public Drone(){
    }

    public void takeCommand(){
        int count = 0;
        Movement move = new Movement(this, info);
        while(!batteryTooLow() && Objects.equals(info.status(), "OK")){
            if(scanner.scanTerrain()){
                move.returnHome();
                return;
            }
            else{
                if(count % 3 ==0){
                    chooseDirection();
                }
                else{
                    move.fly();
                }
                count++;
            }
        }
        move.returnHome();
    }

    private void chooseDirection(){

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

