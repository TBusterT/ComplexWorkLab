package main.command;

import main.model.Accessory;
import main.model.Bouquet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RemoveAccessoryCommand implements Command {

    private static final Logger logger =
            LogManager.getLogger(RemoveAccessoryCommand.class);

    private final Bouquet bouquet;

    public RemoveAccessoryCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        logger.info("RemoveAccessoryCommand started");

        List<Accessory> list = bouquet.getAccessories();

        if (list.isEmpty()) {
            logger.warn("Attempt to remove accessory from empty bouquet");
            System.out.println("No accessories to remove.");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + " - " + list.get(i));
        }

        System.out.print("Choose: ");
        try {
            int idx = Integer.parseInt(AbstractMenu.sc.nextLine()) - 1;

            if (idx < 0 || idx >= list.size()) {
                logger.warn("Invalid accessory index: {}", idx + 1);
                System.out.println("Invalid choice.");
                return;
            }

            Accessory removed = list.get(idx);
            bouquet.removeAccessory(removed);

            logger.info("Accessory removed: {}", removed);
        } catch (NumberFormatException e) {
            logger.warn("Invalid number format while removing accessory", e);
            System.out.println("Please enter a valid number.");
        }
    }

    @Override
    public String getName() {
        return "removeaccessory";
    }

    @Override
    public String getDesc() {
        return "Remove accessory";
    }
}
