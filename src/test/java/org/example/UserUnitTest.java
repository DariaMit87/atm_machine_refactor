package org.example;

import org.example.model.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class UserUnitTest {
    @Test
    public void testUserConstructor() {
        // Arrange
        int id = 1;
        String name = "John Doe";
        String cardNumber = "123456789";
        String pin = "1234";
        double balance = 1000.0;
        String role = "user";

        // Act
        User user = new User(id, name, cardNumber, pin, balance, role);

        // Assert
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(cardNumber, user.getCardNumber());
        assertEquals(pin, user.getPin());
        assertEquals(balance, user.getBalance());
        assertEquals(role, user.getRole());
    }

    @Test
    public void testGetId() {
        // Arrange
        int id = 1;
        User user = new User(id, "John", "123", "1111", 500.0, "user");

        // Act & Assert
        assertEquals(id, user.getId());
    }

    @Test
    public void testGetName() {
        // Arrange
        String name = "Alice";
        User user = new User(1, name, "654321", "4321", 1500.0, "user");

        // Act & Assert
        assertEquals(name, user.getName());
    }

    @Test
    public void testGetCardNumber() {
        // Arrange
        String cardNumber = "987654321";
        User user = new User(2, "Bob", cardNumber, "7890", 800.0, "admin");

        // Act & Assert
        assertEquals(cardNumber, user.getCardNumber());
    }

    @Test
    public void testGetPin() {
        // Arrange
        String pin = "0000";
        User user = new User(3, "Charlie", "123123", pin, 100.0, "user");

        // Act & Assert
        assertEquals(pin, user.getPin());
    }

    @Test
    public void testGetBalance() {
        // Arrange
        double balance = 500.0;
        User user = new User(4, "David", "111222", "2345", balance, "user");

        // Act & Assert
        assertEquals(balance, user.getBalance());
    }

    @Test
    public void testGetRole() {
        // Arrange
        String role = "admin";
        User user = new User(5, "Eve", "222333", "5678", 1200.0, role);

        // Act & Assert
        assertEquals(role, user.getRole());
    }
}
