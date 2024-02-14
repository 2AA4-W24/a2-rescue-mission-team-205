package ca.mcmaster.se2aa4.island.team205;



public class Movement {

    private final Drone drone;


    public Movement(Drone user_drone){
        drone = user_drone;
    }

    public void fly(){

    }

    public void returnHome(){

    }

    public void turnRight(){
        switch(drone.getDirection()){
            case NORTH -> drone.setDirection(Drone.Direction.EAST);
            case EAST -> drone.setDirection(Drone.Direction.SOUTH);
            case SOUTH -> drone.setDirection(Drone.Direction.WEST);
            default -> drone.setDirection(Drone.Direction.NORTH);

        }
    }

    public void turnLeft(){
        switch(drone.getDirection()){
            case NORTH -> drone.setDirection(Drone.Direction.WEST);
            case EAST -> drone.setDirection(Drone.Direction.NORTH);
            case SOUTH -> drone.setDirection(Drone.Direction.EAST);
            default -> drone.setDirection(Drone.Direction.SOUTH);

        }
    }


}
