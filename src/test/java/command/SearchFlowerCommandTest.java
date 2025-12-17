package test.java.command;

import main.java.command.AbstractMenu;
import main.java.command.SearchFlowersCommand;
import main.java.model.Flower;
import main.java.model.Bouquet;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SearchFlowersCommandTest {

    private Bouquet bouquet;
    private SearchFlowersCommand command;

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        command = new SearchFlowersCommand(bouquet);
    }

    @AfterEach
    void tearDown() {

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
    void testSearchByNameContains_found() throws Exception {
        bouquet.addFlower(new Flower("Rose", "Red", 10.0, 50.0, 5, 7));
        bouquet.addFlower(new Flower("Sunflower", "Yellow", 15.0, 30.0, 4, 6));

        provideInput("1\nros\n"); // choose name contains, text "ros"

        String out = captureOutput(() -> command.execute());

        assertTrue(out.contains("Choose flower type") || out.contains("1 - Name contains"),
                "Should print menu");
        assertTrue(out.toLowerCase().contains("rose"),
                "Output should contain matching flower 'Rose'");
        assertFalse(out.toLowerCase().contains("sunflower") && out.toLowerCase().contains("rose") == false,
                "Should not accidentally exclude Rose");
    }

    @Test
    void testSearchByNameCaseInsensitive() throws Exception {
        bouquet.addFlower(new Flower("RoSeTiA", "Pink", 5.0, 12.0, 3, 4));

        provideInput("1\nroset\n"); // lower-case search

        String out = captureOutput(() -> command.execute());

        assertTrue(out.toLowerCase().contains("rosetia"),
                "Search should be case-insensitive and match 'RoSeTiA'");
    }

    @Test
    void testSearchByColor_found_caseInsensitive() throws Exception {
        bouquet.addFlower(new Flower("Lily", "White", 9.0, 20.0, 6, 8));
        bouquet.addFlower(new Flower("Carnation", "red", 7.0, 8.0, 4, 5));

        provideInput("2\nRED\n"); // choose color, case-insensitive

        String out = captureOutput(() -> command.execute());

        assertTrue(out.toLowerCase().contains("carnation"),
                "Should find flower with color 'red' ignoring case");
        assertFalse(out.toLowerCase().contains("lily"),
                "Should not print 'Lily' when searching for color RED");
    }

    @Test
    void testNoMatchesProducesNoFlowersInOutput() throws Exception {
        bouquet.addFlower(new Flower("A", "Blue", 1.0, 1.0, 1, 1));

        provideInput("1\nnotfound\n");

        String out = captureOutput(() -> command.execute());

        // menu text exists but should not contain any flower names from bouquet
        assertTrue(out.contains("1 - Name contains") || out.contains("Color:") || out.contains(">"),
                "Menu prompts should be present");
        assertFalse(out.toLowerCase().contains("a,") || out.toLowerCase().contains("flower{") || out.toLowerCase().contains("blue") ,
                "There should be no printed flower details matching the bouquet");
    }

    @Test
    void testMultipleMatchesPrinted() throws Exception {
        bouquet.addFlower(new Flower("Red Rose", "Red", 10.0, 5.0, 5, 4));
        bouquet.addFlower(new Flower("Red Tulip", "Red", 8.0, 4.0, 4, 3));
        bouquet.addFlower(new Flower("White Lily", "White", 9.0, 6.0, 6, 5));

        provideInput("2\nred\n"); // search by color -> should print two red flowers

        String out = captureOutput(() -> command.execute());

        int occurrences = out.toLowerCase().split("red").length - 1; // crude count for 'red' occurrences
        assertTrue(occurrences >= 2, "Output should contain at least two 'red' occurrences for the two matches");
        assertTrue(out.toLowerCase().contains("red rose"));
        assertTrue(out.toLowerCase().contains("red tulip"));
    }
}
