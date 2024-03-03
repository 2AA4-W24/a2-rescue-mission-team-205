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

    private final Drone.Direction searchingDirection;

    private final Logger logger = LogManager.getLogger();

    private Drone.Direction previousD;

    int i = 0;

    public GridSearch2(Information information, Drone drone1, Radar radar1, ActionLog log, Drone.Direction d){
        info = information;

        radar = radar1;
        drone = drone1;
        photoScanner = new PhotoScanner(info, drone);
        searchingDirection = drone.getDirection();
        actionLog = log;

    }
    @Override
    public void findEmergencySite() {

    }

    @Override
    public void findCreeks() {
        if(actionLog.getPrev() == Action.SCAN) {
            if (photoScanner.siteFound()) {
                drone.returnHome();
            }
            else{
                logger.info(searchingDirection);
                switch(Drone.Direction.S){
                    case N, S -> horizontalSearch();
                    default -> verticalSearch();
                }
            }
        }
        else if(i == 0){
            logger.info(drone.getDirection());
            switch(drone.getDirection()){
                case N, S -> {
                    previousD = drone.getDirection();
                    drone.turnLeft();
                    actionLog.addLog(Action.FLY);
                    //add logic to find this
                }
                default -> verticalSearch();
            }
            i++;
        }
        else{
            logger.info(searchingDirection);
            switch(Drone.Direction.S){
                case N, S -> horizontalSearch();
                default -> verticalSearch();
            }
        }

    }

    @Override
    public PointOfInterest closestCreek() {
        return null;
    }

    private void horizontalSearch(){
        //logger.info(actionLog.getList());
        if(sliding){
            horizontalSlide();
        }
        else if(actionLog.getPrev() == Action.SCAN){
            if(photoScanner.scanResults()){
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
            else{
                if(drone.getDirection() == Drone.Direction.E || drone.getDirection() == Drone.Direction.W) {
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

            if(radar.distanceToLand() == -1){

                horizontalSlide();
            }
            else{
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
        }
        else{
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
        }
    }

    private void verticalSearch(){
        //logger.info(actionLog.getList());
        if(sliding){
            verticalSlide();
        }
        else if(actionLog.getPrev() == Action.SCAN){
            if(photoScanner.siteFound()){
                drone.returnHome();
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

            if(radar.distanceToLand() == -1){

                verticalSlide();
            }
            else{
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
        }
        else{
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
        }
    }


    private void verticalSlide(){

        sliding = true;
        logger.info(drone.getDirection());
        logger.info(slideStage);
        if(slideStage %2 == 1){
            if(drone.getLeftDirection() == Drone.Direction.E){
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
            if(drone.getDirection()== Drone.Direction.E){
                logger.info(previousD);
                if(previousD == Drone.Direction.N){
                    drone.turnLeft();

                }
                else{
                    drone.turnRight();
                }

            }
            else{
                drone.turnRight();
            }
            actionLog.addLog(Action.TURN);
            slideStage = 1;
            sliding = false;
        }
    }

    private void horizontalSlide(){

        sliding = true;
       // logger.info(drone.getDirection());
       // logger.info(slideStage);
        if(slideStage %3 == 1){
            if(drone.getLeftDirection() == Drone.Direction.S){
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
        else if(slideStage % 3 == 2){
            drone.fly();
            actionLog.addLog(Action.FLY);
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

    private void checkSliding(){
        if(actionLog.getPrev() == Action.TURN){
            sliding = actionLog.getPrev(2) != Action.TURN;
            drone.fly();
            actionLog.addLog(Action.FLY);
        }
        else{
            if(actionLog.getPrev(1) == Action.TURN){
                drone.turnRight();
                actionLog.addLog(Action.TURN);
                sliding = true;
            }
            else{
                drone.fly();
                actionLog.addLog(Action.FLY);
                sliding = false;
            }
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
}
