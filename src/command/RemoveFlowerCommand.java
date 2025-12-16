package command;

import model.Bouquet;
import model.Flower;
import java.util.List;

public class RemoveFlowerCommand implements Command {

    private final Bouquet bouquet;

    public RemoveFlowerCommand(Bouquet bouquet) {
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

        if (idx < 0 || idx >= list.size()) {
            System.out.println("Wrong index.");
            return;
        }

        Flower f = list.get(idx);
        bouquet.removeFlower(f);
        System.out.println("Removed: " + f);
    }

    @Override public String getName() { return "removeflower"; }
    @Override public String getDesc() { return "Remove flower by index"; }
}
