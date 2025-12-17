package main.command;

import main.model.Accessory;
import main.model.Bouquet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddAccessoryCommand implements Command {

    private static final Logger logger =
            LogManager.getLogger(AddAccessoryCommand.class);

    private final Bouquet bouquet;

    public AddAccessoryCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        logger.info("AddAccessoryCommand started");

        System.out.print("Name: ");
        String name = AbstractMenu.sc.nextLine();

        System.out.print("Price: ");
        double price = Double.parseDouble(AbstractMenu.sc.nextLine());

        System.out.print("Material/color: ");
        String material = AbstractMenu.sc.nextLine();

        Accessory accessory = new Accessory(name, price, material);
        bouquet.addAccessory(accessory);

        logger.info("Accessory added: {}", accessory);
    }

    @Override
    public String getName() {
        return "addaccessory";
    }

    @Override
    public String getDesc() {
        return "Add accessory";
    }
}
