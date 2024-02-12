package ca.mcmaster.se2aa4.island.team205;

public class Location {
    int xCoordinate;
    int yCoordinate;

    public Location(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public int calcDistanceTo(Location loc) {
        return 0;
    }
}
