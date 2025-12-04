package command;

import Model.Bouquet;
import Model.Flower;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SearchFlowersCommand implements Command {

    private final Bouquet bouquet;

    public SearchFlowersCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Search and filter flowers";
    }

    @Override
    public void execute(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Search by:");
        System.out.println("1 - Name contains");
        System.out.println("2 - Color equals");
        System.out.println("3 - Stem length range");
        System.out.print("> ");
        String opt = sc.nextLine().trim();

        List<Flower> src = bouquet.getFlowers();
        List<Flower> res = null;

        switch (opt) {
            case "1":
                System.out.print("Enter substring (case-insensitive): ");
                String sub = sc.nextLine().trim().toLowerCase();
                res = src.stream()
                        .filter(f -> f.getName() != null && f.getName().toLowerCase().contains(sub))
                        .collect(Collectors.toList());
                break;
            case "2":
                System.out.print("Enter color: ");
                String color = sc.nextLine().trim().toLowerCase();
                res = src.stream()
                        .filter(f -> f.getColor() != null && f.getColor().equalsIgnoreCase(color))
                        .collect(Collectors.toList());
                break;
            case "3":
                try {
                    System.out.print("Enter min stem length: ");
                    double min = Double.parseDouble(sc.nextLine().trim());
                    System.out.print("Enter max stem length: ");
                    double max = Double.parseDouble(sc.nextLine().trim());
                    res = bouquet.findByStemLengthRange(min, max);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid numbers.");
                    return;
                }
                break;
            default:
                System.out.println("Unknown option.");
                return;
        }

        if (res == null || res.isEmpty()) {
            System.out.println("No flowers found.");
        } else {
            System.out.println("Found flowers:");
            for (int i = 0; i < res.size(); i++) {
                System.out.println((i+1) + " - " + res.get(i));
            }
        }
    }
}
