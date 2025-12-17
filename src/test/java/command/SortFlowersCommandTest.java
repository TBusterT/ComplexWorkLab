package test.java.command;

import main.java.command.AbstractMenu;
import main.java.command.SortFlowersCommand;
import main.java.model.Flower;
import main.java.model.Bouquet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortFlowersCommandTest {

    private Bouquet bouquet;
    private SortFlowersCommand command;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
        command = new SortFlowersCommand(bouquet);
    }

    // Підмінює System.in і пересоздає static Scanner в AbstractMenu
    private void provideInput(String data) throws Exception {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Field scField = AbstractMenu.class.getDeclaredField("sc");
        scField.setAccessible(true);
        scField.set(null, new Scanner(System.in));
    }

    @Test
    void testSortByFreshnessDescending() throws Exception {
        // freshness: 3, 7, 5
        bouquet.addFlower(new Flower("A", "c1", 1.0, 10.0, 3, 5));
        bouquet.addFlower(new Flower("B", "c2", 2.0, 20.0, 7, 5));
        bouquet.addFlower(new Flower("C", "c3", 3.0, 30.0, 5, 5));

        provideInput("1\n"); // choose freshness desc
        command.execute();

        List<Flower> f = bouquet.getFlowers();
        assertEquals(3, f.size());
        assertEquals(7, f.get(0).getFreshness());
        assertEquals(5, f.get(1).getFreshness());
        assertEquals(3, f.get(2).getFreshness());
    }

    @Test
    void testSortByPriceAscending() throws Exception {
        // prices: 30, 10, 20
        bouquet.addFlower(new Flower("A", "c1", 1.0, 30.0, 1, 5));
        bouquet.addFlower(new Flower("B", "c2", 2.0, 10.0, 1, 5));
        bouquet.addFlower(new Flower("C", "c3", 3.0, 20.0, 1, 5));

        provideInput("2\n"); // choose price asc
        command.execute();

        List<Flower> f = bouquet.getFlowers();
        assertEquals(3, f.size());
        assertEquals(10.0, f.get(0).getPrice());
        assertEquals(20.0, f.get(1).getPrice());
        assertEquals(30.0, f.get(2).getPrice());
    }

    @Test
    void testInvalidOptionKeepsOriginalOrder() throws Exception {
        bouquet.addFlower(new Flower("First", "c1", 1.0, 5.0, 2, 5));
        bouquet.addFlower(new Flower("Second", "c2", 2.0, 6.0, 3, 5));
        // remember original order
        String firstName = bouquet.getFlowers().get(0).getName();
        String secondName = bouquet.getFlowers().get(1).getName();

        provideInput("x\n"); // invalid option
        command.execute();

        List<Flower> f = bouquet.getFlowers();
        assertEquals(2, f.size());
        assertEquals(firstName, f.get(0).getName());
        assertEquals(secondName, f.get(1).getName());
    }

    @Test
    void testEmptyBouquetDoesNotThrow() throws Exception {
        // no flowers
        provideInput("1\n");
        // should not throw
        command.execute();
        assertTrue(bouquet.getFlowers().isEmpty());
    }

    @Test
    void testStableSortWhenEqualPrices() throws Exception {

        bouquet.addFlower(new Flower("X", "c1", 1.0, 9.99, 1, 5));
        bouquet.addFlower(new Flower("Y", "c2", 2.0, 9.99, 2, 5));
        bouquet.addFlower(new Flower("Z", "c3", 3.0, 5.0, 3, 5)); // different price

        provideInput("2\n"); // sort by price asc
        command.execute();

        List<Flower> f = bouquet.getFlowers();

        assertEquals(5.0, f.get(0).getPrice());
        assertEquals("X", f.get(1).getName());
        assertEquals("Y", f.get(2).getName());
    }
}
