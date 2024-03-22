package ca.mcmaster.se2aa4.island.team205;

import java.util.*;

public class CreekLocations {

    int x = 1000;
    int y = 1000;
    private final Map<PointOfInterest, Point> creeks = new HashMap<>();

    private PointOfInterest closest = null;

    public void addCreek(PointOfInterest poi){
        creeks.put(poi, poi.location);
        closest = poi;
    }

    public PointOfInterest closestCreak(PointOfInterest site){
        int minimum = 1000;

        if(site == null){
            return closest;
        }
        for(PointOfInterest poi : creeks.keySet()){
            int distanceToSite = Math.abs(poi.location.getXCoordinate() -  site.location.getXCoordinate()) + Math.abs(poi.location.getYCoordinate() -  site.location.getYCoordinate());
            if(distanceToSite <= minimum){
                x = Math.abs(poi.location.getXCoordinate() -  site.location.getXCoordinate());
                y = Math.abs(poi.location.getYCoordinate() -  site.location.getYCoordinate());
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
