package test.java.command;

import main.java.command.AddAccessoryCommand;
import main.java.command.AbstractMenu;
import main.java.model.Accessory;
import main.java.model.Bouquet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AddAccessoryCommandTest {

    private Bouquet bouquet;
    private AddAccessoryCommand command;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        command = new AddAccessoryCommand(bouquet);
    }


    private void provideInput(String data) throws Exception {
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Field scField = AbstractMenu.class.getDeclaredField("sc");
        scField.setAccessible(true);
        // пересоздаємо сканер, щоб він читав новий System.in
        scField.set(null, new Scanner(System.in));
    }

    @Test
    void testAddAccessoryBasic() throws Exception {
        provideInput("Bow\n15.5\nRed\n");
        command.execute();

        assertEquals(1, bouquet.getAccessories().size(), "Accessory should be added");
        Accessory a = bouquet.getAccessories().get(0);
        assertEquals("Bow", a.getName());
    }

    @Test
    void testAccessoryPriceParsed() throws Exception {
        provideInput("Ribbon\n3.75\nBlue\n");
        command.execute();

        Accessory a = bouquet.getAccessories().get(0);
        assertEquals(3.75, a.getPrice(), 1e-9);
    }

    @Test
    void testAccessoryMaterialStored() throws Exception {
        provideInput("Wrapper\n1.25\nPaper\n");
        command.execute();

        Accessory a = bouquet.getAccessories().get(0);
        assertEquals("Paper", a.getMaterialOrColor());
    }

    @Test
    void testMultipleAccessories() throws Exception {
        // перша
        provideInput("Tag\n0.5\nCardboard\n");
        command.execute();

        // друга
        provideInput("Sticker\n0.2\nPlastic\n");
        command.execute();

        assertEquals(2, bouquet.getAccessories().size());
        assertEquals("Tag", bouquet.getAccessories().get(0).getName());
        assertEquals("Sticker", bouquet.getAccessories().get(1).getName());
    }

    @Test
    void testAccessoryToStringContainsFields() throws Exception {
        provideInput("Charm\n2.0\nMetal\n");
        command.execute();

        Accessory a = bouquet.getAccessories().get(0);
        String s = a.toString();
        assertTrue(s.contains("Charm"));
        assertTrue(s.contains("2.0") || s.contains("2")); // різні toString можуть форматувати по-різному
        assertTrue(s.contains("Metal"));
    }
}
