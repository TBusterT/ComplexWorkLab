package command;

import model.Accessory;
import model.Bouquet;

public class AddAccessoryCommand implements Command {

    private final Bouquet bouquet;

    public AddAccessoryCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        System.out.print("Name: ");
        String name = AbstractMenu.sc.nextLine();

        System.out.print("Price: ");
        double price = Double.parseDouble(AbstractMenu.sc.nextLine());

        System.out.print("Material/color: ");
        String m = AbstractMenu.sc.nextLine();

        bouquet.addAccessory(new Accessory(name, price, m));
        System.out.println("Accessory added.");
    }

    @Override public String getName() { return "addaccessory"; }
    @Override public String getDesc() { return "Add accessory"; }
}
