package test.java.command;

import main.java.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BouquetTest {

    private Bouquet bouquet;

    @BeforeEach
    void setUp() {
        bouquet = new Bouquet();
    }

    @Test
    void testAddAndRemoveFlower() {
        Flower f = new Flower("Rose", "Red", 10.0, 50.0, 5, 7);
        bouquet.addFlower(f);
        assertEquals(1, bouquet.getFlowers().size(), "Flower should be added");

        boolean removed = bouquet.removeFlower(f);
        assertTrue(removed, "removeFlower should return true for existing flower");
        assertTrue(bouquet.getFlowers().isEmpty(), "Bouquet should be empty after removal");

        // removing non-existing should return false
        Flower other = new Flower("Tulip", "Yellow", 8.0, 30.0, 4, 6);
        assertFalse(bouquet.removeFlower(other), "removeFlower should return false for non-existing flower");
    }

    @Test
    void testAddAndRemoveAccessory() {
        Accessory a = new Accessory("Bow", 1.5, "Red");
        bouquet.addAccessory(a);
        assertEquals(1, bouquet.getAccessories().size(), "Accessory should be added");

        boolean removed = bouquet.removeAccessory(a);
        assertTrue(removed, "removeAccessory should return true for existing accessory");
        assertTrue(bouquet.getAccessories().isEmpty(), "Accessories list should be empty after removal");

        // removing non-existing accessory
        Accessory x = new Accessory("Tag", 0.2, "Paper");
        assertFalse(bouquet.removeAccessory(x), "removeAccessory should return false for non-existing accessory");
    }

    @Test
    void testGetTotalPriceWithFlowersAndAccessories() {
        bouquet.addFlower(new Flower("A", "c1", 1.0, 10.0, 1, 1));
        bouquet.addFlower(new Flower("B", "c2", 2.0, 20.5, 2, 2));
        bouquet.addAccessory(new Accessory("X", 2.25, "m"));
        bouquet.addAccessory(new Accessory("Y", 0.75, "m2"));

        double expected = 10.0 + 20.5 + 2.25 + 0.75; // 33.5
        assertEquals(expected, bouquet.getTotalPrice(), 1e-9, "Total price should be sum of all items");
    }

    @Test
    void testSortByFreshnessDescending() {
        bouquet.addFlower(new Flower("Low", "c", 1.0, 5.0, 1, 1));
        bouquet.addFlower(new Flower("High", "c", 2.0, 5.0, 9, 1));
        bouquet.addFlower(new Flower("Mid", "c", 3.0, 5.0, 5, 1));

        bouquet.sortByFreshnessDescending();

        List<Flower> f = bouquet.getFlowers();
        assertEquals("High", f.get(0).getName(), "First should be highest freshness");
        assertEquals("Mid", f.get(1).getName(), "Second should be mid freshness");
        assertEquals("Low", f.get(2).getName(), "Third should be lowest freshness");
    }

    @Test
    void testFindByStemLengthRangeInclusive() {
        bouquet.addFlower(new Flower("Short", "c", 2.0, 1.0, 1, 1));
        bouquet.addFlower(new Flower("ExactlyMin", "c", 3.0, 1.0, 1, 1));
        bouquet.addFlower(new Flower("Middle", "c", 4.5, 1.0, 1, 1));
        bouquet.addFlower(new Flower("ExactlyMax", "c", 6.0, 1.0, 1, 1));
        bouquet.addFlower(new Flower("Long", "c", 7.0, 1.0, 1, 1));

        double min = 3.0;
        double max = 6.0;

        List<Flower> res = bouquet.findByStemLengthRange(min, max);

        // Should include ExactlyMin, Middle, ExactlyMax (3 items)
        assertEquals(3, res.size(), "Should find 3 flowers in inclusive range [3.0, 6.0]");
        assertTrue(res.stream().anyMatch(fl -> "ExactlyMin".equals(fl.getName())));
        assertTrue(res.stream().anyMatch(fl -> "Middle".equals(fl.getName())));
        assertTrue(res.stream().anyMatch(fl -> "ExactlyMax".equals(fl.getName())));
        // Should not include Short or Long
        assertFalse(res.stream().anyMatch(fl -> "Short".equals(fl.getName())));
        assertFalse(res.stream().anyMatch(fl -> "Long".equals(fl.getName())));
    }

    @Test
    void testToStringContainsItemsAndTotal() {
        bouquet.addFlower(new Flower("Rose", "Red", 10.0, 50.0, 5, 7));
        bouquet.addAccessory(new Accessory("Bow", 1.0, "Red"));

        String out = bouquet.toString();
        assertTrue(out.contains("Rose"), "toString should contain flower representation");
        assertTrue(out.contains("Bow"), "toString should contain accessory representation");
        // Total price formatted with 2 decimals in toString()
        double total = bouquet.getTotalPrice();
        String totalFormatted = String.format("Total price: %.2f", total);
        assertTrue(out.contains(totalFormatted), "toString should contain total price formatted with 2 decimals");
    }
}
