package command;

import Model.Bouquet;

public class ShowBouquetCommand implements Command {

    private final Bouquet bouquet;

    public ShowBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Display all flowers and accessories in the bouquet";
    }

    @Override
    public void execute(String[] args) {
        System.out.println("=== Current Bouquet(s) ===");
        System.out.println(bouquet);
    }
}
