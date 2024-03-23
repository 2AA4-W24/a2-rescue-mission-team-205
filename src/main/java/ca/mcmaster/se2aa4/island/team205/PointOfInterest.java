package ca.mcmaster.se2aa4.island.team205;

public class PointOfInterest {

    private final String identifier;

    private final Point location;

    public PointOfInterest (String identifier1, Point location1) {
        identifier = identifier1;
        location = location1;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Point getLocation() {
        return location;
    }

}
