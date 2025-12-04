package menu;

import command.Command;
import java.util.Map;
import java.util.Scanner;

public class FileMenu {

    private final Map<String, Command> commands;

    public FileMenu(Map<String, Command> commands) {
        this.commands = commands;
    }

    public void show() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- File Menu ---");
            System.out.println("1 - Save bouquet");
            System.out.println("2 - Load bouquet");
            System.out.println("3 - Nothing");
            System.out.println("0 - Back");
            System.out.print("> ");

            switch (sc.nextLine().trim()) {
                case "1": commands.get("save").execute(new String[]{"save"}); break;
                case "2": commands.get("load").execute(new String[]{"load"}); break;
                case "3": commands.get("filenothing").execute(new String[]{"filenothing"}); break;
                case "0": return;
                default: System.out.println("Unknown option.");
            }
        }
    }
}
