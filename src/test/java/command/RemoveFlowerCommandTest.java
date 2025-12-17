package test.java.command;

import main.java.command.AbstractMenu;
import main.java.command.RemoveFlowerCommand;
import main.java.model.Flower;
import main.java.model.Bouquet;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class RemoveFlowerCommandTest {

    private Bouquet bouquet;
    private RemoveFlowerCommand command;

    private final PrintStream systemOut = System.out;
    private final java.io.InputStream systemIn = System.in;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        command = new RemoveFlowerCommand(bouquet);
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
        bouquet.addFlower(new Flower("Rose", "Red", 10.0, 50.0, 5, 7));
        bouquet.addFlower(new Flower("Tulip", "Yellow", 8.0, 30.0, 4, 6));

        provideInput("1\n"); // choose first flower

        String out = captureOutput(() -> command.execute());

        assertEquals(1, bouquet.getFlowers().size(), "One flower should remain after removal");
        assertEquals("Tulip", bouquet.getFlowers().get(0).getName(), "Remaining flower should be Tulip");
        assertTrue(out.contains("Removed") || out.toLowerCase().contains("removed"),
                "Output should contain 'Removed' message");
        assertTrue(out.contains("Rose") || out.toLowerCase().contains("rose"),
                "Output should contain removed flower's representation");
    }

    @Test
    void testRemoveWrongIndexTooLarge() throws Exception {
        bouquet.addFlower(new Flower("A", "c", 1.0, 5.0, 1, 1));

        provideInput("2\n"); // index 2 -> out of range

        String out = captureOutput(() -> command.execute());

        assertEquals(1, bouquet.getFlowers().size(), "Bouquet should remain unchanged on wrong index");
        assertTrue(out.contains("Wrong index.") || out.toLowerCase().contains("wrong index"),
                "Should print 'Wrong index.' message");
    }

    @Test
    void testRemoveFromEmptyBouquet() throws Exception {
        // bouquet empty
        provideInput("\n"); // no input needed, but set scanner to avoid issues

        String out = captureOutput(() -> command.execute());

        assertTrue(out.contains("No flowers.") || out.toLowerCase().contains("no flowers"),
                "Should print 'No flowers.' when bouquet is empty");
        assertTrue(bouquet.getFlowers().isEmpty(), "Bouquet must still be empty");
    }

    @Test
    void testRemoveLastFlower() throws Exception {
        bouquet.addFlower(new Flower("Solo", "Blue", 5.0, 12.0, 2, 3));

        provideInput("1\n"); // remove the only flower

        String out = captureOutput(() -> command.execute());

        assertTrue(bouquet.getFlowers().isEmpty(), "Bouquet should be empty after removing the only flower");
        assertTrue(out.contains("Removed") || out.toLowerCase().contains("removed"),
                "Output should contain 'Removed' message");
        assertTrue(out.contains("Solo") || out.toLowerCase().contains("solo"),
                "Output should contain removed flower's representation");
    }

    @Test
    void testMultipleRemovalsSequence() throws Exception {
        bouquet.addFlower(new Flower("A", "c1", 1.0, 1.0, 1, 1));
        bouquet.addFlower(new Flower("B", "c2", 2.0, 2.0, 2, 2));
        bouquet.addFlower(new Flower("C", "c3", 3.0, 3.0, 3, 3));

        // remove second (B)
        provideInput("2\n");
        String out1 = captureOutput(() -> command.execute());
        assertEquals(2, bouquet.getFlowers().size());
        assertFalse(bouquet.getFlowers().stream().anyMatch(f -> "B".equals(f.getName())));
        assertTrue(out1.contains("Removed") || out1.toLowerCase().contains("removed"));

        // now remove new second (originally C)
        provideInput("2\n");
        String out2 = captureOutput(() -> command.execute());
        assertEquals(1, bouquet.getFlowers().size());
        assertEquals("A", bouquet.getFlowers().get(0).getName());
        assertTrue(out2.contains("Removed") || out2.toLowerCase().contains("removed"));
    }
}
