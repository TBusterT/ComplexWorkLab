package main.java;

import main.java.model.Bouquet;
import main.java.menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        Bouquet bouquet = new Bouquet();

        MainMenu mainMenu = new MainMenu(bouquet);
        mainMenu.execute();
    }
}
