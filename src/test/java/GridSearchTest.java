import ca.mcmaster.se2aa4.island.team205.ActionLog;
import ca.mcmaster.se2aa4.island.team205.Drone;
import ca.mcmaster.se2aa4.island.team205.GridSearch;
import ca.mcmaster.se2aa4.island.team205.Information;
import ca.mcmaster.se2aa4.island.team205.Radar;
import ca.mcmaster.se2aa4.island.team205.UsingJSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GridSearchTest {
    
    private GridSearch gridSearch;
    private Information info;
    private Drone drone;
    private Radar radar;
    private ActionLog actionLog;

    @BeforeEach
    void setUp() {
        info = new UsingJSON(); 
        //drone = new Drone(info);
        radar = new Radar(info); 
        actionLog = new ActionLog();
        gridSearch = new GridSearch(info, drone, radar, actionLog);
    }
    @Test
    void testFindCreeksInitialFly() {

    }
/*
    @Test  
    void closestCreek (){
        PointOfInterest site = new PointOfInterest("Site", new int[]{5, 5});
        PointOfInterest creek1 = new PointOfInterest("Creek1", new int[]{1, 1});
        PointOfInterest creek2 = new PointOfInterest("Creek2", new int[]{10, 10});
        gridSearch.getPhotoScanner().setSite(site);
        gridSearch.getCreekLocations().addCreek(creek1);
        gridSearch.getCreekLocations().addCreek(creek2);

        PointOfInterest closest = gridSearch.closestCreek();
        assertEquals(creek1, closest, "Creek1 should be the closest creek to the site.");
    }
*/
    @Test
    void verticalSearch(){

    }

    @Test 
    void towardsMiddle(){

    }

    @Test
    void isSiteFound (){

    }
}
