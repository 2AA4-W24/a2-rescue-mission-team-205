package ca.mcmaster.se2aa4.island.team205;

public class GridSearch implements SearchAlgorithm{

    CreekLocations creeks = new CreekLocations();

    private PointOfInterest site;

    private boolean done = false;

    private boolean initialized = false;

    private boolean turned = false;

    private boolean reachedEdge = false;

    private boolean aboveOcean = false;

    private final Information info = new UsingJSON();

    private final Drone drone = new Drone(info);

    private final PhotoScanner scanner = new PhotoScanner(info, drone,  new CreekLocations());

    private final Radar radar = new Radar(info);

    private Integer stage = 1;

    private Turn lastTurn = Turn.RIGHT;

    private ActionLog log;

    public GridSearch(ActionLog actionLog) {
        log = actionLog;
    }

    @Override
    public void findEmergencySite() {
        //when site is found
        site = new PointOfInterest("identifier", new int[]{4,6});
    }

    @Override
    public void findCreeks() {
        if (!done) {
            if (!initialized) {
                initialize();
            }
            else {
                if (!aboveOcean) {
                    generalMovement();
                }
                else {
                    uTurn();
                }
            }
        }
    }

    @Override
    public PointOfInterest closestCreek(){
        return creeks.closestCreak(site);
    }

    @Override
    public boolean isSiteFound() {
        return false;
    }

    public void initialize() {
        if (!turned) {
            drone.turnRight();
            turned = true;
            log.addLog(Action.TURN);
        }
        else if (!reachedEdge) {
            generalMovement();
        }
        else {
            drone.turnRight();
            log.addLog(Action.TURN);
            if (log.getPrev() == Action.TURN) {
                initialized = true;
            }
        }
    }

    private void generalMovement() {
        if (log.getPrev() == Action.SCAN) {
            checkScanResults();
            if (!aboveOcean) {
                drone.fly();
                log.addLog(Action.FLY);
            }
        }
        else if(log.getPrev() == Action.TURN) {
            drone.fly();
            log.addLog(Action.FLY);
        }
        else if(log.getPrev() == Action.ECHOF) {
            if(radar.distanceToLand() == -1) {
                done = true;
            }
            else {
                drone.fly();
                log.addLog(Action.FLY);
            }
        }
        else {
            scanner.scanTerrain();
            log.addLog(Action.SCAN);
        }
    }

    private void checkScanResults() {
        if(scanner.scanOcean()) {
            aboveOcean = true;
            if (!reachedEdge) {
                reachedEdge = true;
            }
        }
        else if(!scanner.scanOcean()){
            aboveOcean = false;
        }
        else if(scanner.scanCreek()) {
            String creek = scanner.getCreekResults().getLast();
            creeks.addCreek(new PointOfInterest(creek, drone.getLocation()));
        }
        else if(scanner.scanSite()) {
            String foundSite = scanner.getSiteResults().getLast();
            site = new PointOfInterest(foundSite, drone.getLocation());
        }
    }

    private void uTurn() {
        if(stage%4 == 1) {
            nextTurn();
        }
        else if (stage%4 == 2) {
            drone.fly();
            log.addLog(Action.FLY);
        }
        else if (stage%4 == 3){
            nextTurn();
        }
        else {
            radar.useRadarFront(drone.getDirection());
            log.addLog(Action.ECHOF);
        }
    }

    private void nextTurn() {
        switch (lastTurn) {
            case LEFT -> {
                drone.turnRight();
                lastTurn = Turn.RIGHT;
                log.addLog(Action.TURN);
            }
            case RIGHT -> {
                drone.turnLeft();
                lastTurn = Turn.LEFT;
                log.addLog(Action.TURN);
            }
        }
    }

    private enum Turn {
        LEFT,
        RIGHT
    }
}
