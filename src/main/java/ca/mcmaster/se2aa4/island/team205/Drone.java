package ca.mcmaster.se2aa4.island.team205;


public class Drone {

    public Integer battery;

    private final Information info;

    private Direction direction;
    private final Movement move;

    public Drone(Information information){
        info = information;
        move = new Movement(this, info);
    }


    public void fly(){
        move.fly();
    }

    public void returnHome(){
        move.returnHome();
    }

    public void turnRight(){
        move.turnRight();
    }

    public void turnLeft(){
        move.turnLeft();
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

    public void drain(Integer cost){
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

