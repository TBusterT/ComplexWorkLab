package menu;

import command.Command;

import java.util.Map;
import java.util.Scanner;

public class MainMenu {

    private final FlowersMenu flowersMenu;
    private final AccessoriesMenu accessoriesMenu;
    private final FileMenu fileMenu;
    private final Map<String, Command> commands; // доступ для help()

    public MainMenu(Map<String, Command> commands,
                    FlowersMenu flowersMenu,
                    AccessoriesMenu accessoriesMenu,
                    FileMenu fileMenu) {
        this.commands = commands;
        this.flowersMenu = flowersMenu;
        this.accessoriesMenu = accessoriesMenu;
        this.fileMenu = fileMenu;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1 - Flowers");
            System.out.println("2 - Accessories");
            System.out.println("3 - File");
            System.out.println("4 - Help");
            System.out.println("5 - Exit");
            System.out.print("> ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": flowersMenu.show(); break;
                case "2": accessoriesMenu.show(); break;
                case "3": fileMenu.show(); break;
                case "4": help(); break;
                case "5":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }


    public void help() {
        System.out.println("\n--- HELP: Available commands ---");
        if (commands == null || commands.isEmpty()) {
            System.out.println("No commands registered.");
            return;
        }

        for (Map.Entry<String, Command> e : commands.entrySet()) {
            String key = e.getKey();
            String desc;
            try {
                desc = e.getValue() != null ? e.getValue().getDescription() : "(no description)";
            } catch (Exception ex) {
                desc = "(error reading description)";
            }
            System.out.printf("%-12s : %s%n", key, desc);
        }

        System.out.println("\nNotes:");
        System.out.println(" - Use submenus to execute interactive commands (Flowers / File).");
        System.out.println(" - You can also call some commands directly from submenus if implemented.");
    }
}
