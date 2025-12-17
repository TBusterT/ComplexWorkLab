package main.java.command;

import main.java.model.Bouquet;
import main.java.model.Flower;
import java.util.List;

public class UpdateFlowerCommand implements Command {

    private final Bouquet bouquet;

    public UpdateFlowerCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        List<Flower> list = bouquet.getFlowers();
        if (list.isEmpty()) {
            System.out.println("No flowers.");
            return;
        }

        for (int i = 0; i < list.size(); i++)
            System.out.println((i+1) + " - " + list.get(i));

        System.out.print("Choose number: ");
        int idx = Integer.parseInt(AbstractMenu.sc.nextLine()) - 1;

        if (idx < 0 || idx >= list.size()) return;

        Flower f = list.get(idx);

        System.out.print("New price ("+f.getPrice()+"): ");
        String s = AbstractMenu.sc.nextLine();
        if (!s.isEmpty()) f.setPrice(Double.parseDouble(s));

        System.out.print("New freshness ("+f.getFreshness()+"): ");
        s = AbstractMenu.sc.nextLine();
        if (!s.isEmpty()) f.setFreshness(Integer.parseInt(s));

        System.out.println("Updated: " + f);
    }

    @Override public String getName() { return "updateflower"; }
    @Override public String getDesc() { return "Edit flower"; }
}
