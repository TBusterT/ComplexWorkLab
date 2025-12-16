package menu;

import model.Bouquet;
import command.*;

import java.util.HashMap;
import java.util.Map;

public class MainMenu extends AbstractMenu {

    public MainMenu(Bouquet bouquet) {
        super("main", "Main menu", createCommands(bouquet));
    }

    private static Map<String, Command> createCommands(Bouquet bouquet) {
        Map<String, Command> map = new HashMap<>();

        map.put("flowers", new FlowersMenu(bouquet));
        map.put("accessories", new AccessoriesMenu(bouquet));
        map.put("file", new FileMenu(bouquet));

        map.put("show", new ShowBouquetCommand(bouquet));
        map.put("total", new ShowTotalPriceCommand(bouquet));

        return map;
    }

    @Override
    protected void menuCycle() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1 - Flowers");
            System.out.println("2 - Accessories");
            System.out.println("3 - File");
            System.out.println("4 - Show bouquet");
            System.out.println("5 - Total price");
            System.out.println("h - Help");
            System.out.println("0 - Exit");
            System.out.print("> ");

            switch (sc.nextLine()) {
                case "1": exec("flowers"); break;
                case "2": exec("accessories"); break;
                case "3": exec("file"); break;
                case "4": exec("show"); break;
                case "5": exec("total"); break;
                case "h": help(); break;
                case "0": return;
                default: System.out.println("Wrong option");
            }
        }
    }

}
