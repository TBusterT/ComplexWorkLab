package main.java.command;

import main.java.model.Bouquet;
import main.java.model.Flower;
import java.util.Comparator;

public class SortFlowersCommand implements Command {

    private final Bouquet bouquet;

    public SortFlowersCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        System.out.println("1 - Freshness desc");
        System.out.println("2 - Price asc");
        System.out.print("> ");

        String opt = AbstractMenu.sc.nextLine();

        if ("1".equals(opt))
            bouquet.getFlowers().sort(Comparator.comparingInt(Flower::getFreshness).reversed());
        else if ("2".equals(opt))
            bouquet.getFlowers().sort(Comparator.comparingDouble(Flower::getPrice));

        System.out.println("Sorted.");
    }

    @Override public String getName() { return "sortflowers"; }
    @Override public String getDesc() { return "Sort flowers"; }
}
