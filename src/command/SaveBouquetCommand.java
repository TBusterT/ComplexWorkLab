package command;

import Model.Bouquet;

import java.util.Scanner;

public class SaveBouquetCommand implements Command {

    private final Bouquet bouquet;

    public SaveBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Save bouquet to file: save <filename> or save";
    }

    @Override
    public void execute(String[] args) {
        String filename;
        if (args.length >= 2) {
            filename = args[1];
        } else {
            System.out.print("Enter file name to save: ");
            filename = new Scanner(System.in).nextLine().trim();

            if (filename.isEmpty()) {
                System.out.println("File name cannot be empty. Operation cancelled.");
                return;
            }
        }

        try {
            bouquet.saveToFile(filename);
            System.out.println("Bouquet saved to file: " + filename);
        } catch (Exception e) {
            System.out.println("Failed to save bouquet: " + e.getMessage());
        }
    }
}
