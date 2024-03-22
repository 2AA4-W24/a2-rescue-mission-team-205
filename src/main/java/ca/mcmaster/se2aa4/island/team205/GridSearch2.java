package ca.mcmaster.se2aa4.island.team205;


public class GridSearch2 implements SearchAlgorithm{

    private final PhotoScanner photoScanner;

    private final Radar radar;

    private final Drone drone;

    private final ActionLog actionLog;

    private final CreekLocations creeks = new CreekLocations();

    private Drone.Direction previousD;

    private Drone.Direction slideDirection = Drone.Direction.E;

    private Drone.Direction loopDirection;

    private boolean initial = true;

    private boolean afterSlide = false;

    private boolean siteFound = false;

    private boolean looping = false;

    private boolean eastCoast = true;

    private boolean sliding = false;

    private int slideStage = 1;

    private int range = -1;


    public GridSearch2(Information information, Drone drone1, Radar radar1, ActionLog log){
        radar = radar1;
        drone = drone1;
        photoScanner = new PhotoScanner(information, drone, creeks);
        actionLog = log;

    }

    @Override
    public void findEmergencySite() {
        if(actionLog.getPrev() == Action.SCAN){
            photoScanner.creekScan();
            if(photoScanner.siteFound()) {
                siteFound = true;
                closestCreek();
            }
        }

        if(initial && !siteFound){
            previousD = drone.getDirection();
            if(drone.getDirection() == Drone.Direction.E || drone.getDirection() == Drone.Direction.W){
                drone.turnLeft();
                actionLog.addLog(Action.TURN);
            }
            else{
                photoScanner.scanTerrain();
                actionLog.addLog(Action.SCAN);
                initial = false;
            }
        }
        else{
            verticalSearch();
        }
    }

    @Override
    public PointOfInterest closestCreek() {
        return creeks.closestCreak(photoScanner.getSite());
    }

    private void verticalSearch(){
        if(sliding){
            if(actionLog.getPrev() == Action.SCAN){
                sliding = false;
                postTurnAction();
            }
            else{
                verticalSlide();
            }

        }
        else if(looping){
            loop();
        }
        else{
            switch (actionLog.getPrev()){
                case TURN -> postTurnAction();
                case SCAN -> scanDecision();
                case NONE -> radarSurrounding();
                case ECHOF -> echoFrontDecision();
                case ECHOR,ECHOL ->echoSideDirection();
                default ->  generalMovement();
            }
        }
    }

    private void postTurnAction(){
        radar.useRadar(drone.getDirection());
        actionLog.addLog(Action.ECHOF);
        afterSlide = true;
    }

    private void radarSurrounding(){
        if(actionLog.getPrev(1) == Action.ECHOR){
            radar.useRadar(drone.getRightDirection());
            actionLog.addLog(Action.ECHOR);
        }
        else{
            radar.useRadar(drone.getLeftDirection());
            actionLog.addLog(Action.ECHOL);
        }
    }

    private void generalMovement(){
        if(range > 0){
            drone.fly();
            actionLog.addLog(Action.FLY);
            range --;
        }
        else{
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
        }
    }

    private void verticalSlide(){
        sliding = true;
        if(slideStage %3 == 1){
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
        else if(slideStage %3 == 2){
            if(drone.getLeftDirection()== towardsMiddle(previousD)){
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
            photoScanner.scanTerrain();
            actionLog.addLog(Action.SCAN);
            slideStage = 1;
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
                radar.useRadar(drone.getDirection());
                actionLog.addLog(Action.ECHOF);
            }
            else{
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
        }
    }

    private void loop(){
        looping  = true;
        switch (slideStage % 6) {
            case 1, 3, 4, 5 -> {
                if ((eastCoast && loopDirection == Drone.Direction.N) || (!eastCoast && loopDirection == Drone.Direction.S)){
                    drone.turnLeft();
                }
                else{
                    drone.turnRight();
                }
                actionLog.addLog(Action.TURN);
            }
            case 2 -> {
                drone.fly();
                actionLog.addLog(Action.FLY);
            }
            default -> {
                radar.useRadar(drone.getDirection());
                actionLog.addLog(Action.ECHOF);
                slideStage = 0;
                looping = false;
                eastCoast = !eastCoast;
            }
        }
        slideStage++;
    }

    private void echoFrontDecision(){
        if(radar.distanceToLand() == -1 && afterSlide){
            looping = true;
            if(slideDirection == Drone.Direction.E){
                slideDirection = Drone.Direction.W;
                loopDirection = drone.getDirection();
                eastCoast = true;
            }
            else{
                eastCoast = false;
                loopDirection = drone.getDirection();
                slideDirection = Drone.Direction.E;
            }
            loop();
        }
        else if(radar.distanceToLand() == -1){
            if(drone.getRightDirection() == slideDirection){
                radar.useRadar(drone.getRightDirection());
                actionLog.addLog(Action.ECHOR);
            }
            else{
                radar.useRadar(drone.getLeftDirection());
                actionLog.addLog(Action.ECHOL);
            }
        }
        else{
            range = radar.distanceToLand();
            drone.fly();
            actionLog.addLog(Action.FLY);
            range --;
        }
        afterSlide = false;
    }

    private void echoSideDirection(){
        if(radar.distanceToLand() == -1){
            verticalSlide();
        }
        else{
            drone.fly();
            actionLog.addLog(Action.NONE);
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
        return siteFound && creeks.numberOfCreeks() == 10;
    }
}

