package ca.mcmaster.se2aa4.island.team205;



public class Movement {

    private final Drone drone;

    private final Information info;

    private final int[] coordinatesOfPath = {0,0};

    public Movement(Drone userDrone, Information information){
        drone = userDrone;
        info = information;

    }
    public void fly(){
        info.fly();
        adjustPosition();
    }

    public void returnHome(){
        info.goHome();
    }

    public void turnRight(){

        switch(drone.getDirection()){

            case N -> {
                drone.setDirection(Drone.Direction.E);
                //info.turnDrone(Drone.Direction.E);

            }
            case E -> {
                drone.setDirection(Drone.Direction.S);
               // info.turnDrone(Drone.Direction.S);
            }
            case S -> {
                drone.setDirection(Drone.Direction.W);
               // info.turnDrone(Drone.Direction.W);

            }
            case W -> {
                drone.setDirection(Drone.Direction.N);
                //nfo.turnDrone(Drone.Direction.N);
            }

        }
    }


    public void turnLeft(){
        switch(drone.getDirection()){

            case N -> {
                drone.setDirection(Drone.Direction.W);
               // info.turnDrone(Drone.Direction.W);

            }
            case E -> {
                drone.setDirection(Drone.Direction.N);
                //info.turnDrone(Drone.Direction.N);
            }
            case S -> {
                drone.setDirection(Drone.Direction.E);
                //info.turnDrone(Drone.Direction.E);

            }
            case W -> {
                drone.setDirection(Drone.Direction.S);
                //info.turnDrone(Drone.Direction.S);
            }

        }
    }


    private void adjustPosition (){
        switch(drone.getDirection()) {
            case N -> coordinatesOfPath[1]++;

            case E -> coordinatesOfPath[0]++;

            case S -> coordinatesOfPath[1]--;

            default -> coordinatesOfPath[0]--;
        }
    }

    public int[] getCoordinates(){
        return coordinatesOfPath;
    }
}
