import model.Bouquet;
import menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        Bouquet bouquet = new Bouquet();

        MainMenu mainMenu = new MainMenu(bouquet);
        mainMenu.execute();
    }
}
