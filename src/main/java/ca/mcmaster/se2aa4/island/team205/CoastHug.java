package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class CoastHug implements SearchAlgorithm{

    private final Information info;

    private final PhotoScanner photoScanner;

    private final Radar radar;

    private final Drone drone;

    private final ActionLog actionLog;

    private final int[] startingLocation;

    private final Logger logger = LogManager.getLogger();

    private final CreekLocations creeks = new CreekLocations();

    private Drone.Direction previousD;

    private boolean traversedCoast = false;


    public CoastHug(Information information, Drone drone1, Radar radar1, ActionLog log){
        info = information;
        radar = radar1;
        drone = drone1;
        photoScanner = new PhotoScanner(info, drone, creeks);
        startingLocation = drone.getLocation();
        actionLog = log;

    }
    @Override
    public void findEmergencySite() {

    }

    @Override
    public void findCreeks() {

        if(Arrays.equals(drone.getLocation(), startingLocation) && !traversedCoast){
            traversedCoast = true;
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
            //
        }
        else{
            if(actionLog.getPrev() == Action.SCAN){
                if(photoScanner.overCoast()){
                    drone.fly();
                    actionLog.addLog(Action.FLY);
                }
                else if(!photoScanner.scanResults() && actionLog.getPrev(1) == Action.TURN){
                    drone.turnLeft();
                    actionLog.addLog(Action.TURN);
                }
                else if(!photoScanner.scanResults()){
                    drone.turnLeft();
                    actionLog.addLog(Action.TURN);
                }
                else{
                    radar.useRadarLeft(drone.getDirection());
                    actionLog.addLog(Action.ECHOL);
                }
            }
            else if(actionLog.getPrev() == Action.ECHOL){
                if(radar.distanceToLand() == 0){
                    drone.fly();
                    actionLog.addLog(Action.FLY);
                }
                else{
                   drone.turnRight();
                   actionLog.addLog(Action.TURN);
                }

            }
            else{
                photoScanner.scanTerrain();
                actionLog.addLog(Action.SCAN);
            }
        }

    }

    private void init(){

    }

    @Override
    public PointOfInterest closestCreek() {
        return null;
    }

    @Override
    public boolean isSiteFound() {
        return false;
    }
}
