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

    boolean recovering = false;
    private Drone.Direction previousD;

    private Drone.Direction slideDirection = Drone.Direction.E;

    boolean both = false;

    boolean general = false;

    int i = 0;

    boolean transition = false;

    int range = -1;

    boolean getToCoast = false;

    boolean getToLand = false;

    boolean turning = false;

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
                uTurn();
            }
            else if(radar.distanceToLand() != 0){
                radar.useRadarRight(drone.getRightDirection());
                actionLog.addLog(Action.ECHOR);
               //recovering = true;
                //recover();
                /*
                drone.turnLeft();
                actionLog.addLog(Action.TURN);
                range = radar.distanceToLand();
                getToLand = true;
                */


            }
            else{
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
        }
        else if(actionLog.getPrev() == Action.ECHOR){
            if(radar.distanceToLand() !=0 ){
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
            else{
                recovering = true;
                recover();
            }
        }
        else if(range > -1){
            general = true;
            gen();
        }
        else if(recovering){
            recover();
        }
        else if(turning){
            uTurn();
        }
        else if(general){
            gen();
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


    private void gen(){
        if(range > 0){
            drone.fly();
            actionLog.addLog(Action.FLY);
            range --;
        }
        else if(range == 0){

            drone.turnRight();
            actionLog.addLog(Action.TURN);

        }
        else{
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
            range = -1;
            general =false;
        }

    }

    private void recover(){
        if(slideStage % 4 == 1){
            drone.fly();
            actionLog.addLog(Action.FLY);
            slideStage++;
        }
        else if(slideStage %4 == 2){
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else if(slideStage %4 == 3){
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else{
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            slideStage = 1;
            recovering = false;
            general = true;
        }
    }
    private void toLand(){
        logger.info("----------toLAND");
        if(range > 0){
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
        logger.info("----------toCoast");
        if(slideStage %3 == 1){
           drone.turnRight();
           slideStage++;
           actionLog.addLog(Action.TURN);
        }
        else if(slideStage%3 == 2){
                drone.fly();
                slideStage++;
                actionLog.addLog(Action.FLY);
        }
        else{
            getToCoast = false;
            slideStage = 1;
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
        }
    }

    private void uTurn(){
        if(!turning){
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            turning = true;
        }
        else{
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            turning = false;
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
