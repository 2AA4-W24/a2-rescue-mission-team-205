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

    public boolean scanTerrain(){
        List<String> list = info.scan();
        return !list.contains("OCEAN");
    }


}

