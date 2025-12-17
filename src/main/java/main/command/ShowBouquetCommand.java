package main.command;

import main.model.Bouquet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowBouquetCommand implements Command {

    private static final Logger logger =
            LogManager.getLogger(ShowBouquetCommand.class);

    private final Bouquet bouquet;

    public ShowBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        logger.info("ShowBouquetCommand started");

        System.out.println(bouquet);

        logger.info("Bouquet displayed with {} flowers and {} accessories",
                bouquet.getFlowers().size(), bouquet.getAccessories().size());
        logger.info("ShowBouquetCommand completed successfully");
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDesc() {
        return "Show bouquet";
    }
}
