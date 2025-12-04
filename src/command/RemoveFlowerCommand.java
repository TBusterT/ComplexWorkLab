package command;

import Model.Bouquet;
import Model.Flower;

import java.util.Scanner;

public class RemoveFlowerCommand implements Command {

    private final Bouquet bouquet;

    public RemoveFlowerCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Remove a flower from the bouquet by name";
    }

    @Override
    public void execute(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the name of the flower to remove: ");
        String name = sc.nextLine().trim();

        Flower target = null;
        for (Flower f : bouquet.getFlowers()) {
            if (f.getName().equalsIgnoreCase(name)) {
                target = f;
                break;
            }
        }

        if (target != null) {
            bouquet.removeFlower(target);
            System.out.println("Flower removed: " + target);
        } else {
            System.out.println("No flower with this name found.");
        }
    }
}
