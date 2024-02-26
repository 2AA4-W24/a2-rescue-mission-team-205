package ca.mcmaster.se2aa4.island.team205;

import java.util.HashMap;

public class Radar {
    
    private final Information info;

    public Radar(Information information){
        info = information;
    }

    public HashMap<String, String> useRadar(Drone.Direction droneDirection) {
        return info.echo(droneDirection);
    }

    // helper methods to help determine the drones relative direction
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
}

