package ca.mcmaster.se2aa4.island.team205;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private CommandCenter center;

    @Override
    public void initialize(String s) {
        center = new CommandCenter(s);
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
        String identifier = center.finalReport();
        logger.info(identifier);
        return identifier;
    }
}
