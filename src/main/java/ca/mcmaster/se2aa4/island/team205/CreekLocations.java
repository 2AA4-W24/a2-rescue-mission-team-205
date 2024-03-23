package ca.mcmaster.se2aa4.island.team205;

import java.util.*;

public class CreekLocations {

    private final Map<PointOfInterest, Point> creeks = new HashMap<>();

    private PointOfInterest closest = null;

    public void addCreek(PointOfInterest poi){
        creeks.put(poi, poi.getLocation());
        closest = poi;
    }

    public PointOfInterest closestCreak(PointOfInterest site){
        int minimum = 1000;
        Point poiLocation;
        if(site == null){
            return closest;
        }
        Point siteLocation = site.getLocation();
        for(PointOfInterest poi : creeks.keySet()){
            poiLocation = poi.getLocation();
            int distanceToSite = Math.abs(poiLocation.getXCoordinate() -  siteLocation.getXCoordinate()) + Math.abs(poiLocation.getYCoordinate() -  siteLocation.getYCoordinate());
            if(distanceToSite <= minimum){
                minimum = distanceToSite;
                closest = poi;
            }
        }
        return closest;
    }

    public List<String> identifiers(){
        List<String> identifiers = new ArrayList<>();
        for(PointOfInterest pointOfInterest : creeks.keySet()){
            identifiers.add(pointOfInterest.getIdentifier());
        }
        return identifiers;
    }

}
