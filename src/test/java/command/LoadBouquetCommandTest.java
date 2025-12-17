package test.java.command;

import main.java.command.AbstractMenu;
import main.java.command.LoadBouquetCommand;
import main.java.model.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LoadBouquetCommandTest {

    private Bouquet bouquet;
    private LoadBouquetCommand command;

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        command = new LoadBouquetCommand(bouquet);
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
    void testLoadEmptyFileProducesEmptyBouquet() throws Exception {
        Path tmpFile = Files.createTempFile("load-empty", ".txt");
        try {
            // empty file
            provideInput(tmpFile.toString() + "\n");

            String out = captureOutput(() -> command.execute());

            assertTrue(out.toLowerCase().contains("loaded"), "Should print 'Loaded.' on success");
            assertTrue(bouquet.getFlowers().isEmpty(), "No flowers after loading empty file");
            assertTrue(bouquet.getAccessories().isEmpty(), "No accessories after loading empty file");
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    @Test
    void testLoadRegularFlowerLine() throws Exception {
        Path tmpFile = Files.createTempFile("load-flower", ".txt");
        try {
            // Format: F|Flower|name|color|stem|price|fresh|life
            String line = "F|Flower|Rose|Red|10.0|50.0|5|7";
            Files.write(tmpFile, List.of(line), StandardCharsets.UTF_8);

            provideInput(tmpFile.toString() + "\n");
            String out = captureOutput(() -> command.execute());

            assertTrue(out.toLowerCase().contains("loaded"));
            assertEquals(1, bouquet.getFlowers().size());
            Flower f = bouquet.getFlowers().get(0);
            assertEquals("Rose", f.getName());
            assertEquals("Red", f.getColor());
            assertEquals(10.0, f.getStemLength(), 1e-9);
            assertEquals(50.0, f.getPrice(), 1e-9);
            assertEquals(5, f.getFreshness());
            assertEquals(7, f.getLifespan());
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    @Test
    void testLoadGardenExoticAndAccessory() throws Exception {
        Path tmpFile = Files.createTempFile("load-mixed", ".txt");
        try {
            // GardenFlower: F|GardenFlower|Tulip|Yellow|8.0|40.0|4|6|Spring|Strong
            String g = "F|GardenFlower|Tulip|Yellow|8.0|40.0|4|6|Spring|Strong";
            // ExoticFlower: F|ExoticFlower|Orchid|White|12.0|100.0|6|10|Brazil|High
            String e = "F|ExoticFlower|Orchid|White|12.0|100.0|6|10|Brazil|High";
            // Accessory: A|Bow|1.5|Red
            String a = "A|Bow|1.5|Red";
            Files.write(tmpFile, List.of(g, e, a), StandardCharsets.UTF_8);

            provideInput(tmpFile.toString() + "\n");
            String out = captureOutput(() -> command.execute());

            assertTrue(out.toLowerCase().contains("loaded"));
            // check flowers
            assertEquals(2, bouquet.getFlowers().size());
            assertTrue(bouquet.getFlowers().get(0) instanceof GardenFlower);
            assertTrue(bouquet.getFlowers().get(1) instanceof ExoticFlower);

            GardenFlower gf = (GardenFlower) bouquet.getFlowers().stream()
                    .filter(f -> f instanceof GardenFlower).findFirst().orElse(null);
            ExoticFlower ef = (ExoticFlower) bouquet.getFlowers().stream()
                    .filter(f -> f instanceof ExoticFlower).findFirst().orElse(null);

            assertNotNull(gf);
            assertEquals("Spring", gf.getVariety());
            assertEquals("Strong", gf.getFragranceLevel());

            assertNotNull(ef);
            assertEquals("Brazil", ef.getOriginCountry());
            assertEquals("High", ef.getCareComplexity());

            // accessories
            assertEquals(1, bouquet.getAccessories().size());
            assertEquals("Bow", bouquet.getAccessories().get(0).getName());
            assertEquals(1.5, bouquet.getAccessories().get(0).getPrice(), 1e-9);
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }

    @Test
    void testLoadNonexistentFilePrintsError() throws Exception {
        // choose a path that does not exist
        Path notExist = Paths.get(System.getProperty("java.io.tmpdir"), "no-such-file-" + System.nanoTime() + ".txt");
        provideInput(notExist.toString() + "\n");

        String out = captureOutput(() -> command.execute());

        assertTrue(out.toLowerCase().contains("error"), "Should print 'Error:' when file does not exist");
        assertTrue(bouquet.getFlowers().isEmpty());
        assertTrue(bouquet.getAccessories().isEmpty());
    }

    @Test
    void testLoadMalformedLineProducesErrorAndClearsCollections() throws Exception {
        Path tmpFile = Files.createTempFile("load-malformed", ".txt");
        try {
            // malformed: missing numeric fields -> will cause NumberFormatException during parsing
            String bad = "F|Flower|BadFlower|Green|not-a-number|price|fresh|life";
            // also add a valid line to see behavior (but loadFromFile clears collections before processing)
            String good = "A|Tag|0.5|Paper";
            Files.write(tmpFile, List.of(bad, good), StandardCharsets.UTF_8);

            provideInput(tmpFile.toString() + "\n");
            String out = captureOutput(() -> command.execute());

            // On malformed line, LoadBouquetCommand catches exception and prints Error: ...
            assertTrue(out.toLowerCase().contains("error"), "Malformed content should cause 'Error:'");
            // loadFromFile clears collections at start, so bouquet should be empty after error
            assertTrue(bouquet.getFlowers().isEmpty(), "Flowers should be empty after failed load");
            assertTrue(bouquet.getAccessories().isEmpty(), "Accessories should be empty after failed load");
        } finally {
            Files.deleteIfExists(tmpFile);
        }
    }
}
