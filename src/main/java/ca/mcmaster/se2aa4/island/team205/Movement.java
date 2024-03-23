package ca.mcmaster.se2aa4.island.team205;

public class Movement {

    private final Drone drone;

    private final Information info;

    private final Point point;

    public Movement(Drone userDrone, Information information, Point myPoint){
        drone = userDrone;
        info = information;
        point = myPoint;
    }

    public void fly(){
        info.fly();
        adjustPosition();
    }

    public void returnHome(){
        info.goHome();
    }

    public void turnRight(){
        adjustPosition();
        switch(drone.getDirection()){
            case N -> drone.setDirection(Drone.Direction.E);
            case E -> drone.setDirection(Drone.Direction.S);
            case S -> drone.setDirection(Drone.Direction.W);
            default -> drone.setDirection(Drone.Direction.N);
        }
        adjustPosition();
    }

    public void turnLeft(){
        adjustPosition();
        switch(drone.getDirection()){
            case N -> drone.setDirection(Drone.Direction.W);
            case E -> drone.setDirection(Drone.Direction.N);
            case S -> drone.setDirection(Drone.Direction.E);
            default -> drone.setDirection(Drone.Direction.S);
        }
        adjustPosition();
    }

    private void adjustPosition (){
        switch(drone.getDirection()) {
            case N -> point.incrementY();
            case E -> point.incrementX();
            case S -> point.decrementY();
            default-> point.decrementX();
        }
    }

}
