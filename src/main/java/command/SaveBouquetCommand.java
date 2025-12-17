package main.java.command;

import main.java.model.Bouquet;

public class SaveBouquetCommand implements Command {

    private final Bouquet bouquet;

    public SaveBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        System.out.print("Filename: ");
        String file = AbstractMenu.sc.nextLine();
        try {
            bouquet.saveToFile(file);
            System.out.println("Saved.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override public String getName() { return "save"; }
    @Override public String getDesc() { return "Save bouquet to file"; }
}
