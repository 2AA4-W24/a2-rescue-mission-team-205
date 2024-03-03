package ca.mcmaster.se2aa4.island.team205;

import java.util.List;
import java.util.ArrayList;

public class PhotoScanner {

    private final List <PointOfInterest> pointsOfInterest;

    private Information info;

    private Drone drone;

    PointOfInterest site = null;

    public PhotoScanner(Information information, Drone drone1){
        pointsOfInterest = new ArrayList<>();
        info = information;
        drone = drone1;
    }

    public void scanTerrain(){
        info.scan();
    }

    public boolean scanResults(){
        return !info.terrain().toList().contains("OCEAN");
    }

    public boolean siteFound(){
        if(!info.site().toList().isEmpty()){
            site = new PointOfInterest(info.site().toString(), drone.getLocation());
            return true;
        }
        else{
            return false;
        }
    }

    public PointOfInterest getSite(){
        if(site != null){
            return site;
        }
        else{
            return null;
        }
    }


}

