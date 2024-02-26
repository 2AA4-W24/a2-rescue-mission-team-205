package ca.mcmaster.se2aa4.island.team205;

import java.util.HashMap;

public class Radar {
    
    private final Information info;

    public Radar(Information information){
        info = information;
    }

    public HashMap<String, String> useRadar(Drone.Direction droneDirection) {


        /*
        if(info.echo(droneDirection)){
            result += "Front = Ground";
        } 
        else {
            result += "Front: OUT_OF_RANGE";
        }

        //scan left direction
        Drone.Direction leftDirection = getLeftDirection(droneDirection);
        if (info.echo(leftDirection)) {
            result += "Left: GROUND ";
        } else {
            result += "Left: OUT_OF_RANGE ";
        }

        // Scanning right
        Drone.Direction rightDirection = getRightDirection(droneDirection);
        if (info.echo(rightDirection)) {
            result += "Right: GROUND";
        } else {
            result += "Right: OUT_OF_RANGE";
        }
        */
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
        /*
            // if ground is detected in front, return the current direction
        if (radarResults.contains("Front = Ground")) {
            return currentDirection;
        }

        // check for ground on the left
        if (radarResults.contains("Left: GROUND")) {
            return getLeftDirection(currentDirection);
        }

        // check for ground on the right
        if (radarResults.contains("Right: GROUND")) {
            return getRightDirection(currentDirection);
        }

        // If no ground detected fly forward
        return currentDirection;
        */

    }
}

