package ca.mcmaster.se2aa4.island.team205;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreekLocations {

    int x = 1000;
    int y = 1000;
    private final Map<PointOfInterest, int[]> creeks = new HashMap<>();

    private PointOfInterest closest = null;


    public void addCreek(PointOfInterest poi){

        creeks.put(poi,  poi.location);
        closest = poi;
    }

    public PointOfInterest closestCreak(PointOfInterest site){
        int minimum = 1000;

        if(site == null){
            return closest;
        }
        for(PointOfInterest poi : creeks.keySet()){
            int distanceToSite = Math.abs(poi.location[0] -  site.location[0]) + Math.abs(poi.location[1] -  site.location[1]);
            if(distanceToSite <= minimum){
                x = Math.abs(poi.location[0] -  site.location[0]);
                y = Math.abs(poi.location[1] -  site.location[1]);
                minimum = distanceToSite;
                closest = poi;
            }
        }
        return closest;
    }

    public List<String> identifiers(){
        List<String> identifiers = new ArrayList<>();
        for(PointOfInterest pointOfInterest : creeks.keySet()){
            identifiers.add(pointOfInterest.identifier);
        }
        return identifiers;
    }

    public int numberOfCreeks(){
        return creeks.size();
    }

}
