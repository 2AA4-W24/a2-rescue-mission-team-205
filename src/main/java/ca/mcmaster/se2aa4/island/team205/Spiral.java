package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Spiral {

    private final Information info;

    private final PhotoScanner photoScanner;

    private final Drone drone;

    private final ActionLog actionLog;

    private final Logger logger = LogManager.getLogger();

    private final CreekLocations creeks = new CreekLocations();

    private final Point curCreekLoc;

    private int turnStage = 1;

    private int spiralStage = 1;

    private int flyAmount = 1;

    private int curAmount = 0;

    private boolean turnComplete = false;

    private boolean spiralComplete = false;

    public Spiral(Information information, Drone drone1, ActionLog log, Point point1){
        info = information;
        drone = drone1;
        photoScanner = new PhotoScanner(info, drone, creeks);
        actionLog = log;
        curCreekLoc = point1;
    }

    private void tightTurnLeft() {
        turnComplete = false;
        if (turnStage % 5 == 1) {
            drone.fly();
            actionLog.addLog(Action.FLY);
        }
        else if (turnStage % 5 == 2) {
            drone.turnRight();
            actionLog.addLog(Action.TURN);
        }
        else if (turnStage % 5 == 3) {
            drone.turnRight();
            actionLog.addLog(Action.TURN);
        }
        else if (turnStage % 5 == 4) {
            drone.turnRight();
            actionLog.addLog(Action.TURN);
        }
        else {
            drone.fly();
            actionLog.addLog(Action.FLY);
            turnComplete = true;
        }
        turnStage++;
    }

    // call this continuously
    public void searchRadially() {
        // spiraling not done
        if (!spiralComplete) {
            if (actionLog.getPrev() == Action.FLY) {
                photoScanner.scanTerrain();
                actionLog.addLog(Action.SCAN);
            }
            else if(actionLog.getPrev() == Action.SCAN) {
                // creek found
                if(photoScanner.creekScan()) {
                    // check creek by comparing their points
                    // same creek -> spiral is complete, the old creek was the closest
                    // new creek -> spiral is complete, the new creek is the closest
                    spiralComplete = true;
                }
                // no creek found
                if (curAmount < flyAmount) {
                    drone.fly();
                    actionLog.addLog(Action.FLY);
                    curAmount++;
                }
                if (curAmount == flyAmount) {
                    tightTurnLeft();
                    if (turnComplete) {
                        spiralStage++;
                        curAmount = 0;
                    }
                }
                if (spiralStage % 3 == 0) {
                    flyAmount++;
                    spiralStage++;
                }
            }
        }
        // spiraling done
        else {
            // should this return something to signify that it is done?
        }
    }
}
