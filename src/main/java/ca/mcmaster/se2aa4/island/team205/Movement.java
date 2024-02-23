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
            case N -> drone.setDirection(Drone.Direction.E);
            case E -> drone.setDirection(Drone.Direction.S);
            case S -> drone.setDirection(Drone.Direction.W);
            default -> drone.setDirection(Drone.Direction.N);
        }
        //map.addMove(drone.getLocation(), drone.getDirection());
    }

    public void turnLeft(){
       // map.addMove(drone.getLocation(), drone.getDirection());
        switch(drone.getDirection()){

            case N -> drone.setDirection(Drone.Direction.W);
            case E -> drone.setDirection(Drone.Direction.N);
            case S -> drone.setDirection(Drone.Direction.E);
            default -> drone.setDirection(Drone.Direction.S);

        }
        //map.addMove(drone.getLocation(), drone.getDirection());
    }


}
