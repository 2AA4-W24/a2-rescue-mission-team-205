package ca.mcmaster.se2aa4.island.team205;

public class Radar {
    
    private final Information info;

    String direction;

    public Radar(Information information){
        info = information;

    }

    public void useRadar(Drone.Direction d){
        info.echo(d);

    }

    public void useRadarFront(Drone.Direction d) {
        info.echo(d);
        direction = "front";
    }

    public void useRadarRight(Drone.Direction d) {
        info.echo(getRightDirection(d));
        direction = "right";
    }

    public void useRadarLeft(Drone.Direction d) {
        info.echo(getLeftDirection(d));
        direction = "left";
    }

    public int distanceToLand(){
        if(info.echoReceived().equals("GROUND")){
            return info.range();
        }
        else{
            return -1;
        }
    }

    public String directionOfLand(){
        return direction;
    }

    private Drone.Direction getLeftDirection(Drone.Direction currentDirection) {
        return switch (currentDirection) {
            case N -> Drone.Direction.W;
            case S -> Drone.Direction.E;
            case W -> Drone.Direction.S;
            case E -> Drone.Direction.N;
        };
    }

    private Drone.Direction getRightDirection(Drone.Direction currentDirection) {
        return switch (currentDirection) {
            case N -> Drone.Direction.E;
            case S -> Drone.Direction.W;
            case W -> Drone.Direction.N;
            case E -> Drone.Direction.S;
        };
    }

    /*
    public String chooseDirection(Drone.Direction currentDirection){
        HashMap<String, String> radarResults = useRadar(currentDirection);
        for(String s : radarResults.keySet()){
            if(radarResults.get(s).equals("GROUND")){
               switch(s){
                   case "Front" -> {
                       return "Front";
                   }
                   case "Left" -> {
                       return "Left";
                   }
                   case "Right" -> {
                       return "Right";
                   }
               }
            }
        }
        return "Front";

    }
    */
}

