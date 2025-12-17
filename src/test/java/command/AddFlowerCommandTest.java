package test.java.command;

import main.java.command.AddFlowerCommand;
import main.java.command.AbstractMenu;
import main.java.model.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AddFlowerCommandTest {

    private Bouquet bouquet;
    private AddFlowerCommand command;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        command = new AddFlowerCommand(bouquet);
    }

    private void provideInput(String data) throws Exception {
        System.setIn(new ByteArrayInputStream(data.getBytes()));


        Field scField = AbstractMenu.class.getDeclaredField("sc");
        scField.setAccessible(true);
        scField.set(null, new Scanner(System.in));
    }

    @Test
    void testAddRegularFlower() throws Exception {
        provideInput(
                "1\nRose\nRed\n10\n50\n5\n7\n"
        );

        command.execute();

        assertEquals(1, bouquet.getFlowers().size());
        assertEquals("Rose", bouquet.getFlowers().get(0).getName());
    }

    @Test
    void testAddGardenFlower() throws Exception {
        provideInput(
                "2\nTulip\nYellow\n8\n40\n4\n6\nSpring\nStrong\n"
        );

        command.execute();

        assertTrue(bouquet.getFlowers().get(0) instanceof GardenFlower);
    }

    @Test
    void testAddExoticFlower() throws Exception {
        provideInput(
                "3\nOrchid\nWhite\n12\n100\n6\n10\nBrazil\nHigh\n"
        );

        command.execute();

        assertTrue(bouquet.getFlowers().get(0) instanceof ExoticFlower);
    }

    @Test
    void testAddDecorativePlant() throws Exception {
        provideInput(
                "4\nBonsai\nGreen\n15\n200\n9\n30\nIndoor\nSmooth\n"
        );

        command.execute();

        assertTrue(bouquet.getFlowers().get(0) instanceof DecorativePlant);
    }

    @Test
    void testFlowerPriceIsCorrect() throws Exception {
        provideInput(
                "1\nLily\nWhite\n9\n70\n5\n8\n"
        );

        command.execute();

        assertEquals(70.0, bouquet.getFlowers().get(0).getPrice());
    }
}
