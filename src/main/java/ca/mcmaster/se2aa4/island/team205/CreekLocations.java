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
        Point poiLocation;
        if(site == null){
            return closest;
        }
        Point siteLocation = site.location;
        for(PointOfInterest poi : creeks.keySet()){
            poiLocation = poi.location;
            int distanceToSite = Math.abs(poiLocation.getXCoordinate() -  siteLocation.getXCoordinate()) + Math.abs(poiLocation.getYCoordinate() -  siteLocation.getYCoordinate());
            if(distanceToSite <= minimum){
                x = Math.abs(poiLocation.getXCoordinate() -  siteLocation.getXCoordinate());
                y = Math.abs(poiLocation.getYCoordinate() -  siteLocation.getYCoordinate());
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
