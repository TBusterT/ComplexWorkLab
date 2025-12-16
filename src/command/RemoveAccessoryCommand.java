package command;

import model.Accessory;
import model.Bouquet;
import java.util.List;

public class RemoveAccessoryCommand implements Command {

    private final Bouquet bouquet;

    public RemoveAccessoryCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        List<Accessory> list = bouquet.getAccessories();
        for (int i = 0; i < list.size(); i++)
            System.out.println((i+1) + " - " + list.get(i));

        System.out.print("Choose: ");
        int idx = Integer.parseInt(AbstractMenu.sc.nextLine()) - 1;

        bouquet.removeAccessory(list.get(idx));
        System.out.println("Removed.");
    }

    @Override public String getName() { return "removeaccessory"; }
    @Override public String getDesc() { return "Remove accessory"; }
}
