package main.command;

import main.model.Bouquet;
import main.model.Flower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class SearchFlowersCommand implements Command {

    private static final Logger logger =
            LogManager.getLogger(SearchFlowersCommand.class);

    private final Bouquet bouquet;

    public SearchFlowersCommand(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    @Override
    public void execute() {
        logger.info("SearchFlowersCommand started");

        System.out.println("1 - Name contains");
        System.out.println("2 - Color");
        System.out.print("> ");

        String opt = AbstractMenu.sc.nextLine();
        List<Flower> res;

        if ("1".equals(opt)) {
            System.out.print("Text: ");
            String text = AbstractMenu.sc.nextLine().toLowerCase();
            logger.info("Searching flowers by name containing: '{}'", text);

            res = bouquet.getFlowers().stream()
                    .filter(f -> f.getName().toLowerCase().contains(text))
                    .collect(Collectors.toList());
        } else if ("2".equals(opt)) {
            System.out.print("Color: ");
            String color = AbstractMenu.sc.nextLine();
            logger.info("Searching flowers by color: '{}'", color);

            res = bouquet.getFlowers().stream()
                    .filter(f -> f.getColor().equalsIgnoreCase(color))
                    .collect(Collectors.toList());
        } else {
            logger.warn("Unknown search option: '{}'", opt);
            System.out.println("Unknown option");
            return;
        }

        logger.info("Found {} flower(s) matching criteria", res.size());

        if (res.isEmpty()) {
            System.out.println("No flowers found.");
        } else {
            res.forEach(System.out::println);
        }
    }

    @Override
    public String getName() {
        return "searchflowers";
    }

    @Override
    public String getDesc() {
        return "Search flowers";
    }
}
