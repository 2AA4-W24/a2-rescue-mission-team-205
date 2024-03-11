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
        String decision = center.decision();
        logger.info("** Decision: {}",decision);
        return center.decision();
    }

    @Override
    public void acknowledgeResults(String s) {
        center.updateInformation(s);
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        logger.info(center.getRange());
    }

    @Override
    public String deliverFinalReport() {
        logger.info("final Report: " + center.finalReport());
        return center.finalReport();
    }
}
