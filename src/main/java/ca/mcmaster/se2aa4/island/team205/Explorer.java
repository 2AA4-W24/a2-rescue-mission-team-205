package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import eu.ace_design.island.bot.IExplorerRaid;


public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private Mission mission;

    @Override
    public void initialize(String s) {
        mission = new CommandCenter(s);
    }

    @Override
    public String takeDecision() {
        mission.takeCommand();
        return mission.decision();
    }

    @Override
    public void acknowledgeResults(String s) {
        mission.updateInformation(s);
    }

    @Override
    public String deliverFinalReport() {
        String identifier = mission.finalReport();
        logger.info(identifier);
        return identifier;
    }
}
