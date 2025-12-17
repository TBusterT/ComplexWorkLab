package main.command;

import main.model.Bouquet;
import main.model.Flower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

public class SortFlowersCommand implements Command {

    private static final Logger logger =
            LogManager.getLogger(SortFlowersCommand.class);

    private final Bouquet bouquet;

    public SortFlowersCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        logger.info("SortFlowersCommand started");

        System.out.println("1 - Freshness desc");
        System.out.println("2 - Price asc");
        System.out.print("> ");

        String opt = AbstractMenu.sc.nextLine();

        switch (opt) {
            case "1":
                bouquet.getFlowers().sort(Comparator.comparingInt(Flower::getFreshness).reversed());
                logger.info("Flowers sorted by freshness descending");
                break;
            case "2":
                bouquet.getFlowers().sort(Comparator.comparingDouble(Flower::getPrice));
                logger.info("Flowers sorted by price ascending");
                break;
            default:
                logger.warn("Unknown sort option: '{}'", opt);
                System.out.println("Unknown option");
                return;
        }

        System.out.println("Sorted.");
        logger.info("SortFlowersCommand completed successfully");
    }

    @Override
    public String getName() {
        return "sortflowers";
    }

    @Override
    public String getDesc() {
        return "Sort flowers";
    }
}
