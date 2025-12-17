package main;

import main.model.Bouquet;
import main.menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        Bouquet bouquet = new Bouquet();

        MainMenu mainMenu = new MainMenu(bouquet);
        mainMenu.execute();
    }
}
