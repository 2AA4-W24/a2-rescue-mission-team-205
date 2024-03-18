package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandCenter {

    private final Information info = new UsingJSON();

    private final Point point = new Point();

    private final Radar radar = new Radar(info);

    private final Drone drone = new Drone(info, point);

    private final ActionLog actionLog = new ActionLog();

    private final SearchAlgorithm coastSearch = new Coast(info, drone, radar, actionLog);

    private final SearchAlgorithm gridSearch = new GridSearch2(info, drone, radar, actionLog);

    private int range = -1;

    private boolean landSpotted = false;

    private boolean emergencySiteFound = false;

    private boolean creekSearching = true;

    private boolean closestCreekFound = false;

    private boolean land = false;

    private final Logger logger = LogManager.getLogger();

    private final Spiral spiral = new Spiral(info, drone, actionLog, point);

    boolean mappedCoast = true;

    int count = 0;

    private PointOfInterest creek;


    public CommandCenter(String s){
        info.results(s);
        drone.initialize(s);
        logger.info(drone.getDirection());
    }

    public Drone getDrone(){
        return drone;
    }

    public void updateInformation(String s){
        info.results(s);
        drone.drain(info.cost());
    }

    public void takeCommand(){
        logger.info(count);
        logger.info(drone.battery);
        if (count >= 2450){
            drone.returnHome();
        }
        else if(drone.battery <= 30){
            creek = closestCreek();
            drone.returnHome();
            count++;
        }
        else if(range != 0 && !land){
            phaseOne();
            count++;
        }
        else if(creekSearching){
            findSite();
            count++;

        }
        else if(!emergencySiteFound){
            findSite();
            count++;
        }
        else if(!closestCreekFound){
            creek = closestCreek();
            count++;
        }
        else {
            drone.returnHome();
        }

    }

    private void phaseOne(){
        if(!landSpotted) {
            findLand();
        }
        else{
            flyToLand();
        }
    }


    private void generalMovement(){
        radar.useRadarFront(drone.getDirection());
        actionLog.addLog(Action.ECHOF);

    }
    
    private void findLand(){
        if (actionLog.getPrev() == Action.NONE || actionLog.getPrev() == Action.FLY) {
            generalMovement();
        }
        else if (actionLog.getPrev() == Action.ECHOF) {
            if (radar.distanceToLand() != -1) {
                findRange();
                drone.fly();
                range--;
                actionLog.addLog(Action.FLY);
            }
            else {
                radar.useRadarRight(drone.getRightDirection());
                actionLog.addLog(Action.ECHOR);
            }
        }
        else if (actionLog.getPrev() == Action.ECHOR) {
            if (radar.distanceToLand() != -1) {
                findRange();
                drone.turnRight();
                actionLog.addLog(Action.TURN);
            }
            else {
                radar.useRadarLeft(drone.getLeftDirection());
                actionLog.addLog(Action.ECHOL);

            }
        }
        else if (actionLog.getPrev() == Action.ECHOL) {
            if (radar.distanceToLand() != -1) {
                findRange();
                drone.turnLeft();
                actionLog.addLog(Action.TURN);
            }
            else {
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
        }

    }

    private void findRange(){
        range = radar.distanceToLand();
        landSpotted = true;
    }

    public int getRange(){
        return range;
    }

    private void flyToLand(){
        if(range > 0){
            drone.fly();
            range --;
            actionLog.addLog(Action.FLY);
        }
        else{
            drone.fly();
            actionLog.addLog(Action.FLY);
            land = true;
        }
    }

    private void findCreeks(){
        coastSearch.findCreeks();

    }

    private void findSite(){
        //implement grid search
        if(gridSearch.isSiteFound()){
            spiral.searchRadially();
        }
        else{
            spiral.searchRadially();
            //gridSearch.findCreeks();
        }


    }


    private PointOfInterest closestCreek(){
    //start looking radially outward
        return gridSearch.closestCreek();
    }

    public String finalReport(){
        creek = closestCreek();
        return creek.identifier;
    }


    public String decision(){
        return info.decision();
    }
}
