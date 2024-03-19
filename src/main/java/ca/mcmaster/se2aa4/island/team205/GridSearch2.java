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
    boolean finished = false;

    int turned = 0;

    public GridSearch2(Information information, Drone drone1, Radar radar1, ActionLog log){
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
        logger.info(creeks.numberOfCreeks());

        if(actionLog.getPrev() == Action.SCAN){
            photoScanner.creekScan();
            /*
            if(photoScanner.siteFound()){
                siteFound = true;
                closestCreek();
                x = creeks.x;
                y = creeks.y;
                drone.fly();
                actionLog.addLog(Action.FLY);
            }

             */


        }

        if(i == 0 && !siteFound){
            previousD = drone.getDirection();
            if(drone.getDirection() == Drone.Direction.E || drone.getDirection() == Drone.Direction.W){
                drone.turnLeft();
                actionLog.addLog(Action.TURN);
            }
            else{
                drone.fly();
                actionLog.addLog(Action.FLY);
            }


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

        if(sliding){
            verticalSlide();
        }
        else if(actionLog.getPrev() == Action.TURN){
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
            finished = true;

        }
        else if(finished){
            if(transition){
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
            else {
                radar.useRadarFront(drone.getDirection());
                actionLog.addLog(Action.ECHOF);
                afterSlide = true;

            }
            finished = false;
        }
        else if(actionLog.getPrev() == Action.SCAN){
            scanDecision();
        }
        else if(actionLog.getPrev() == Action.ECHOF) {
            echoFrontDecision();
        }
        else{
            if(transition){
                transition = false;
                both = true;
                verticalSlide();

            }
            else{
                if(range >= 0){
                    if(siteFound){
                        if(actionLog.getPrev() == Action.SCAN){
                            drone.fly();
                            actionLog.addLog(Action.FLY);
                            range --;
                        }
                        else{
                            photoScanner.scanTerrain();
                            actionLog.addLog(Action.SCAN);
                        }
                    }
                    else{
                        drone.fly();
                        actionLog.addLog(Action.FLY);
                        range --;
                    }

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
                previousD = drone.getDirection();
                drone.turnLeft();
            }
            else{
                previousD = drone.getDirection();
                drone.turnRight();
            }
            actionLog.addLog(Action.TURN);
            slideStage = 1;
            sliding = false;

        }
    }

    private void scanDecision(){
        if(photoScanner.siteFound()){
            drone.fly();
            actionLog.addLog(Action.FLY);
            siteFound = true;

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

    private void echoFrontDecision(){
        if(radar.distanceToLand() == -1 && afterSlide){
            if(turned == 3){
                drone.returnHome();
            }
            else if(slideDirection == Drone.Direction.E){
                slideDirection = Drone.Direction.W;
                afterSlide = false;
                if(drone.getDirection() == Drone.Direction.N){
                    drone.turnLeft();
                }
                else{
                    drone.turnRight();
                }

            }
            else{
                slideDirection = Drone.Direction.E;
                afterSlide = false;
                if(drone.getDirection() == Drone.Direction.S){
                    drone.turnLeft();
                }
                else{
                    drone.turnRight();
                }

            }
            actionLog.addLog(Action.TURN);
            slideStage = 2;
            turned ++;
            transition = true;
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
