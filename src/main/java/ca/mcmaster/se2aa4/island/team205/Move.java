package ca.mcmaster.se2aa4.island.team205;

public class Move {
    private final Location location;
    private final Drone.Direction direction;

    public Move(Location location, Drone.Direction direction) {
        this.location = location;
        this.direction = direction;
    }

    public Location getLocation() {
        return location;
    }

    public Drone.Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Move{" +
               "location=" + location +
               ", direction=" + direction +
               '}';
    }
}

