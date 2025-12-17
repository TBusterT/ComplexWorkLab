package main.java.command;

import main.java.model.Bouquet;

public class LoadBouquetCommand implements Command {

    private final Bouquet bouquet;

    public LoadBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        System.out.print("Filename: ");
        String file = AbstractMenu.sc.nextLine();
        try {
            bouquet.loadFromFile(file);
            System.out.println("Loaded.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override public String getName() { return "load"; }
    @Override public String getDesc() { return "Load bouquet from file"; }
}
