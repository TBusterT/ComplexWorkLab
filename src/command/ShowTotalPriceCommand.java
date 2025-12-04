package command;

import Model.Bouquet;

public class ShowTotalPriceCommand implements Command {

    private final Bouquet bouquet;

    public ShowTotalPriceCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public String getDescription() {
        return "Show total price of bouquet (flowers + accessories)";
    }

    @Override
    public void execute(String[] args) {
        System.out.printf("Total bouquet price: %.2f%n", bouquet.getTotalPrice());
    }
}
