package test.java.command;

import main.java.command.AbstractMenu;
import main.java.command.UpdateFlowerCommand;
import main.java.model.Flower;
import main.java.model.Bouquet;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UpdateFlowerCommandTest {

    private Bouquet bouquet;
    private UpdateFlowerCommand command;

    private final java.io.InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        command = new UpdateFlowerCommand(bouquet);
    }

    @AfterEach
    void tearDown() {
        // restore original streams
        System.setIn(systemIn);
        System.setOut(systemOut);
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
    void testUpdatePriceOnly() throws Exception {
        bouquet.addFlower(new Flower("Rose", "Red", 10.0, 50.0, 5, 7));

        // choose first flower, new price 55.5, then empty freshness (keep old)
        provideInput("1\n55.5\n\n");

        String out = captureOutput(() -> command.execute());

        assertEquals(1, bouquet.getFlowers().size());
        Flower f = bouquet.getFlowers().get(0);
        assertEquals(55.5, f.getPrice(), 1e-9, "Price should be updated to 55.5");
        assertEquals(5, f.getFreshness(), "Freshness should remain unchanged");
        assertTrue(out.toLowerCase().contains("updated"), "Should print 'Updated' message");
    }

    @Test
    void testUpdateFreshnessOnly() throws Exception {
        bouquet.addFlower(new Flower("Tulip", "Yellow", 8.0, 30.0, 4, 6));

        // choose first flower, empty price (keep old), new freshness 8
        provideInput("1\n\n8\n");

        captureOutput(() -> command.execute());

        Flower f = bouquet.getFlowers().get(0);
        assertEquals(30.0, f.getPrice(), 1e-9, "Price should remain unchanged");
        assertEquals(8, f.getFreshness(), "Freshness should be updated to 8");
    }

    @Test
    void testUpdateBothPriceAndFreshness() throws Exception {
        bouquet.addFlower(new Flower("Lily", "White", 9.0, 20.0, 6, 8));

        // choose first flower, new price 25, new freshness 7
        provideInput("1\n25\n7\n");

        captureOutput(() -> command.execute());

        Flower f = bouquet.getFlowers().get(0);
        assertEquals(25.0, f.getPrice(), 1e-9);
        assertEquals(7, f.getFreshness());
    }

    @Test
    void testInvalidIndexDoesNothing() throws Exception {
        bouquet.addFlower(new Flower("Orchid", "Purple", 12.0, 100.0, 6, 10));

        // choose invalid index 5 (only 1 flower) -> command returns without changes
        provideInput("5\n");

        String out = captureOutput(() -> command.execute());

        Flower f = bouquet.getFlowers().get(0);
        assertEquals("Orchid", f.getName());
        assertEquals(100.0, f.getPrice(), 1e-9);
        assertEquals(6, f.getFreshness());
        // since method returns silently on invalid index, ensure no "Updated" message
        assertFalse(out.toLowerCase().contains("updated"));
    }

    @Test
    void testEmptyBouquetPrintsNoFlowers() throws Exception {
        // bouquet empty - should print "No flowers."
        provideInput("\n"); // provide at least something so scanner exists
        String out = captureOutput(() -> command.execute());

        assertTrue(out.toLowerCase().contains("no flowers"), "Should print 'No flowers.' when bouquet is empty");
        assertTrue(bouquet.getFlowers().isEmpty());
    }
}
