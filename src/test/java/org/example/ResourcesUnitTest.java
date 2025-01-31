package org.example;

import org.example.model.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourcesUnitTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange & Act
        Resources resources = new Resources();

        // Assert
        assertEquals(100, resources.getInk());
        assertEquals(100, resources.getPaper());
        assertEquals(1000.0, resources.getCash());
        assertEquals(100, resources.getSoftwareUpdate());
    }

    @Test
    public void testConstructorWithArguments() {
        // Arrange
        int ink = 50;
        int paper = 60;
        double cash = 500.0;
        int softwareUpdate = 30;

        // Act
        Resources resources = new Resources(ink, paper, cash, softwareUpdate);

        // Assert
        assertEquals(ink, resources.getInk());
        assertEquals(paper, resources.getPaper());
        assertEquals(cash, resources.getCash());
        assertEquals(softwareUpdate, resources.getSoftwareUpdate());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        Resources resources = new Resources();

        // Act
        resources.setInk(80);
        resources.setPaper(70);
        resources.setCash(1500.0);
        resources.setSoftwareUpdate(90);

        // Assert
        assertEquals(80, resources.getInk());
        assertEquals(70, resources.getPaper());
        assertEquals(1500.0, resources.getCash());
        assertEquals(90, resources.getSoftwareUpdate());
    }

    @Test
    public void testIsLowOnResources_whenAnyResourceIsLow() {
        // Arrange
        Resources resources = new Resources(10, 100, 50.0, 100);

        // Act
        boolean isLow = resources.isLowOnResources();

        // Assert
        assertTrue(isLow);
    }

    @Test
    public void testIsLowOnResources_whenNoResourceIsLow() {
        // Arrange
        Resources resources = new Resources(50, 100, 500.0, 100);

        // Act
        boolean isLow = resources.isLowOnResources();

        // Assert
        assertFalse(isLow);
    }

    @Test
    public void testIsOutOfResources_whenAnyResourceIsZero() {
        // Arrange
        Resources resources = new Resources(0, 100, 1000.0, 100);

        // Act
        boolean isOut = resources.isOutOfResources();

        // Assert
        assertTrue(isOut);
    }

    @Test
    public void testIsOutOfResources_whenNoResourceIsZero() {
        // Arrange
        Resources resources = new Resources(50, 100, 500.0, 100);

        // Act
        boolean isOut = resources.isOutOfResources();

        // Assert
        assertFalse(isOut);
    }
}

