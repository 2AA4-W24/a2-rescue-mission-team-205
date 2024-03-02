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

    public void scanTerrain(){
        info.scan();
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

