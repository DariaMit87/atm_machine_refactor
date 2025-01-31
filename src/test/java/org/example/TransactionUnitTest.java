package org.example;

import org.example.model.Transaction;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionUnitTest {

    @Test
    public void testTransactionConstructor() {
        // Arrange
        int id = 1;
        int userId = 101;
        String type = "Deposit";
        double amount = 500.0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Act
        Transaction transaction = new Transaction(id, userId, type, amount, timestamp);

        // Assert
        assertEquals(id, transaction.getId());
        assertEquals(userId, transaction.getUserId());
        assertEquals(type, transaction.getType());
        assertEquals(amount, transaction.getAmount());
        assertEquals(timestamp, transaction.getTimestamp());
    }

    @Test
    public void testGetId() {
        // Arrange
        int id = 1;
        Transaction transaction = new Transaction(id, 101, "Deposit", 300.0, new Timestamp(System.currentTimeMillis()));

        // Act & Assert
        assertEquals(id, transaction.getId());
    }

    @Test
    public void testGetUserId() {
        // Arrange
        int userId = 101;
        Transaction transaction = new Transaction(1, userId, "Deposit", 500.0, new Timestamp(System.currentTimeMillis()));

        // Act & Assert
        assertEquals(userId, transaction.getUserId());
    }

    @Test
    public void testGetType() {
        // Arrange
        String type = "Deposit";
        Transaction transaction = new Transaction(1, 101, type, 500.0, new Timestamp(System.currentTimeMillis()));

        // Act & Assert
        assertEquals(type, transaction.getType());
    }

    @Test
    public void testGetAmount() {
        // Arrange
        double amount = 1000.0;
        Transaction transaction = new Transaction(1, 101, "Deposit", amount, new Timestamp(System.currentTimeMillis()));

        // Act & Assert
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    public void testGetTimestamp() {
        // Arrange
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction(1, 101, "Withdrawal", 200.0, timestamp);

        // Act & Assert
        assertEquals(timestamp, transaction.getTimestamp());
    }
}

