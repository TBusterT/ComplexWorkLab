package command;

import Model.Bouquet;

import java.util.Scanner;

public class LoadBouquetCommand implements Command {

    private final Bouquet bouquet;

    public LoadBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Load bouquet from file: load <filename> or load ";
    }

    @Override
    public void execute(String[] args) {
        String filename;
        if (args.length >= 2) {
            filename = args[1];
        } else {
            System.out.print("Enter file name to load: ");
            filename = new Scanner(System.in).nextLine().trim();
        }

        try {
            bouquet.loadFromFile(filename);
            System.out.println("Bouquet loaded from file: " + filename);
        } catch (Exception e) {
            System.out.println("Failed to load bouquet: " + e.getMessage());
        }
    }
}
