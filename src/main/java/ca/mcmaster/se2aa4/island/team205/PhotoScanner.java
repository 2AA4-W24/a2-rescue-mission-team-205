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

        if(info.terrain().size() == 1){
            return !info.terrain().contains("OCEAN");
        }
        else{
            return true;
        }
    }

    public boolean siteFound(){
        if(!info.site().isEmpty()){
            site = new PointOfInterest(info.site().toString(), drone.getLocation());
            return true;
        }
        else{
            return false;
        }
    }

    public boolean overCoast(){

        if(info.terrain().size() >= 2){
            return info.terrain().contains("OCEAN");
        }
        else{
            return false;
        }
    }


    public void creekScan() {
        List<String> creekList = info.creek();
        if (!info.creek().isEmpty()) {
            String identifier = creekList.get(0);
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

    public List<String> getCreekResults(){
        return info.creek();
    }

    public List<String> getSiteResults(){
        return info.site();
    }

    public boolean scanCreek(){
        return !info.creek().isEmpty();
    }

    public boolean scanSite(){
        return !info.site().isEmpty();
    }

    public boolean scanOcean(){
        return info.terrain().contains("OCEAN");
    }

}

