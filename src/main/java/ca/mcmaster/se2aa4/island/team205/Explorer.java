package ca.mcmaster.se2aa4.island.team205;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;



public class Explorer implements IExplorerRaid {

 private final Logger logger = LogManager.getLogger();

    private CommandCenter center;

    @Override
    public void initialize(String s) {
        center = new CommandCenter(s);
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));

        String start = info.toString(2);
        logger.info("** Initialization info:\n {}",start);
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", center);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        center.takeCommand();
        return center.decision();
    }

    @Override
    public void acknowledgeResults(String s) {
        center.updateInformation(s);
    }

    @Override
    public String deliverFinalReport() {
        logger.info("final Report: " + center.finalReport());
        return center.finalReport();
    }
}
