package ca.mcmaster.se2aa4.island.team205;



public class Movement {

    private final Drone drone;
   // private final Map map;
    private final Information info;

    public Movement(Drone user_drone, Information information){
        drone = user_drone;
        info = information;
       // this.map = map;
    }

    public void fly(){
        info.fly();
       /// map.addMove(drone.getLocation(), drone.getDirection());
    }

    public void returnHome(){
        info.goHome();
    }

    public void turnRight(){
        //map.addMove(drone.getLocation(), drone.getDirection());
        switch(drone.getDirection()){
            case NORTH -> drone.setDirection(Drone.Direction.EAST);
            case EAST -> drone.setDirection(Drone.Direction.SOUTH);
            case SOUTH -> drone.setDirection(Drone.Direction.WEST);
            default -> drone.setDirection(Drone.Direction.NORTH);
        }
        //map.addMove(drone.getLocation(), drone.getDirection());
    }

    public void turnLeft(){
       // map.addMove(drone.getLocation(), drone.getDirection());
        switch(drone.getDirection()){
            case NORTH -> drone.setDirection(Drone.Direction.WEST);
            case EAST -> drone.setDirection(Drone.Direction.NORTH);
            case SOUTH -> drone.setDirection(Drone.Direction.EAST);
            default -> drone.setDirection(Drone.Direction.SOUTH);
        }
        //map.addMove(drone.getLocation(), drone.getDirection());
    }


}
