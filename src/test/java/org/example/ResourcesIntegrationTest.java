package org.example;

import org.example.controller.ResourcesController;
import org.example.model.Resources;
import org.example.model.ResourcesDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesIntegrationTest {

    @Mock
    private ResourcesDAO mockResourcesDAO;  // Mocked DAO to simulate DB interactions

    private ResourcesController resourcesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        resourcesController = new ResourcesController(mockResourcesDAO);  // Inject the mocked ResourcesDAO
    }

    @Test
    void testGetResources() {
        // Arrange
        Resources expectedResources = new Resources(50, 50, 500.0, 50);
        Mockito.when(mockResourcesDAO.getResources()).thenReturn(expectedResources);  // Mock the DAO behavior

        // Act
        Resources result = resourcesController.getResources();

        // Assert
        assertNotNull(result);  // Make sure result is not null
        assertEquals(expectedResources.getInk(), result.getInk());
        assertEquals(expectedResources.getPaper(), result.getPaper());
        assertEquals(expectedResources.getCash(), result.getCash());
        assertEquals(expectedResources.getSoftwareUpdate(), result.getSoftwareUpdate());
    }

    @Test
    void testRefillResources_Success() {
        // Arrange
        Mockito.when(mockResourcesDAO.refillResources()).thenReturn(true);  // Mock success refill

        // Act
        boolean success = resourcesController.refillResources();

        // Assert
        assertTrue(success);
        Mockito.verify(mockResourcesDAO).refillResources();  // Verify that refillResources was called on the DAO
    }

    @Test
    void testRefillResources_Failure() {
        // Arrange
        Mockito.when(mockResourcesDAO.refillResources()).thenReturn(false);  // Mock failure refill

        // Act
        boolean success = resourcesController.refillResources();

        // Assert
        assertFalse(success);
        Mockito.verify(mockResourcesDAO).refillResources();  // Verify that refillResources was called on the DAO
    }

    @Test
    void testUpdateResourcesAfterTransaction_Deposit() {
        // Arrange
        Resources initialResources = new Resources(50, 50, 500.0, 50);
        Mockito.when(mockResourcesDAO.getResources()).thenReturn(initialResources);

        double depositAmount = 100.0;

        // Act
        resourcesController.updateResourcesAfterTransaction(depositAmount, true);

        // Assert
        Mockito.verify(mockResourcesDAO).updateResources(Mockito.any(Resources.class));  // Verify update call
        assertEquals(600, initialResources.getCash());  // Verify cash update
        assertEquals(49, initialResources.getInk());  // Verify ink deduction
        assertEquals(48, initialResources.getPaper());  // Verify paper deduction
        assertEquals(47, initialResources.getSoftwareUpdate());  // Verify software update deduction
    }

    @Test
    void testUpdateResourcesAfterTransaction_Withdrawal() {
        // Arrange
        Resources initialResources = new Resources(50, 50, 500.0, 50);
        Mockito.when(mockResourcesDAO.getResources()).thenReturn(initialResources);

        double withdrawalAmount = 100.0;

        // Act
        resourcesController.updateResourcesAfterTransaction(withdrawalAmount, false);

        // Assert
        Mockito.verify(mockResourcesDAO).updateResources(Mockito.any(Resources.class));
        assertEquals(400, initialResources.getCash());
        assertEquals(49, initialResources.getInk());
        assertEquals(48, initialResources.getPaper());
        assertEquals(47, initialResources.getSoftwareUpdate());
    }

    @Test
    void testUpdateResourcesAfterTransaction_InsufficientResources() {
        // Arrange
        Resources initialResources = new Resources(1, 1, 500.0, 1);
        Mockito.when(mockResourcesDAO.getResources()).thenReturn(initialResources);

        // Act
        resourcesController.updateResourcesAfterTransaction(100.0, false);

        // Assert
        Mockito.verify(mockResourcesDAO, Mockito.never()).updateResources(Mockito.any(Resources.class));  // Verify that resources were not updated
    }
}

