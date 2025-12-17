package test.java.command;

import main.java.model.Flower;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowerTest {

    @Test
    void testConstructorAndGetters() {
        Flower f = new Flower("Rose", "Red", 10.0, 50.0, 5, 7);

        assertEquals("Rose", f.getName());
        assertEquals("Red", f.getColor());
        assertEquals(10.0, f.getStemLength());
        assertEquals(50.0, f.getPrice());
        assertEquals(5, f.getFreshness());
        assertEquals(7, f.getLifespan());
    }

    @Test
    void testSetters() {
        Flower f = new Flower("Tulip", "Yellow", 8.0, 30.0, 4, 6);

        f.setName("Daisy");
        f.setColor("White");
        f.setStemLength(12.0);
        f.setPrice(25.5);
        f.setFreshness(3);
        f.setLifespan(5);

        assertEquals("Daisy", f.getName());
        assertEquals("White", f.getColor());
        assertEquals(12.0, f.getStemLength());
        assertEquals(25.5, f.getPrice());
        assertEquals(3, f.getFreshness());
        assertEquals(5, f.getLifespan());
    }

    @Test
    void testEqualsAndHashCode() {
        Flower f1 = new Flower("Rose", "Red", 10.0, 50.0, 5, 7);
        Flower f2 = new Flower("Rose", "Red", 10.0, 50.0, 5, 7);
        Flower f3 = new Flower("Tulip", "Yellow", 8.0, 30.0, 4, 6);

        // Equals
        assertEquals(f1, f2, "Flowers with same data should be equal");
        assertNotEquals(f1, f3, "Flowers with different data should not be equal");

        // HashCode
        assertEquals(f1.hashCode(), f2.hashCode(), "Equal objects must have same hashCode");
        assertNotEquals(f1.hashCode(), f3.hashCode(), "Different objects should ideally have different hashCode");
    }

    @Test
    void testToStringContainsFields() {
        Flower f = new Flower("Rose", "Red", 10.0, 50.0, 5, 7);
        String str = f.toString();

        assertTrue(str.contains("Rose"));
        assertTrue(str.contains("Red"));
        assertTrue(str.contains("10.0"));
        assertTrue(str.contains("50.0"));
        assertTrue(str.contains("5"));
        assertTrue(str.contains("7"));
    }
}
