package main.java.command;

import main.java.model.Bouquet;
import main.java.model.Flower;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFlowersCommand implements Command {

    private final Bouquet bouquet;

    public SearchFlowersCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        System.out.println("1 - Name contains");
        System.out.println("2 - Color");
        System.out.print("> ");

        String opt = AbstractMenu.sc.nextLine();
        List<Flower> res;

        if ("1".equals(opt)) {
            System.out.print("Text: ");
            String t = AbstractMenu.sc.nextLine().toLowerCase();
            res = bouquet.getFlowers().stream()
                    .filter(f -> f.getName().toLowerCase().contains(t))
                    .collect(Collectors.toList());
        } else {
            System.out.print("Color: ");
            String c = AbstractMenu.sc.nextLine();
            res = bouquet.getFlowers().stream()
                    .filter(f -> f.getColor().equalsIgnoreCase(c))
                    .collect(Collectors.toList());
        }

        res.forEach(System.out::println);
    }

    @Override public String getName() { return "searchflowers"; }
    @Override public String getDesc() { return "Search flowers"; }
}
