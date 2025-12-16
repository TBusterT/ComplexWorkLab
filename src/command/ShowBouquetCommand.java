package command;

import model.Bouquet;

public class ShowBouquetCommand implements Command {

    private final Bouquet bouquet;

    public ShowBouquetCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        System.out.println(bouquet);
    }

    @Override public String getName() { return "show"; }
    @Override public String getDesc() { return "Show bouquet"; }
}
