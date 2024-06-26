package ca.mcmaster.se2aa4.island.team205;

import java.util.List;

public class PhotoScanner {

    private final Information info;

    private final CreekLocations creeks;
    private final Drone drone;

    private final String oceanCheck;

    private PointOfInterest site = null;


    public PhotoScanner(Information information, Drone drone1, CreekLocations creekLocations) {
        info = information;
        drone = drone1;
        creeks = creekLocations;
        oceanCheck = "OCEAN";
    }

    public void scanTerrain() {
        info.scan();
    }

    public boolean scanResults() {
        List<String> results = info.terrain();
        return !(results.size() == 1 && results.contains(oceanCheck));
    }

    public boolean siteFound() {
        if (!info.site().isEmpty()) {
            Point curLocation = drone.getLocation();
            site = new PointOfInterest(info.site().toString(), new Point(curLocation.getXCoordinate(), curLocation.getYCoordinate()));
            return true;
        } else {
            return false;
        }
    }

    public boolean overCoast() {
        if (info.terrain().size() >= 2) {
            return info.terrain().contains(oceanCheck);
        } else {
            return false;
        }
    }


    public void creekScan() {
        List<String> creekList = info.creek();
        if (!info.creek().isEmpty()) {
            Point curLocation = drone.getLocation();
            String identifier = creekList.get(0);
            if (!creeks.identifiers().contains(identifier)) {
                creeks.addCreek(new PointOfInterest(identifier,  new Point(curLocation.getXCoordinate(), curLocation.getYCoordinate())));
            }
        }
    }

    public PointOfInterest getSite() {
        if (site != null) {
            return site;
        } else {
            return null;
        }
    }

    public boolean scanOcean(){
        return info.terrain().contains(oceanCheck);
    }

}

