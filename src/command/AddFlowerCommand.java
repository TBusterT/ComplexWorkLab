package command;

import Model.*;

import java.util.Scanner;

public class AddFlowerCommand implements Command {

    private final Bouquet bouquet;

    public AddFlowerCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Add a flower to the bouquet";
    }

    @Override
    public void execute(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose flower type:");
        System.out.println("1 - Regular Flower");
        System.out.println("2 - Garden Flower");
        System.out.println("3 - Exotic Flower");
        System.out.println("4 - Decorative Plant");
        System.out.print("> ");
        int type = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Enter flower name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter color: ");
        String color = sc.nextLine().trim();

        System.out.print("Enter stem length (cm): ");
        double stem = Double.parseDouble(sc.nextLine().trim());

        System.out.print("Enter price: ");
        double price = Double.parseDouble(sc.nextLine().trim());

        System.out.print("Enter freshness (1-10): ");
        int freshness = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Enter lifespan (days): ");
        int lifespan = Integer.parseInt(sc.nextLine().trim());

        Flower flower;

        switch (type) {
            case 2:
                System.out.print("Enter variety: ");
                String variety = sc.nextLine().trim();

                System.out.print("Enter fragrance level: ");
                String fragrance = sc.nextLine().trim();

                flower = new GardenFlower(name, color, stem, price, freshness, lifespan, variety, fragrance);
                break;

            case 3:
                System.out.print("Enter country of origin: ");
                String origin = sc.nextLine().trim();

                System.out.print("Enter care complexity: ");
                String care = sc.nextLine().trim();

                flower = new ExoticFlower(name, color, stem, price, freshness, lifespan, origin, care);
                break;
            case 4:

                System.out.print("Enter type (e.g. fern, succulent): ");
                String dType = sc.nextLine().trim();

                System.out.print("Enter texture: ");
                String texture = sc.nextLine().trim();

                flower = new DecorativePlant(name, color, stem, price, freshness, lifespan, dType, texture);
                break;
            default:
                flower = new Flower(name, color, stem, price, freshness, lifespan);
                break;
        }

        bouquet.addFlower(flower);
        System.out.println("Flower successfully added:");
        System.out.println(flower);
    }
}
