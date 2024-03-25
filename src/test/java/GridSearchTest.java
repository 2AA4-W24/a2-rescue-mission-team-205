import ca.mcmaster.se2aa4.island.team205.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GridSearchTest {
    
    private GridSearch gridSearch;
    private Information info;
    private Drone drone;
    private ActionLog actionLog;

    private final String scanNothing = "{\"extras\":{\"biomes\":[\"OCEAN\"], \"creeks\":[], \"sites\":[]}}";

    private final String foundGround = "{\"status\":\"OK\",\"extras\":{\"found\":\"GROUND\",\"range\":2}}";

    private final String noGround = "{\"status\":\"OK\",\"extras\":{\"found\":\"OUT_OF_RANGE\",\"range\":2}}";


    @BeforeEach
    void setUp() {
        info = new UsingJSON();
        String initial = "{\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
        info.results(initial);
        drone = new Drone(info, new Point(0,0));
        drone.initialize();
        Radar radar = new Radar(info);
        actionLog = new ActionLog();
        gridSearch = new GridSearch(info, drone, radar, actionLog);

    }
    @Test
    void initialTurn() {
        gridSearch.findEmergencySite();
        assertEquals(drone.getDirection(), Drone.Direction.N);
    }

    @Test
    void initialScan() {
        String initialScan = "{\"heading\":\"N\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}\n";
        info.results(initialScan);
        gridSearch.findEmergencySite();
        assertEquals(drone.getDirection(), Drone.Direction.N);
    }

    @Test
    void vertSearch() {
        actionLog.addLog(Action.FLY);
        gridSearch.findEmergencySite();
        gridSearch.findEmergencySite();
        assertEquals(actionLog.getPrev(), Action.SCAN);
    }

    @Test
    void scannedSite(){
        actionLog.addLog(Action.FLY);
        for(int i = 0; i < 3; i ++){
            gridSearch.findEmergencySite();
            if(i == 1){
                String scanSite = "{\"extras\":{\"biomes\":[\"BEACH\"], \"creeks\":[], \"sites\":[\"abcdefg-1\"]}}";
                info.results(scanSite);
            }
        }
        assertEquals(actionLog.getPrev(), Action.FLY);
    }

    @Test
    void scanned(){
        actionLog.addLog(Action.FLY);
        gridSearch.findEmergencySite();
        drone.setDirection(Drone.Direction.N);
        gridSearch.findEmergencySite();
        info.results(scanNothing);
        gridSearch.findEmergencySite();
        assertEquals(Action.ECHOF, actionLog.getPrev());
    }

    @Test
    void genMove(){
        actionLog.addLog(Action.FLY);
        gridSearch.findEmergencySite();
        drone.setDirection(Drone.Direction.N);
        gridSearch.findEmergencySite();
        info.results(scanNothing);
        gridSearch.findEmergencySite();
        info.results(foundGround);
        gridSearch.findEmergencySite();
        assertEquals(Action.FLY, actionLog.getPrev());
        gridSearch.findEmergencySite();
        gridSearch.findEmergencySite();
        assertEquals(Action.SCAN, actionLog.getPrev());
    }

    @Test
    void closestCreek (){
        String id = "1";
        scannedSite();
        gridSearch.findEmergencySite();
        String creek1 = "{\"extras\":{\"biomes\":[\"BEACH\"], \"creeks\":[\"1\"], \"sites\":[]}}";
        info.results(creek1);
        gridSearch.findEmergencySite();
        String creek2 = "{\"extras\":{\"biomes\":[\"BEACH\"], \"creeks\":[\"6\"], \"sites\":[]}}";
        info.results(creek2);
        assertEquals(id, gridSearch.closestCreek().getIdentifier());
    }

    @Test
    void radarSurroundingLeft(){
        actionLog.addLog(Action.FLY);
        gridSearch.findEmergencySite();
        info.results(scanNothing);
        gridSearch.findEmergencySite();
        actionLog.addLog(Action.ECHOL);
        info.results(foundGround);
        gridSearch.findEmergencySite();
        gridSearch.findEmergencySite();
        assertEquals(Action.ECHOL, actionLog.getPrev());
    }

    @Test
    void radarSurroundingRight(){
        actionLog.addLog(Action.FLY);
        gridSearch.findEmergencySite();
        info.results(scanNothing);
        gridSearch.findEmergencySite();
        actionLog.addLog(Action.ECHOR);
        info.results(foundGround);
        gridSearch.findEmergencySite();
        gridSearch.findEmergencySite();
        assertEquals(Action.ECHOR, actionLog.getPrev());
    }

    @Test
    void radarFrontDistanceRight(){
        actionLog.addLog(Action.FLY);
        gridSearch.findEmergencySite();
        info.results(scanNothing);
        gridSearch.findEmergencySite();
        actionLog.addLog(Action.ECHOF);
        info.results(noGround);
        drone.setDirection(Drone.Direction.N);
        gridSearch.findEmergencySite();
        assertEquals(Action.ECHOR, actionLog.getPrev());
    }

    @Test
    void radarFrontLeft(){
        actionLog.addLog(Action.FLY);
        gridSearch.findEmergencySite();
        info.results(scanNothing);
        gridSearch.findEmergencySite();
        actionLog.addLog(Action.ECHOF);
        info.results(noGround);
        drone.setDirection(Drone.Direction.S);
        gridSearch.findEmergencySite();
        assertEquals(Action.ECHOL, actionLog.getPrev());
    }

    @Test
    void verticalSlideCaseOne(){
        actionLog.addLog(Action.FLY);
        gridSearch.findEmergencySite();
        info.results(scanNothing);
        gridSearch.findEmergencySite();
        actionLog.addLog(Action.ECHOR);
        info.results(noGround);
        drone.setDirection(Drone.Direction.S);
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.E, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.N, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.ECHOF, actionLog.getPrev());
    }

    @Test
    void verticalSlideCaseTwo(){
        actionLog.addLog(Action.FLY);
        gridSearch.findEmergencySite();
        info.results(scanNothing);
        gridSearch.findEmergencySite();
        actionLog.addLog(Action.ECHOR);
        info.results(noGround);
        drone.setDirection(Drone.Direction.N);
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.E, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.S, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.ECHOF, actionLog.getPrev());
    }

    @Test
    void echoFrontAfterSlideEast(){
        verticalSlideCaseTwo();
        info.results(noGround);
        drone.setDirection(Drone.Direction.N);
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.W, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.FLY, actionLog.getPrev());
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.S, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.E, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.N, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.ECHOF, actionLog.getPrev());
    }

    @Test
    void echoFrontAfterSlideWest(){
        verticalSlideCaseTwo();
        info.results(noGround);
        drone.setDirection(Drone.Direction.S);
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.W, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.FLY, actionLog.getPrev());
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.N, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.E, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.TURN, actionLog.getPrev());
        assertEquals(Drone.Direction.S, drone.getDirection());
        gridSearch.findEmergencySite();
        assertEquals(Action.ECHOF, actionLog.getPrev());
    }


}
