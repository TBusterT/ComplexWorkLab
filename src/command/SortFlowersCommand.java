package command;

import Model.Bouquet;
import Model.Flower;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SortFlowersCommand implements Command {

    private final Bouquet bouquet;

    public SortFlowersCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Sort flowers by criteria";
    }

    @Override
    public void execute(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sort by:");
        System.out.println("1 - Freshness (desc)");
        System.out.println("2 - Price (asc)");
        System.out.println("3 - Stem length (asc)");
        System.out.print("> ");
        String opt = sc.nextLine().trim();
        List<Flower> list = bouquet.getFlowers();
        switch (opt) {
            case "1":
                list.sort(Comparator.comparingInt(Flower::getFreshness).reversed());
                System.out.println("Sorted by freshness (descending).");
                break;
            case "2":
                list.sort(Comparator.comparingDouble(Flower::getPrice));
                System.out.println("Sorted by price (ascending).");
                break;
            case "3":
                list.sort(Comparator.comparingDouble(Flower::getStemLength));
                System.out.println("Sorted by stem length (ascending).");
                break;
            default:
                System.out.println("Unknown option.");
                return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i+1) + " - " + list.get(i));
        }
    }
}
