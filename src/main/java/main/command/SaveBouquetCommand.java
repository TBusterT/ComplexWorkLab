package main.command;

import main.model.Bouquet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveBouquetCommand implements Command {

    private static final Logger logger =
            LogManager.getLogger(SaveBouquetCommand.class);

    private final Bouquet bouquet;

    public SaveBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        logger.info("SaveBouquetCommand started");

        System.out.print("Filename: ");
        String file = AbstractMenu.sc.nextLine();

        try {
            bouquet.saveToFile(file);
            logger.info("Bouquet saved to file: {}", file);
        } catch (Exception e) {
            logger.error("Failed to save bouquet to file: {}", file, e);
            System.out.println("Cannot save file. See logs for details.");
        }
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDesc() {
        return "Save bouquet to file";
    }
}
