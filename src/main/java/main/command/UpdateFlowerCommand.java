package main.command;

import main.model.Bouquet;
import main.model.Flower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UpdateFlowerCommand implements Command {

    private static final Logger logger =
            LogManager.getLogger(UpdateFlowerCommand.class);

    private final Bouquet bouquet;

    public UpdateFlowerCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        logger.info("UpdateFlowerCommand started");

        List<Flower> list = bouquet.getFlowers();
        if (list.isEmpty()) {
            logger.warn("Attempt to update flower in empty bouquet");
            System.out.println("No flowers.");
            return;
        }

        for (int i = 0; i < list.size(); i++)
            System.out.println((i + 1) + " - " + list.get(i));

        System.out.print("Choose number: ");
        try {
            int idx = Integer.parseInt(AbstractMenu.sc.nextLine()) - 1;

            if (idx < 0 || idx >= list.size()) {
                logger.warn("Invalid flower index selected: {}", idx + 1);
                System.out.println("Wrong index.");
                return;
            }

            Flower f = list.get(idx);

            System.out.print("New price (" + f.getPrice() + "): ");
            String priceInput = AbstractMenu.sc.nextLine();
            if (!priceInput.isEmpty()) {
                double newPrice = Double.parseDouble(priceInput);
                f.setPrice(newPrice);
                logger.info("Updated price of flower {} to {}", f.getName(), newPrice);
            }

            System.out.print("New freshness (" + f.getFreshness() + "): ");
            String freshInput = AbstractMenu.sc.nextLine();
            if (!freshInput.isEmpty()) {
                int newFreshness = Integer.parseInt(freshInput);
                f.setFreshness(newFreshness);
                logger.info("Updated freshness of flower {} to {}", f.getName(), newFreshness);
            }

            System.out.println("Updated: " + f);
            logger.info("Flower updated successfully: {}", f);

        } catch (NumberFormatException e) {
            logger.warn("Invalid input format during flower update", e);
            System.out.println("Please enter valid numbers.");
        }
    }

    @Override
    public String getName() {
        return "updateflower";
    }

    @Override
    public String getDesc() {
        return "Edit flower";
    }
}
