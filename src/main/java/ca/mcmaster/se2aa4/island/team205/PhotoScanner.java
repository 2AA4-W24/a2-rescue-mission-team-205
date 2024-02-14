package ca.mcmaster.se2aa4.island.team205;

import java.util.List;
import eu.ace_design.island.game.PointOfInterest;
import java.util.ArrayList;

public class PhotoScanner {
    // instance variables
    private List <PointOfInterest> pointsOfInterest;
    private int direction = 0;
    
    public PhotoScanner(){
        this.pointsOfInterest = new ArrayList<>();
        this.direction = 0;
    }

    public void scanTerrain(){

        System.out.println("scanning terrain");
    }

    public List <PointOfInterest> findPointsOfInterests(){

        System.out.println("looking for POIS");
        return pointsOfInterest;
    }
}

