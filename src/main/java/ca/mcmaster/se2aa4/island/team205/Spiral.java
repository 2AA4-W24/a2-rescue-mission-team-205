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

    private boolean turnComplete = true;

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
            logger.info("----------------------tight turn1");
            drone.fly();
            actionLog.addLog(Action.FLY);
        }
        else if (turnStage % 5 == 2) {
            logger.info("----------------------tight turn2");
            drone.turnRight();
            actionLog.addLog(Action.TURN);
        }
        else if (turnStage % 5 == 3) {
            logger.info("----------------------tight turn3");
            drone.turnRight();
            actionLog.addLog(Action.TURN);
        }
        else if (turnStage % 5 == 4) {
            logger.info("----------------------tight turn4");
            drone.turnRight();
            actionLog.addLog(Action.TURN);
        }
        else {
            logger.info("----------------------tight turn5");
            drone.fly();
            actionLog.addLog(Action.FLY);
            turnComplete = true;
            turnStage = 0;
            spiralStage++;
            curAmount = 0;
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
            if (!turnComplete) {
                logger.info("----------------------first if");
                tightTurnLeft();
            }
            else if (actionLog.getPrev() == Action.FLY) {
                logger.info("----------------------second if");
                photoScanner.scanTerrain();
                actionLog.addLog(Action.SCAN);
            }
            else if(actionLog.getPrev() == Action.SCAN) {
                logger.info("----------------------third if");
                // creek found
                int i = photoScanner.numberOfCreeks();
                if (photoScanner.creekScan()) { // true if new creek or no creek
                    // check creek by comparing their points
                    // same creek -> spiral is complete, the old creek was the closest
                    // new creek -> spiral is complete, the new creek is the closest
                    if (i != photoScanner.numberOfCreeks()) { // new creek is added
                        logger.info("----------------------spiral complete1");
                        spiralComplete = true;
                        drone.returnHome();
                    } else { // no creek
                        logger.info("----------------------third if - no creek scanned");
                        if (curAmount < flyAmount) {
                            logger.info("----------------------if cur<fly");
                            drone.fly();
                            actionLog.addLog(Action.FLY);
                            curAmount++;
                        } else if (curAmount == flyAmount) {
                            logger.info("----------------------if cur=fly");
                            tightTurnLeft();
//                            logger.info("----------------------just ran tightTurnLeft()");
//                            if (turnComplete) {
//                                logger.info("----------------------TURN COMPLETE");
//                                spiralStage++;
//                                curAmount = 0;
//                            }
                        }
                        if (spiralStage % 3 == 0) {
                            logger.info("----------------------new spiral stage");
                            flyAmount++;
                            spiralStage++;
                        }
                    }
                } else {
                    logger.info("----------------------spiral complete2");
                    spiralComplete = true;
                    drone.returnHome();
                }
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
