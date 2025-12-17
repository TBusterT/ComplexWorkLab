package main.java.command;

import main.java.model.*;
import java.util.Scanner;

public class AddFlowerCommand implements Command {

    private final Bouquet bouquet;

    public AddFlowerCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        Scanner sc = AbstractMenu.sc;

        System.out.println("Choose flower type:");
        System.out.println("1 - Regular Flower");
        System.out.println("2 - Garden Flower");
        System.out.println("3 - Exotic Flower");
        System.out.println("4 - Decorative Plant");
        System.out.print("> ");

        int type = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Color: ");
        String color = sc.nextLine();

        System.out.print("Stem length: ");
        double stem = Double.parseDouble(sc.nextLine());

        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine());

        System.out.print("Freshness: ");
        int fresh = Integer.parseInt(sc.nextLine());

        System.out.print("Lifespan: ");
        int life = Integer.parseInt(sc.nextLine());

        Flower flower;

        switch (type) {
            case 2:
                System.out.print("Variety: ");
                String variety = sc.nextLine();
                System.out.print("Fragrance: ");
                String frag = sc.nextLine();
                flower = new GardenFlower(name, color, stem, price, fresh, life, variety, frag);
                break;

            case 3:
                System.out.print("Origin country: ");
                String origin = sc.nextLine();
                System.out.print("Care complexity: ");
                String care = sc.nextLine();
                flower = new ExoticFlower(name, color, stem, price, fresh, life, origin, care);
                break;

            case 4:
                System.out.print("Type: ");
                String t = sc.nextLine();
                System.out.print("Texture: ");
                String tex = sc.nextLine();
                flower = new DecorativePlant(name, color, stem, price, fresh, life, t, tex);
                break;

            default:
                flower = new Flower(name, color, stem, price, fresh, life);
        }

        bouquet.addFlower(flower);
        System.out.println("Added: " + flower);
    }

    @Override public String getName() { return "addflower"; }
    @Override public String getDesc() { return "Add flower to bouquet"; }
}
