package main.command;

import main.model.Bouquet;
import main.model.Flower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RemoveFlowerCommand implements Command {

    private static final Logger logger =
            LogManager.getLogger(RemoveFlowerCommand.class);

    private final Bouquet bouquet;

    public RemoveFlowerCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        logger.info("RemoveFlowerCommand started");

        List<Flower> list = bouquet.getFlowers();

        if (list.isEmpty()) {
            logger.warn("Attempt to remove flower from empty bouquet");
            System.out.println("No flowers.");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + " - " + list.get(i));
        }

        System.out.print("Choose number: ");
        try {
            int idx = Integer.parseInt(AbstractMenu.sc.nextLine()) - 1;

            if (idx < 0 || idx >= list.size()) {
                logger.warn("Invalid flower index: {}", idx + 1);
                System.out.println("Wrong index.");
                return;
            }

            Flower f = list.get(idx);
            bouquet.removeFlower(f);
            logger.info("Flower removed: {}", f);
        } catch (NumberFormatException e) {
            logger.warn("Invalid number format while removing flower", e);
            System.out.println("Please enter a valid number.");
        }
    }

    @Override
    public String getName() {
        return "removeflower";
    }

    @Override
    public String getDesc() {
        return "Remove flower by index";
    }
}
