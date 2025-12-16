package menu;

import model.Bouquet;
import command.*;
import java.util.HashMap;
import java.util.Map;

public class AccessoriesMenu extends AbstractMenu {

    public AccessoriesMenu(Bouquet bouquet) {
        super("accessories", "Accessories submenu", createCommands(bouquet));
    }

    private static Map<String, Command> createCommands(Bouquet bouquet) {
        Map<String, Command> map = new HashMap<>();

        map.put("add", new AddAccessoryCommand(bouquet));
        map.put("remove", new RemoveAccessoryCommand(bouquet));

        return map;
    }

    @Override
    protected void menuCycle() {
        while (true) {
            System.out.println("\n--- Accessories Menu ---");
            System.out.println("1 - Add accessory");
            System.out.println("2 - Remove accessory");
            System.out.println("h - Help");
            System.out.println("0 - Back");
            System.out.print("> ");

            switch (sc.nextLine()) {
                case "1": exec("add"); break;
                case "2": exec("remove"); break;
                case "h": help(); break;
                case "0": return;
                default: System.out.println("Wrong option");
            }
        }
    }

}
