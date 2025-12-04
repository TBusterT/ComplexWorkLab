package command;

import Model.Accessory;
import Model.Bouquet;

import java.util.List;
import java.util.Scanner;

public class RemoveAccessoryCommand implements Command {

    private final Bouquet bouquet;

    public RemoveAccessoryCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Remove an accessory from the bouquet by index";
    }

    @Override
    public void execute(String[] args) {
        List<Accessory> list = bouquet.getAccessories();
        if (list.isEmpty()) {
            System.out.println("No accessories to remove.");
            return;
        }
        System.out.println("Accessories:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + " - " + list.get(i));
        }
        System.out.print("Enter accessory number to remove: ");
        Scanner sc = new Scanner(System.in);
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx >= 0 && idx < list.size()) {
                Accessory removed = list.get(idx);
                bouquet.removeAccessory(removed);
                System.out.println("Removed: " + removed);
            } else {
                System.out.println("Index out of range.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}
