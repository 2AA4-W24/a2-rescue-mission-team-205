package ca.mcmaster.se2aa4.island.team205;

import java.util.List;
import java.util.ArrayList;

public class PhotoScanner {
    private final List <PointOfInterest> pointsOfInterest;
    private Information info;

    public PhotoScanner(Information information){
        pointsOfInterest = new ArrayList<>();
        info = information;
    }

    public void scanTerrain(){
        info.scan();
    }

    public boolean scanResults(){
        if(info.terrain().toList().contains("OCEAN")){
            return false;
        }
        else{
            return true;
        }
    }



}

