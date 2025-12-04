import Model.Bouquet;
import command.CommandRegistry;
import menu.*;

import java.util.Map;
import command.Command;

public class Main {
    public static void main(String[] args) {
        Bouquet bouquet = new Bouquet();

        Map<String, Command> commandMap = CommandRegistry.create(bouquet);

        FlowersMenu flowersMenu = new FlowersMenu(commandMap);
        AccessoriesMenu accessoriesMenu = new AccessoriesMenu(commandMap);
        FileMenu fileMenu = new FileMenu(commandMap);

        MainMenu mainMenu = new MainMenu(commandMap, flowersMenu, accessoriesMenu, fileMenu);
        mainMenu.run();
    }
}


