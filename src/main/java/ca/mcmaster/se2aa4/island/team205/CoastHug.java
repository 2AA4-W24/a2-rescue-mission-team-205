package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class CoastHug implements SearchAlgorithm {


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

    int turns = 1;


    int range = -1;


    boolean recovering = false;

    boolean turning = false;

    boolean general = false;

    public CoastHug(Information information, Drone drone1, Radar radar1, ActionLog log){
        info = information;

        radar = radar1;
        drone = drone1;
        photoScanner = new PhotoScanner(info, drone, creeks);
        actionLog = log;

    }
    @Override
    public void findEmergencySite() {


    }



    @Override
    public void findCreeks() {
        logger.info(range);
        if(actionLog.getPrev()  == Action.SCAN){
            if(photoScanner.overCoast()){
                sliding = false;
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
            else if(photoScanner.scanResults()){
                radar.useRadarRight(drone.getRightDirection());
                actionLog.addLog(Action.ECHOR);
            }
            else{
                sliding = false;
                radar.useRadarLeft(drone.getLeftDirection());
                actionLog.addLog(Action.ECHOL);
            }
        }
        else if(actionLog.getPrev() == Action.ECHOR){
               if(radar.distanceToLand() != 0){
                   drone.fly();
                   actionLog.addLog(Action.FLY);
               }
               else if (radar.distanceToLand() == -1){
                   drone.fly();
                   actionLog.addLog(Action.FLY);
               }
               else{
                   goToCoast();
               }

        }
        else if(actionLog.getPrev() == Action.ECHOL){
            if(radar.distanceToLand() == 0){
                photoScanner.scanTerrain();
                actionLog.addLog(Action.SCAN);
            }
            else if (radar.distanceToLand() == -1){
                uTurn();
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
        else{
            radar.useRadarLeft(drone.getLeftDirection());
            actionLog.addLog(Action.ECHOL);
        }

    }


    private void goToCoast(){
        if(slideStage % 3 == 1){
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else if(slideStage % 3 == 2){
            drone.fly();
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else{
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
            slideStage = 1;
        }
    }
    private void gen(){
        if(range > 0){
            drone.fly();
            actionLog.addLog(Action.FLY);
            range --;
        }
        else{
            general = false;
            drone.turnRight();
            actionLog.addLog(Action.TURN);
            range = -1;
        }

    }

    private void uTurn(){
        if(turns %3 == 1){
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            turning = true;
            turns ++;
        }
        else if(turns %3 == 2){
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            turns ++;
        }
        else{
            drone.turnLeft();
            actionLog.addLog(Action.TURN);
            turning = false;
            turns = 1;
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

    @Override
    public PointOfInterest closestCreek() {
        return null;
    }

    @Override
    public boolean isSiteFound() {
        return false;
    }
}