package ca.mcmaster.se2aa4.island.team205;

import java.util.List;
import java.util.ArrayList;

public class PhotoScanner {

    private final List <PointOfInterest> pointsOfInterest;

    private Information info;

    private final CreekLocations creeks;
    private Drone drone;

    PointOfInterest site = null;

    public PhotoScanner(Information information, Drone drone1, CreekLocations creekLocations){
        pointsOfInterest = new ArrayList<>();
        info = information;
        drone = drone1;
        creeks = creekLocations;
    }

    public void scanTerrain(){
        info.scan();
    }

    public boolean scanResults(){

        if(info.terrain().toList().size() == 1){
            return !info.terrain().toList().contains("OCEAN");
        }
        else{
            return true;
        }
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

    public boolean overCoast(){

        if(info.terrain().toList().size() >= 2){
            return info.terrain().toList().contains("OCEAN");
        }
        else{
            return false;
        }
    }


    public void creekScan(){
        List<Object> creekList = info.creek().toList();
        if(!info.creek().toList().isEmpty()){
            String identifier = creekList.get(0).toString();
            creeks.addCreek(new PointOfInterest(identifier, drone.getLocation()));
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

