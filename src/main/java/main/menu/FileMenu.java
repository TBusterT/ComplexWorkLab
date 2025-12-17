package main.menu;

import main.model.Bouquet;
import main.command.*;
import java.util.HashMap;
import java.util.Map;

public class FileMenu extends AbstractMenu {

    public FileMenu(Bouquet bouquet) {
        super("file", "File submenu", createCommands(bouquet));
    }

    private static Map<String, Command> createCommands(Bouquet bouquet) {
        Map<String, Command> map = new HashMap<>();

        map.put("save", new SaveBouquetCommand(bouquet));
        map.put("load", new LoadBouquetCommand(bouquet));

        return map;
    }

    @Override
    protected void menuCycle() {
        while (true) {
            System.out.println("\n--- File Menu ---");
            System.out.println("1 - Save bouquet");
            System.out.println("2 - Load bouquet");
            System.out.println("h - Help");
            System.out.println("0 - Back");
            System.out.print("> ");

            switch (sc.nextLine()) {
                case "1": exec("save"); break;
                case "2": exec("load"); break;
                case "h": help(); break;
                case "0": return;
                default: System.out.println("Wrong option");
            }
        }
    }

}
