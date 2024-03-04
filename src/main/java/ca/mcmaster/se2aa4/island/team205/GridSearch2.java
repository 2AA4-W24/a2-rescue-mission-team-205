package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GridSearch2 implements SearchAlgorithm{

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

    public GridSearch2(Information information, Drone drone1, Radar radar1, ActionLog log){
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
            photoScanner.creekScan();
            if (photoScanner.siteFound()) {
                drone.fly();
                actionLog.addLog(Action.FLY);
                siteFound = true;
                logger.info(creeks.all().toString());
            }
            else{
                verticalSearch();
            }
        }
        else if(i == 0){
            previousD = drone.getDirection();
            drone.fly();
            actionLog.addLog(Action.FLY);

            i++;
        }
        else{
            verticalSearch();
        }

    }

    @Override
    public PointOfInterest closestCreek() {
        return creeks.closestCreak(photoScanner.site);
    }

    private void verticalSearch(){
        //logger.info(actionLog.getList());
        if(sliding){
            verticalSlide();
        }
        else if(actionLog.getPrev() == Action.TURN && !sliding){
            if(transition){
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
            else {
                radar.useRadarFront(drone.getDirection());
                actionLog.addLog(Action.ECHOF);
                afterSlide = true;
            }

        }
        else if(actionLog.getPrev() == Action.SCAN){
            if(photoScanner.siteFound()){
                drone.fly();
                actionLog.addLog(Action.FLY);
                siteFound = true;
                logger.info(creeks.all().toString());
            }
            else if(photoScanner.scanResults()){
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
            else{
                if(drone.getDirection() == Drone.Direction.N || drone.getDirection() == Drone.Direction.S) {
                    radar.useRadarFront(drone.getDirection());
                    actionLog.addLog(Action.ECHOF);
                }
                else{
                    drone.fly();
                    actionLog.addLog(Action.FLY);
                }
            }
        }
        else if(actionLog.getPrev() == Action.ECHOF){
            if(radar.distanceToLand() == -1 && afterSlide){
                logger.info("both:" + slideDirection);
                if(slideDirection == Drone.Direction.E){
                   // searchingDirection = Drone.Direction.S;
                    slideDirection = Drone.Direction.W;
                    afterSlide = false;
                    drone.turnRight();
                    actionLog.addLog(Action.TURN);
                    slideStage = 2;
                    transition = true;

                }
                else{
                    drone.returnHome();
                }
            }
            else if(radar.distanceToLand() == -1){
                verticalSlide();
            }
            else{
                range = radar.distanceToLand();
                drone.fly();
                actionLog.addLog(Action.FLY);
                range --;
            }
            afterSlide = false;
        }
        else{
            if(transition){
                transition = false;
                both = true;
                verticalSlide();

            }
            else{
                if(range >= 0){
                    drone.fly();
                    actionLog.addLog(Action.FLY);
                    range --;
                }
                else{
                    photoScanner.scanTerrain();
                    actionLog.addLog(Action.SCAN);
                }

            }

        }
    }


    private void verticalSlide(){

        sliding = true;
        logger.info(drone.getDirection());
        logger.info(slideStage);
        if(slideStage %2 == 1){
            if(drone.getLeftDirection() == slideDirection){
                previousD = drone.getDirection();
                drone.turnLeft();
            }
            else{
                previousD = drone.getDirection();
                drone.turnRight();
            }
            actionLog.addLog(Action.TURN);
            slideStage++;
        }
        else{
            if(drone.getLeftDirection()== towardsMiddle(previousD)){
                drone.turnLeft();
            }
            else{
                drone.turnRight();
            }
            actionLog.addLog(Action.TURN);
            slideStage = 1;
            sliding = false;
        }
    }


    private Drone.Direction towardsMiddle(Drone.Direction direction){
        switch(direction) {
            case N -> {
                return Drone.Direction.S;
            }

            case E -> {
                return Drone.Direction.W;
            }

            case S -> {
                return Drone.Direction.N;
            }

            default -> {
                return Drone.Direction.E;
            }
        }
    }

    @Override
    public boolean isSiteFound(){
        return siteFound;
    }
}
