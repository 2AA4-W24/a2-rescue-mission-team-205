package ca.mcmaster.se2aa4.island.team205;

public class Radar {
    
    private Information info;

    public Radar(Information info){
        this.info = info;
    }

    public String useRadar(Drone.Direction droneDirection) {
        String result = "";


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

        return result;
    }

    // helper methods to help determine the drones relative direction
    private Drone.Direction getLeftDirection(Drone.Direction currentDirection) {
        return switch (currentDirection) {
            case NORTH -> Drone.Direction.WEST;
            case SOUTH -> Drone.Direction.EAST;
            case WEST -> Drone.Direction.SOUTH;
            case EAST -> Drone.Direction.NORTH;
        };
    }

    private Drone.Direction getRightDirection(Drone.Direction currentDirection) {
        return switch (currentDirection) {
            case NORTH -> Drone.Direction.EAST;
            case SOUTH -> Drone.Direction.WEST;
            case WEST -> Drone.Direction.NORTH;
            case EAST -> Drone.Direction.SOUTH;
        };
    }

    public Drone.Direction chooseDirection(Drone.Direction currentDirection){

        String radarResults = useRadar(currentDirection);

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
    }
}

