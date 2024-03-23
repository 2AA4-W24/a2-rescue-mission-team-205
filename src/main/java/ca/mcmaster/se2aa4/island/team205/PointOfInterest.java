package ca.mcmaster.se2aa4.island.team205;

public class PointOfInterest {

    private final String identifier;

    private final Point location;

    public PointOfInterest (String poiIdentifier, Point poiLocation) {
        identifier = poiIdentifier;
        location = poiLocation;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Point getLocation() {
        return location;
    }

}
