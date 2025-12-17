package test.java.command;

import main.java.command.AbstractMenu;
import main.java.command.SaveBouquetCommand;
import main.java.model.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SaveBouquetCommandTest {

    private Bouquet bouquet;
    private SaveBouquetCommand command;

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        command = new SaveBouquetCommand(bouquet);
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    // helper: set System.in and recreate AbstractMenu.sc
    private void provideInput(String data) throws Exception {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Field scField = AbstractMenu.class.getDeclaredField("sc");
        scField.setAccessible(true);
        scField.set(null, new Scanner(System.in));
    }

    // helper: capture System.out while running runnable
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
    void testSaveEmptyBouquetCreatesFile() throws Exception {
        Path tmpFile = Files.createTempFile("bouquet-empty", ".txt");
        try {
            provideInput(tmpFile.toString() + "\n");

            String out = captureOutput(() -> command.execute());

            // file exists and is empty (no lines written)
            List<String> lines = Files.readAllLines(tmpFile, StandardCharsets.UTF_8);
            assertTrue(lines.isEmpty(), "Saved file should be empty for empty bouquet");
            assertTrue(out.toLowerCase().contains("saved"), "Should print 'Saved.'");
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    @Test
    void testSaveWithRegularFlowerWritesFlowerLine() throws Exception {
        Path tmpFile = Files.createTempFile("bouquet-flower", ".txt");
        try {
            bouquet.addFlower(new Flower("Rose", "Red", 10.0, 50.0, 5, 7));

            provideInput(tmpFile.toString() + "\n");
            captureOutput(() -> command.execute());

            List<String> lines = Files.readAllLines(tmpFile, StandardCharsets.UTF_8);
            assertFalse(lines.isEmpty(), "File should contain at least one line");
            String first = lines.get(0);
            assertTrue(first.startsWith("F|"), "Flower line should start with 'F|'");
            assertTrue(first.contains("Rose"), "Saved line should contain flower name 'Rose'");
            assertTrue(first.contains("Flower") || first.contains("Flower"), "Line should include kind (Flower)");
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    @Test
    void testSaveWithGardenFlowerWritesExtraFields() throws Exception {
        Path tmpFile = Files.createTempFile("bouquet-garden", ".txt");
        try {
            GardenFlower gf = new GardenFlower("Tulip", "Yellow", 8.0, 40.0, 4, 6, "Spring", "Strong");
            bouquet.addFlower(gf);

            provideInput(tmpFile.toString() + "\n");
            captureOutput(() -> command.execute());

            List<String> lines = Files.readAllLines(tmpFile, StandardCharsets.UTF_8);
            assertFalse(lines.isEmpty());
            String line = lines.get(0);
            assertTrue(line.contains("GardenFlower"), "Should include 'GardenFlower' kind");
            assertTrue(line.contains("Spring"), "Should include variety 'Spring'");
            assertTrue(line.contains("Strong"), "Should include fragrance 'Strong'");
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    @Test
    void testSaveWithAccessoryWritesAccessoryLine() throws Exception {
        Path tmpFile = Files.createTempFile("bouquet-accessory", ".txt");
        try {
            bouquet.addAccessory(new Accessory("Bow", 1.5, "Red"));
            bouquet.addFlower(new Flower("Lily", "White", 9.0, 20.0, 6, 8)); // also add a flower to mix

            provideInput(tmpFile.toString() + "\n");
            captureOutput(() -> command.execute());

            List<String> lines = Files.readAllLines(tmpFile, StandardCharsets.UTF_8);
            // find accessory line (starts with A|)
            boolean foundA = lines.stream().anyMatch(l -> l.startsWith("A|") && l.contains("Bow"));
            assertTrue(foundA, "Accessory line starting with 'A|' and containing 'Bow' should be present");
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    @Test
    void testSaveHandlesIOExceptionAndPrintsError() throws Exception {
        // create a directory and pass its path as filename -> FileWriter should fail with IOException
        Path tmpDir = Files.createTempDirectory("bouquet-dir");
        try {
            provideInput(tmpDir.toString() + "\n");

            String out = captureOutput(() -> command.execute());

            assertTrue(out.toLowerCase().contains("error"), "Should print 'Error:' when save fails");
        } finally {
            // cleanup directory
            Files.deleteIfExists(tmpDir);
        }
    }
}
