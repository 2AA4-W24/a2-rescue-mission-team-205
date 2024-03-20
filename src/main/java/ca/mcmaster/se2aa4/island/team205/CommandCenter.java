package ca.mcmaster.se2aa4.island.team205;

public class CommandCenter {

    private final Information info = new UsingJSON();

    private final Point point = new Point();

    private final Radar radar = new Radar(info);

    private final Drone drone = new Drone(info, point);

    private final ActionLog actionLog = new ActionLog();

    private final SearchAlgorithm gridSearch = new GridSearch2(info, drone, radar, actionLog);

    private int range = -1;

    private boolean landSpotted = false;

    private boolean land = false;

    private PointOfInterest creek;

    public CommandCenter(String s){
        info.results(s);
        drone.initialize(s);
    }

    public Drone getDrone(){
        return drone;
    }

    public void updateInformation(String s){
        info.results(s);
        drone.drain(info.cost());
    }

    public void takeCommand(){
        if(drone.getBattery() <= 30){
            creek = closestCreek();
            drone.returnHome();
        }
        else if(range != 0 && !land){
            phaseOne();
        }
        else {
            findSite();
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

    private void findSite(){
        gridSearch.findEmergencySite();
    }

    private PointOfInterest closestCreek(){
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
