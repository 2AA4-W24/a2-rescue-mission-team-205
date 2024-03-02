package ca.mcmaster.se2aa4.island.team205;


public class CommandCenter {

    private final Information info = new UsingJSON();

    private final Radar radar = new Radar(info);

    private final Drone drone = new Drone(info);

    private final SearchAlgorithm search = new GridSearch();

    private int commands = 1;

    private int range = -1;

    private boolean landSpotted = false;

    private boolean emergencySiteFound = false;

    private boolean creekSearching = true;

    private boolean closestCreekFound = false;

    private PointOfInterest creek;

    private final ActionLog actionLog = new ActionLog();


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

        if(drone.battery <= 15){
            drone.returnHome();
        }
        else if(range != 0){
            phaseOne();
            commands ++;
        }
        else if(creekSearching){
            drone.returnHome();
            findCreeks();
            commands++;
        }
        else if(!emergencySiteFound){
            findSite();
            commands ++;
        }
        else if(!closestCreekFound){
            creek = closestCreek();
            commands ++;
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
        if (commands % 5 == 0) {
            radar.useRadarFront(drone.getDirection());
            actionLog.addLog(Action.ECHOF);
        }
        else {
            drone.fly();
            actionLog.addLog(Action.FLY);
        }
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
                radar.useRadarRight(drone.getDirection());
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
                radar.useRadarLeft(drone.getDirection());
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
        }
    }

    private void findCreeks(){
        //implement grid search
        drone.returnHome();
        search.findCreeks();
        //once done looking
        creekSearching = false;
    }

    private void findSite(){
        //implement grid search
        //if last move was fly or radar do
        search.findEmergencySite();
        //else check radar result and fly
        emergencySiteFound = true;
    }

    private PointOfInterest closestCreek(){
    //start looking radially outward
        return search.closestCreek();
    }

    public String finalReport(){
        return creek.identifier;
    }


    public String decision(){
        return info.decision();
    }
}
