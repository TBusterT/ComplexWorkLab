package main.command;

import main.model.Bouquet;

public class ShowTotalPriceCommand implements Command {

    private final Bouquet bouquet;

    public ShowTotalPriceCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        System.out.println("Total: " + bouquet.getTotalPrice());
    }

    @Override public String getName() { return "total"; }
    @Override public String getDesc() { return "Show total price"; }
}
