package ca.mcmaster.se2aa4.island.team205;

public class Drone {

    private Integer battery;

    private final Information info;

    private Direction direction;

    private final Movement move;

    private final Point point;

    public Drone(Information information, Point myPoint){
        info = information;
        point = myPoint;
        move = new Movement(this, info, point);
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

    private Direction initialDirection(String newDirection){
        Direction startingDirection;
        switch(newDirection){
            case "N" -> {
                startingDirection = Direction.N;
                return startingDirection;
            }
            case "S" -> {
                startingDirection = Direction.S;
                return startingDirection;
            }
            case "W" -> {
                startingDirection = Direction.W;
                return startingDirection;
            }
            default -> {
                startingDirection = Direction.E;
                return startingDirection;
            }
        }
    }

    public void setDirection(Direction d){
        direction = d;
        info.turnDrone(direction);
    }

    public Point getLocation(){
        return point;
    }

    public Direction getDirection(){
        return direction;
    }

    public void drain(Integer cost){
        battery -= cost;
    }

    public void initialize(){
        battery = info.budget();
        Direction initial = initialDirection(info.direction());
        setDirection(initial);
    }

    public Integer getBattery(){
        return battery;
    }

    public Direction getRightDirection() {
        return switch (getDirection()) {
            case N -> Drone.Direction.E;
            case S -> Drone.Direction.W;
            case W -> Drone.Direction.N;
            case E -> Drone.Direction.S;
        };
    }

    public Direction getLeftDirection() {
        return switch (getDirection()) {
            case N -> Drone.Direction.W;
            case S -> Drone.Direction.E;
            case W -> Drone.Direction.S;
            case E -> Drone.Direction.N;
        };
    }

    public enum Direction{
        N,
        S,
        W,
        E
    }
}

