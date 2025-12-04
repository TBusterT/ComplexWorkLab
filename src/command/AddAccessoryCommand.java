package command;

import Model.Accessory;
import Model.Bouquet;

import java.util.Scanner;

public class AddAccessoryCommand implements Command {

    private final Bouquet bouquet;

    public AddAccessoryCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Add an accessory to the bouquet";
    }

    @Override
    public void execute(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter accessory name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter price: ");
        double price;
        try {
            price = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Operation cancelled.");
            return;
        }
        System.out.print("Enter material or color: ");
        String mat = sc.nextLine().trim();

        Accessory a = new Accessory(name, price, mat);
        bouquet.addAccessory(a);
        System.out.println("Accessory added: " + a);
    }
}
