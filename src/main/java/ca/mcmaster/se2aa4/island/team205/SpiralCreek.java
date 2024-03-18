package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpiralCreek {

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

    public SpiralCreek(Information information, Drone drone1, ActionLog log, Point point1){
        info = information;
        drone = drone1;
        photoScanner = new PhotoScanner(info, drone, creeks);
        actionLog = log;
        curCreekLoc = point1;
    }
}
