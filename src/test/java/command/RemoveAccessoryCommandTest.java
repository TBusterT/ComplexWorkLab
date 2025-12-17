package test.java.command;

import main.java.command.AbstractMenu;
import main.java.command.RemoveAccessoryCommand;
import main.java.model.Accessory;
import main.java.model.Bouquet;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class RemoveAccessoryCommandTest {

    private Bouquet bouquet;
    private RemoveAccessoryCommand command;

    private final PrintStream systemOut = System.out;
    private final java.io.InputStream systemIn = System.in;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        command = new RemoveAccessoryCommand(bouquet);
    }

    @AfterEach
    void tearDown() {
        // restore original streams
        System.setOut(systemOut);
        System.setIn(systemIn);
    }


    private void provideInput(String data) throws Exception {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Field scField = AbstractMenu.class.getDeclaredField("sc");
        scField.setAccessible(true);
        scField.set(null, new Scanner(System.in));
    }


    private String captureOutput(Runnable r) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        try {
            r.run();
        } finally {
            System.out.flush();
        }
        return baos.toString();
    }

    @Test
    void testRemoveValidIndex() throws Exception {
        bouquet.addAccessory(new Accessory("Bow", 1.5, "Red"));
        bouquet.addAccessory(new Accessory("Ribbon", 0.5, "Blue"));

        provideInput("1\n"); // choose first accessory

        String out = captureOutput(() -> command.execute());

        assertEquals(1, bouquet.getAccessories().size(), "One accessory should remain after removal");
        assertEquals("Ribbon", bouquet.getAccessories().get(0).getName(), "Remaining accessory should be Ribbon");
        assertTrue(out.toLowerCase().contains("removed"), "Output should contain 'Removed' message");
    }

    @Test
    void testRemoveLastAccessory() throws Exception {
        bouquet.addAccessory(new Accessory("Tag", 0.2, "Cardboard"));

        provideInput("1\n"); // remove the only accessory

        String out = captureOutput(() -> command.execute());

        assertTrue(bouquet.getAccessories().isEmpty(), "Bouquet should be empty after removing the only accessory");
        assertTrue(out.toLowerCase().contains("removed"), "Output should contain 'Removed' message");
    }

    @Test
    void testMultipleRemovalsSequence() throws Exception {
        bouquet.addAccessory(new Accessory("A", 0.1, "m1"));
        bouquet.addAccessory(new Accessory("B", 0.2, "m2"));
        bouquet.addAccessory(new Accessory("C", 0.3, "m3"));

        // remove second (B)
        provideInput("2\n");
        String out1 = captureOutput(() -> command.execute());
        assertEquals(2, bouquet.getAccessories().size());
        assertFalse(bouquet.getAccessories().stream().anyMatch(a -> "B".equals(a.getName())));
        assertTrue(out1.toLowerCase().contains("removed"));

        // now remove new second (originally C)
        provideInput("2\n");
        String out2 = captureOutput(() -> command.execute());
        assertEquals(1, bouquet.getAccessories().size());
        assertEquals("A", bouquet.getAccessories().get(0).getName());
        assertTrue(out2.toLowerCase().contains("removed"));
    }

    @Test
    void testIndexZeroThrowsIndexOutOfBounds() throws Exception {
        bouquet.addAccessory(new Accessory("Only", 1.0, "m"));

        provideInput("0\n"); // will convert to idx = -1 -> list.get(-1) -> IndexOutOfBoundsException

        Field scField = AbstractMenu.class.getDeclaredField("sc");
        scField.setAccessible(true);
        scField.set(null, new Scanner(System.in));

        assertThrows(IndexOutOfBoundsException.class, () -> command.execute(),
                "Using index 0 (which maps to -1) should throw IndexOutOfBoundsException");
    }

    @Test
    void testNonNumericInputThrowsNumberFormatException() throws Exception {
        bouquet.addAccessory(new Accessory("X", 2.0, "m"));

        provideInput("abc\n"); // non-numeric -> Integer.parseInt -> NumberFormatException

        Field scField = AbstractMenu.class.getDeclaredField("sc");
        scField.setAccessible(true);
        scField.set(null, new Scanner(System.in));

        assertThrows(NumberFormatException.class, () -> command.execute(),
                "Non-numeric input should throw NumberFormatException");
    }
}
