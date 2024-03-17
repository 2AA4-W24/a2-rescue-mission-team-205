package ca.mcmaster.se2aa4.island.team205;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreekLocations {

    private final Map<PointOfInterest, int[]> creeks = new HashMap<>();


    public void addCreek(PointOfInterest poi){
        creeks.put(poi,  poi.location);
    }

    public PointOfInterest closestCreak(PointOfInterest site){
        int minimum = 1000;
        PointOfInterest closest = null;
        if(site == null){
            for(PointOfInterest poi : creeks.keySet()){
                return poi;
            }
        }
        for(PointOfInterest poi : creeks.keySet()){
            int distanceToSite = Math.abs(poi.location[0] -  site.location[0]) + Math.abs(poi.location[1] -  site.location[1]);
            if(distanceToSite <= minimum){
                minimum = distanceToSite;
                closest = poi;
            }
        }
        return closest;
    }

    public Map<PointOfInterest, int[]> all(){
        return creeks;
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
