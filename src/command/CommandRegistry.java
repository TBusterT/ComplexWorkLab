package command;

import Model.Bouquet;
import java.util.HashMap;
import java.util.Map;

public final class CommandRegistry {

    private CommandRegistry() {}

    public static Map<String, Command> create(Bouquet bouquet) {
        Map<String, Command> commands = new HashMap<>();

        // основні
        commands.put("addflower", new AddFlowerCommand(bouquet));
        commands.put("removeflower", new RemoveFlowerCommand(bouquet));
        commands.put("show", new ShowBouquetCommand(bouquet));
        commands.put("save", new SaveBouquetCommand(bouquet));
        commands.put("load", new LoadBouquetCommand(bouquet));

        // додаткові
        commands.put("addaccessory", new AddAccessoryCommand(bouquet));
        commands.put("removeaccessory", new RemoveAccessoryCommand(bouquet));
        commands.put("total", new ShowTotalPriceCommand(bouquet));
        commands.put("sortflowers", new SortFlowersCommand(bouquet));
        commands.put("updateflower", new UpdateFlowerCommand(bouquet));
        commands.put("searchflowers", new SearchFlowersCommand(bouquet));


        commands.put("filenothing", new FileCommand(bouquet));

        return commands;
    }
}
