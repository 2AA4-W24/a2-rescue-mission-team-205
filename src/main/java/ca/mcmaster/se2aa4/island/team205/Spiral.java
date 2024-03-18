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
            turnStage = 0;
        }
        turnStage++;
    }

    public boolean isSpiralComplete(){
        return spiralComplete;
    }

    // call this continuously
    public void searchRadially() {
        // spiraling not done
        logger.info("-----------------------------------spirl");
        if (!spiralComplete) {
            if (actionLog.getPrev() == Action.FLY && turnComplete) {
                photoScanner.scanTerrain();
                actionLog.addLog(Action.SCAN);
            }
            else if(actionLog.getPrev() == Action.SCAN) {
                // creek found
                int i = photoScanner.numberOfCreeks();
                if (photoScanner.creekScan()) {
                    // check creek by comparing their points
                    // same creek -> spiral is complete, the old creek was the closest
                    // new creek -> spiral is complete, the new creek is the closest
                    if (i != photoScanner.numberOfCreeks()) {
                        spiralComplete = true;
                        drone.returnHome();
                    } else {
                        if (curAmount < flyAmount) {
                            drone.fly();
                            actionLog.addLog(Action.FLY);
                            curAmount++;
                        } else if (curAmount == flyAmount) {
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
                } else {
                    spiralComplete = true;
                    drone.returnHome();
                }
            }
            else if(!turnComplete){
                tightTurnLeft();
            }
                /* no creek found
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

                 */

        }
        else {
            drone.returnHome();
        }
    }
}
