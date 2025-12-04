package command;

import Model.Bouquet;
import Model.Flower;

import java.util.List;
import java.util.Scanner;

public class UpdateFlowerCommand implements Command {

    private final Bouquet bouquet;

    public UpdateFlowerCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Update (edit) a flower in the bouquet";
    }

    @Override
    public void execute(String[] args) {
        List<Flower> list = bouquet.getFlowers();
        if (list.isEmpty()) {
            System.out.println("No flowers to update.");
            return;
        }
        System.out.println("Flowers:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + " - " + list.get(i));
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter flower number to update: ");
        int idx;
        try {
            idx = Integer.parseInt(sc.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        if (idx < 0 || idx >= list.size()) {
            System.out.println("Index out of range.");
            return;
        }
        Flower f = list.get(idx);
        System.out.println("Leave input empty to keep current value.");
        System.out.print("Name [" + f.getName() + "]: ");
        String s = sc.nextLine().trim(); if (!s.isEmpty()) f.setName(s);
        System.out.print("Color [" + f.getColor() + "]: ");
        s = sc.nextLine().trim(); if (!s.isEmpty()) f.setColor(s);
        System.out.print("Stem length (" + f.getStemLength() + "): ");
        s = sc.nextLine().trim(); if (!s.isEmpty()) {
            try { f.setStemLength(Double.parseDouble(s)); } catch (Exception ex) { System.out.println("Invalid value ignored."); }
        }
        System.out.print("Price (" + f.getPrice() + "): ");
        s = sc.nextLine().trim(); if (!s.isEmpty()) {
            try { f.setPrice(Double.parseDouble(s)); } catch (Exception ex) { System.out.println("Invalid value ignored."); }
        }
        System.out.print("Freshness (" + f.getFreshness() + "): ");
        s = sc.nextLine().trim(); if (!s.isEmpty()) {
            try { f.setFreshness(Integer.parseInt(s)); } catch (Exception ex) { System.out.println("Invalid value ignored."); }
        }
        System.out.print("Lifespan (" + f.getLifespan() + "): ");
        s = sc.nextLine().trim(); if (!s.isEmpty()) {
            try { f.setLifespan(Integer.parseInt(s)); } catch (Exception ex) { System.out.println("Invalid value ignored."); }
        }

        System.out.println("Flower updated: " + f);
    }
}
