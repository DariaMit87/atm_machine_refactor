package org.example;

import org.example.controller.ResourcesController;
import org.example.controller.TransactionController;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionIntegrationTest {

    private TransactionController transactionController;
    private UserDAO userDAOMock;
    private TransactionDAO transactionDAOMock;
    private ResourcesController resourcesControllerMock;

    @BeforeEach
        void setUp() throws NoSuchFieldException, IllegalAccessException {
            // Initialize mocks for dependencies
            userDAOMock = Mockito.mock(UserDAO.class);
            transactionDAOMock = Mockito.mock(TransactionDAO.class);
            resourcesControllerMock = Mockito.mock(ResourcesController.class);

            transactionController = new TransactionController();

            // Use reflection to inject mocks into the private fields of TransactionController
            Field userDAOField = TransactionController.class.getDeclaredField("userDAO");
            userDAOField.setAccessible(true);
            userDAOField.set(transactionController, userDAOMock);

            Field transactionDAOField = TransactionController.class.getDeclaredField("transactionDAO");
            transactionDAOField.setAccessible(true);
            transactionDAOField.set(transactionController, transactionDAOMock);

            Field resourcesControllerField = TransactionController.class.getDeclaredField("resourcesController");
            resourcesControllerField.setAccessible(true);
            resourcesControllerField.set(transactionController, resourcesControllerMock);
    }

    @Test
    public void testDepositSuccess() {
        User user = new User(1, "John Doe", "123456", "1234", 1000.0, "customer");
        Mockito.when(userDAOMock.getUserById(1)).thenReturn(user);

        // Call the deposit method
        boolean result = transactionController.deposit(1, 500.0);

        // Verify the result
        assertTrue(result);
        Mockito.verify(userDAOMock).updateUserBalance(1, 1500.0);
        Mockito.verify(transactionDAOMock).addTransaction(1, "Deposit", 500.0);
        Mockito.verify(resourcesControllerMock).updateResourcesAfterTransaction(500.0, true);
    }

    @Test
    public void testDepositInsufficientAmount() {
        // Prepare mock data
        User user = new User(1, "John Doe", "123456", "1234", 1000.0, "customer");
        Mockito.when(userDAOMock.getUserById(1)).thenReturn(user);

        // Call the deposit method with invalid (negative) amount
        boolean result = transactionController.deposit(1, -500.0);

        // Verify the result
        assertFalse(result);
        Mockito.verify(userDAOMock, Mockito.never()).updateUserBalance(Mockito.anyInt(), Mockito.anyDouble());
        Mockito.verify(transactionDAOMock, Mockito.never()).addTransaction(Mockito.anyInt(), Mockito.anyString(), Mockito.anyDouble());
    }

    @Test
    public void testWithdrawSuccess() {
        // Prepare mock data
        User user = new User(1, "John Doe", "123456", "1234", 1000.0, "customer");
        Mockito.when(userDAOMock.getUserById(1)).thenReturn(user);

        // Call the withdraw method
        boolean result = transactionController.withdraw(1, 500.0);

        // Verify the result
        assertTrue(result);
        Mockito.verify(userDAOMock).updateUserBalance(1, 500.0);
        Mockito.verify(transactionDAOMock).addTransaction(1, "Withdrawal", 500.0);
        Mockito.verify(resourcesControllerMock).updateResourcesAfterTransaction(500.0, false);
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        // Prepare mock data
        User user = new User(1, "John Doe", "123456", "1234", 1000.0, "customer");
        Mockito.when(userDAOMock.getUserById(1)).thenReturn(user);

        // Call the withdraw method with an amount greater than the user's balance
        boolean result = transactionController.withdraw(1, 1500.0);

        // Verify the result
        assertFalse(result);
        Mockito.verify(userDAOMock, Mockito.never()).updateUserBalance(Mockito.anyInt(), Mockito.anyDouble());
        Mockito.verify(transactionDAOMock, Mockito.never()).addTransaction(Mockito.anyInt(), Mockito.anyString(), Mockito.anyDouble());
    }

    @Test
    public void testTransferSuccess() {
        // Prepare mock data
        User sender = new User(1, "John Doe", "123456", "1234", 1000.0, "customer");
        User receiver = new User(2, "Jane Doe", "654321", "4321", 500.0, "customer");
        Mockito.when(userDAOMock.getUserById(1)).thenReturn(sender);
        Mockito.when(userDAOMock.getUserByCardNumber("654321")).thenReturn(receiver);

        // Call the transfer method
        boolean result = transactionController.transfer(1, "654321", 300.0);

        // Verify the result
        assertTrue(result);
        Mockito.verify(userDAOMock).updateUserBalance(1, 700.0);
        Mockito.verify(userDAOMock).updateUserBalance(2, 800.0);
        Mockito.verify(transactionDAOMock).addTransaction(1, "Transfer Sent", -300.0);
        Mockito.verify(transactionDAOMock).addTransaction(2, "Transfer Received", 300.0);
        Mockito.verify(resourcesControllerMock).updateResourcesAfterTransaction(0.0, false);
    }

    @Test
    public void testTransferInsufficientFunds() {
        // Prepare mock data
        User sender = new User(1, "John Doe", "123456", "1234", 1000.0, "customer");
        User receiver = new User(2, "Jane Doe", "654321", "4321", 500.0, "customer");
        Mockito.when(userDAOMock.getUserById(1)).thenReturn(sender);
        Mockito.when(userDAOMock.getUserByCardNumber("654321")).thenReturn(receiver);

        // Call the transfer method with an amount greater than the sender's balance
        boolean result = transactionController.transfer(1, "654321", 1500.0);

        // Verify the result
        assertFalse(result);
        Mockito.verify(userDAOMock, Mockito.never()).updateUserBalance(Mockito.anyInt(), Mockito.anyDouble());
        Mockito.verify(transactionDAOMock, Mockito.never()).addTransaction(Mockito.anyInt(), Mockito.anyString(), Mockito.anyDouble());
    }

    @Test
    public void testTransferToSelf() {
        // Prepare mock data
        User sender = new User(1, "John Doe", "123456", "1234", 1000.0, "customer");
        Mockito.when(userDAOMock.getUserById(1)).thenReturn(sender);

        // Call the transfer method to transfer money to self
        boolean result = transactionController.transfer(1, "123456", 300.0);

        // Verify the result
        assertFalse(result);
    }

    @Test
    public void testTransferToTechnician() {
        // Prepare mock data
        User sender = new User(1, "John Doe", "123456", "1234", 1000.0, "customer");
        User receiver = new User(2, "Techie", "789123", "5678", 500.0, "technician");
        Mockito.when(userDAOMock.getUserById(1)).thenReturn(sender);
        Mockito.when(userDAOMock.getUserByCardNumber("789123")).thenReturn(receiver);

        // Call the transfer method to transfer money to a technician (which should be blocked)
        boolean result = transactionController.transfer(1, "789123", 300.0);

        // Verify the result
        assertFalse(result);
    }
}
