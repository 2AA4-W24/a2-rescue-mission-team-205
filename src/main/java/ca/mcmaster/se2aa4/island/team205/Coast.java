package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Coast implements SearchAlgorithm{

    private final Information info;

    private final PhotoScanner photoScanner;

    private final Radar radar;

    private final Drone drone;

    boolean trying = false;

    private final ActionLog actionLog;

    private boolean sliding = false;

    private int slideStage = 1;

    boolean reachedCoast = true;

    private Drone.Direction searchingDirection;

    private final Logger logger = LogManager.getLogger();

    private final CreekLocations creeks = new CreekLocations();


    boolean duplicate = false;
    int turns = 1;


    int i = 1;
    int range = -1;


    boolean recoveringLeft = false;

    boolean recoveringRight = false;

    boolean turning = false;

    boolean general = false;

    boolean edge = false;

    int[] start;

    public Coast(Information information, Drone drone1, Radar radar1, ActionLog log){
        info = information;
        radar = radar1;
        drone = drone1;
        photoScanner = new PhotoScanner(info, drone, creeks);
        actionLog = log;
        start = new int[]{drone.getLocation()[0], drone.getLocation()[1]};

    }
    @Override
    public void findEmergencySite() {

    }

    private boolean close(){
        if(Math.abs(drone.getLocation()[0] - start[0]) <= 4 && Math.abs(drone.getLocation()[1] - start[1]) <= 4){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void findCreeks() {
     //   logger.info(actionLog.getList().toString());

        //logger.info(Arrays.toString(drone.getLocation()) + " " + Arrays.toString(start));
        logger.info(creeks.numberOfCreeks());
        if(i == 1){
            drone.fly();
            actionLog.addLog(Action.FLY);
            i++;
        }
        else if(actionLog.getPrev()  == Action.SCAN){
            scanLogic();
        }
        else if(recoveringRight){
            recoverRight();
        }
        else if(edge){
            returnToCoast();
        }
        else if(recoveringLeft){
            recoverLeft();
        }else if(actionLog.getPrev()  == Action.ECHOR){
            radarRightLogic();
        }
        else if(actionLog.getPrev()  == Action.ECHOL){
            radarLeftLogic();
        }
        else{
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
        }
    }

    private void radarLeftLogic(){
        if(radar.distanceToLand() == -1 || radar.distanceToLand() > 2){
            edge = true;
            returnToCoast();
        }
        else{
            recoveringRight = true;
            recoverRight();
        }
    }

    void returnToCoast(){
        if(slideStage % 3 == 1){
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else if(slideStage %3 == 2){
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else{
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            slideStage = 1;
            edge = false;
        }
    }
    private void radarRightLogic(){
        if(radar.distanceToLand() > 0 || radar.distanceToLand() == -1){
            drone.fly();
            actionLog.addLog(Action.FLY);
        }
        else{
            //need other
            recoveringLeft = true;
            recoverLeft();

        }
    }

    private void recoverLeft(){
        if(slideStage % 5 == 1){
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else if(slideStage %5 == 2){
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else if(slideStage %5 == 3){
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else if(slideStage %5 == 4){
            drone.fly();
            actionLog.addLog(Action.FLY);
            slideStage++;
        }
        else{
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            slideStage = 1;
            recoveringLeft = false;
        }
    }

    private void recoverRight(){
        if(slideStage % 5 == 1){
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else if(slideStage %5 == 2){
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else if(slideStage %5 == 3){
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else if(slideStage %5 == 4){
            drone.fly();
            actionLog.addLog(Action.FLY);
            slideStage++;
        }
        else{
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            slideStage = 1;
            recoveringRight = false;
             reachedCoast = false;
        }
    }
    private void scanLogic(){
        if(!photoScanner.creekScan()){
            duplicate = true;
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
        }
        else if(photoScanner.overCoast()){
            drone.fly();
            actionLog.addLog(Action.FLY);
            reachedCoast = true;

        }
        else if(photoScanner.scanResults()){
            radar.useRadarRight(drone.getRightDirection());
            actionLog.addLog(Action.ECHOR);
        }
        else{

            radar.useRadarLeft(drone.getLeftDirection());
            actionLog.addLog(Action.ECHOL);
            //recoveringRight = true;
            //recoverRight();
        }
    }

    @Override
    public PointOfInterest closestCreek() {
        return null;
    }

    @Override
    public boolean isSiteFound() {
        return duplicate;
    }
}