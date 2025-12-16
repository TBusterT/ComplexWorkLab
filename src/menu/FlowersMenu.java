package menu;

import model.Bouquet;
import command.*;
import java.util.HashMap;
import java.util.Map;

public class FlowersMenu extends AbstractMenu {

    public FlowersMenu(Bouquet bouquet) {
        super("flowers", "Flowers submenu", createCommands(bouquet));
    }

    private static Map<String, Command> createCommands(Bouquet bouquet) {
        Map<String, Command> map = new HashMap<>();

        map.put("add", new AddFlowerCommand(bouquet));
        map.put("remove", new RemoveFlowerCommand(bouquet));
        map.put("update", new UpdateFlowerCommand(bouquet));
        map.put("sort", new SortFlowersCommand(bouquet));
        map.put("search", new SearchFlowersCommand(bouquet));

        return map;
    }

    @Override
    protected void menuCycle() {
        while (true) {
            System.out.println("\n--- Flowers Menu ---");
            System.out.println("1 - Add flower");
            System.out.println("2 - Remove flower");
            System.out.println("3 - Update flower");
            System.out.println("4 - Sort flowers");
            System.out.println("5 - Search flowers");
            System.out.println("h - Help");
            System.out.println("0 - Back");
            System.out.print("> ");

            switch (sc.nextLine()) {
                case "1": exec("add"); break;
                case "2": exec("remove"); break;
                case "3": exec("update"); break;
                case "4": exec("sort"); break;
                case "5": exec("search"); break;
                case "h": help(); break;
                case "0": return;
                default: System.out.println("Unknown option");
            }
        }
    }
}
