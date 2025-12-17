package main.java.command;

import java.util.Map;
import java.util.Scanner;

public abstract class AbstractMenu implements Command {

    public static Scanner sc = new Scanner(System.in);

    protected final String name;
    protected final String desc;
    protected final Map<String, Command> commands;

    protected AbstractMenu(String name, String desc, Map<String, Command> commands) {
        this.name = name;
        this.desc = desc;
        this.commands = commands;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void execute() {
        menuCycle();
    }

    protected void exec(String key) {
        Command cmd = commands.get(key);
        if (cmd == null) {
            System.out.println("Command '" + key + "' not found.");
            return;
        }
        cmd.execute();
    }


    protected void help() {
        System.out.println("\nAvailable commands:");
        for (Command c : commands.values()) {
            System.out.printf("  %-15s : %s%n",
                    c.getName(), c.getDesc());
        }
        System.out.println("  help            : Show this help");
        System.out.println("  0               : Back / Exit");
    }

    protected abstract void menuCycle();
}

