package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CircleSearch implements SearchAlgorithm{

    private final Information info;

    private final PhotoScanner photoScanner;

    private final Radar radar;

    private final Drone drone;

    private final ActionLog actionLog;

    private boolean sliding = false;

    private int slideStage = 1;

    private Drone.Direction searchingDirection;

    private final Logger logger = LogManager.getLogger();

    private final CreekLocations creeks = new CreekLocations();

    boolean afterSlide = false;

    boolean siteFound = false;

    private Drone.Direction previousD;

    private Drone.Direction slideDirection = Drone.Direction.E;

    boolean both = false;

    int i = 0;

    boolean transition = false;

    int range = -1;

    boolean getToCoast = false;

    boolean getToLand = false;

    public CircleSearch(Information information, Drone drone1, Radar radar1, ActionLog log){
        info = information;

        radar = radar1;
        drone = drone1;
        photoScanner = new PhotoScanner(info, drone, creeks);
        searchingDirection = Drone.Direction.E;
        actionLog = log;

    }
    @Override
    public void findEmergencySite() {

    }

    @Override
    public void findCreeks() {
        if(actionLog.getPrev() == Action.SCAN) {
            if (photoScanner.overCoast()) {
                drone.fly();
                actionLog.addLog(Action.FLY);
            } else{
                radar.useRadarLeft(drone.getLeftDirection());
                actionLog.addLog(Action.ECHOL);
            }
        }
        else if(actionLog.getPrev() == Action.ECHOL){
            if(radar.distanceToLand() == -1){
                drone.turnLeft();
                actionLog.addLog(Action.TURN);
            }
            else if(radar.distanceToLand() != 0){
                drone.turnLeft();
                actionLog.addLog(Action.TURN);
                range = radar.distanceToLand();
                getToLand = true;


            }
            else{
                radar.useRadarRight(drone.getRightDirection());
                actionLog.addLog(Action.ECHOR);
            }
        }
        else if(actionLog.getPrev() == Action.ECHOR){
            if(radar.distanceToLand() == -1){
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
            else{
                drone.turnRight();
                actionLog.addLog(Action.TURN);
                getToCoast = true;
            }
        }
        else if(getToCoast){
            toCoast();
        }
        else if(getToLand){
            toLand();
        }
        else{
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
        }
    }

    private void toLand(){
        if(range >= 0){
            drone.fly();
            actionLog.addLog(Action.FLY);
            range--;
        }
        else{
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            getToLand = false;
        }

    }

    private void toCoast(){
        if(actionLog.getPrev() == Action.FLY){
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
        }
        else if(actionLog.getPrev() == Action.SCAN){
            if(!photoScanner.scanResults()){
                drone.turnLeft();
                actionLog.addLog(Action.TURN);
                getToCoast = false;
            }
            else{
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
        }
        else{
            drone.fly();
            actionLog.addLog(Action.FLY);
        }
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
