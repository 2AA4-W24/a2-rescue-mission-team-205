package ca.mcmaster.se2aa4.island.team205;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private int stage = 1;
    private int flyCount = 0;
    //initializes drone on the map

    private Drone drone;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");

        drone = new Drone(batteryLevel, direction);
        logger.info("The drone is facing {}", drone.getDirection());
        logger.info("Battery level is {}", drone.getBattery());
    }

    //makes decision for the drone
    @Override
    public String takeDecision() {
        drone.takeCommand();
        JSONObject decision = new JSONObject();
        JSONObject parameters = new JSONObject();

        if (stage == 1) {
//            decision.put("action", "echo");
//            parameters.put("direction", "E");
//            decision.put("parameters", parameters);
            decision.put("action", "fly");
            stage++;
        }
        else if (stage == 2) {
//            decision.put("action", "echo");
//            parameters.put("direction", "S");
//            decision.put("parameters", parameters);
            decision.put("action", "fly");
            stage++;
        }
        else if (stage == 3) {
//            decision.put("action", "echo");
//            parameters.put("direction", "N");
//            decision.put("parameters", parameters);
            decision.put("action", "fly");
            stage++;
        }
        else {
            decision.put("action", "stop");
//            decision.put("action", "fly");
//            flyCount++;
        }
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
//        logger.info("FLY COUNT: "+flyCount*3+" tiles");
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
