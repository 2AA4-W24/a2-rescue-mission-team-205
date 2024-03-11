package ca.mcmaster.se2aa4.island.team205;

public class Point {
    int xCoordinate = 0;
    int yCoordinate = 0;

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void incrementX() {
        xCoordinate += 1;
    }

    public void incrementY() {
        yCoordinate += 1;
    }

    public void decrementX() {
        xCoordinate -= 1;
    }

    public void decrementY() {
        yCoordinate -= 1;
    }

    public int[] getCoordinates() {
        return new int[]{xCoordinate, yCoordinate};
    }
}