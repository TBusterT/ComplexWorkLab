package main.command;

import main.model.Bouquet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadBouquetCommand implements Command {

    private static final Logger logger =
            LogManager.getLogger(LoadBouquetCommand.class);

    private final Bouquet bouquet;

    public LoadBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        logger.info("LoadBouquetCommand started");

        System.out.print("Filename: ");
        String file = AbstractMenu.sc.nextLine();

        try {
            bouquet.loadFromFile(file);
            logger.info("Bouquet loaded from file: {}", file);
        } catch (Exception e) {
            logger.error("Failed to load bouquet from file: {}", file, e);
        }
    }

    @Override
    public String getName() {
        return "load";
    }

    @Override
    public String getDesc() {
        return "Load bouquet from file";
    }
}
