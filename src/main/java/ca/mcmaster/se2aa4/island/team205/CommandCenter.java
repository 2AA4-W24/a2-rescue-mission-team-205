package ca.mcmaster.se2aa4.island.team205;

public class CommandCenter implements Mission{

    private final Information info = new UsingJSON();

    private final Point point = new Point(0,0);

    private final Radar radar = new Radar(info);

    private final Drone drone = new Drone(info, point);

    private final ActionLog actionLog = new ActionLog();

    private final SearchAlgorithm gridSearch = new GridSearch(info, drone, radar, actionLog);

    private boolean positioning = false;

    private boolean landSpotted = false;

    private boolean land = false;

    private int range = -1;

    private int turns = 1;

    public CommandCenter(String s){
        info.results(s);
        drone.initialize();
    }

    public Drone getDrone(){
        return drone;
    }

    @Override
    public void updateInformation(String s){
        info.results(s);
        drone.drain(info.cost());
    }

    @Override
    public void takeCommand(){
        if(drone.getBattery() <= 30){
            drone.returnHome();
        }
        else if(positioning){
            landSpottedOnSide();
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
        radar.useRadar(drone.getDirection());
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
                radar.useRadar(drone.getRightDirection());
                actionLog.addLog(Action.ECHOR);
            }
        }
        else if (actionLog.getPrev() == Action.ECHOR) {
            if (radar.distanceToLand() != -1) {
                findRange();
                range--;
                landSpottedOnSide();
            }
            else {
                radar.useRadar(drone.getLeftDirection());
                actionLog.addLog(Action.ECHOL);
            }
        }
        else if (actionLog.getPrev() == Action.ECHOL) {
            if (radar.distanceToLand() != -1) {
                findRange();
                range--;
                landSpottedOnSide();
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

    private void landSpottedOnSide(){
        positioning = true;
        switch (turns % 4) {
            case  2, 3 -> {
                if (actionLog.getPrev() == Action.ECHOL){
                    drone.turnLeft();
                }
                else{
                    drone.turnRight();
                }
                range --;
            }
            case 1 -> drone.fly();
            default -> {
                if (actionLog.getPrev() == Action.ECHOL){
                    drone.turnRight();
                }
                else{
                    drone.turnLeft();
                }
                turns = 0;
                positioning = false;
                range--;
            }
        }
        turns ++;
    }

    private void flyToLand(){
        if(range > 0){
            drone.fly();
            range --;
            actionLog.addLog(Action.FLY);
        }
        else{
            land = true;
            findSite();
        }
    }

    private void findSite(){
        gridSearch.findEmergencySite();
    }

    private PointOfInterest closestCreek(){
        return gridSearch.closestCreek();
    }

    @Override
    public String finalReport(){
        PointOfInterest creek = closestCreek();
        if(creek == null){
            return "";
        }
        else{
            return creek.getIdentifier();
        }
    }

    @Override
    public String decision(){
        return info.decision();
    }
}
