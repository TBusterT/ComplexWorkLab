package menu;

import command.Command;

import java.util.Map;
import java.util.Scanner;

public class FlowersMenu {

    private final Map<String, Command> commands;

    public FlowersMenu(Map<String, Command> commands) {
        this.commands = commands;
    }

    public void show() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Flowers Menu ---");
            System.out.println("1 - Add flower");
            System.out.println("2 - Remove flower");
            System.out.println("3 - Show bouquet");
            System.out.println("4 - Update flower");
            System.out.println("5 - Sort flowers");
            System.out.println("6 - Search / Filter flowers");
            System.out.println("0 - Back");
            System.out.print("> ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    execIfExists("addflower", new String[]{"addflower"});
                    break;
                case "2":
                    execIfExists("removeflower", new String[]{"removeflower"});
                    break;
                case "3":
                    execIfExists("show", new String[]{"show"});
                    break;
                case "4":
                    execIfExists("updateflower", new String[]{"updateflower"});
                    break;
                case "5":
                    execIfExists("sortflowers", new String[]{"sortflowers"});
                    break;
                case "6":
                    execIfExists("searchflowers", new String[]{"searchflowers"});
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    private void execIfExists(String key, String[] args) {
        Command cmd = commands.get(key);
        if (cmd == null) {
            System.out.println("Command '" + key + "' is not available (not registered).");
            return;
        }
        try {
            cmd.execute(args);
        } catch (Exception e) {
            System.out.println("Error while executing command '" + key + "': " + e.getMessage());
        }
    }
}
