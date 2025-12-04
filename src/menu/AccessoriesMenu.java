package menu;

import command.Command;

import java.util.Map;
import java.util.Scanner;

public class AccessoriesMenu {

    private final Map<String, Command> commands;

    public AccessoriesMenu(Map<String, Command> commands) {
        this.commands = commands;
    }

    public void show() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Accessories Menu ---");
            System.out.println("1 - Add accessory");
            System.out.println("2 - Remove accessory");
            System.out.println("3 - Show accessories (via bouquet)");
            System.out.println("0 - Back");
            System.out.print("> ");

            switch (sc.nextLine().trim()) {
                case "1": commands.get("addaccessory").execute(new String[]{"addaccessory"}); break;
                case "2": commands.get("removeaccessory").execute(new String[]{"removeaccessory"}); break;
                case "3":
                    // reuse show bouquet command to view accessories
                    commands.get("show").execute(new String[]{"show"});
                    break;
                case "0": return;
                default: System.out.println("Unknown option.");
            }
        }
    }
}
