package org.example;

import org.example.controller.UserController;
import org.example.model.User;
import org.example.model.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

class UserIntegrationTest {

    private UserController userController;

    @Mock
    private UserDAO userDAOMock;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize the mock object
        userDAOMock = Mockito.mock(UserDAO.class);
        userController = new UserController();

        // Use reflection to inject the mock DAO into the controller
        Field field = UserController.class.getDeclaredField("userDAO");
        field.setAccessible(true);
        field.set(userController, userDAOMock);
    }


    @Test
    public void testAuthenticateUser() throws Exception {
        UserDAO userDAOMock = Mockito.mock(UserDAO.class);
        UserController userController = new UserController();

        // Use reflection to set the private field 'userDAO' to mock
        Field field = UserController.class.getDeclaredField("userDAO");
        field.setAccessible(true);
        field.set(userController, userDAOMock);

        User mockedUser = new User(1, "John Doe", "123456", "1234", 1000.0, "customer");
        Mockito.when(userDAOMock.getUserById(1)).thenReturn(mockedUser);

        double balance = userController.checkBalance(1);
        assertEquals(1000.0, balance);
    }

    @Test
    void testAddUser_Success() {
        // Arrange
        String name = "newuser";
        String cardNumber = "987654321";
        String pin = "5678";
        double balance = 200.0;
        String role = "customer";

        // Act
        userController.addUser(name, cardNumber, pin, balance, role);

        // Assert
        verify(userDAOMock, times(1)).addUser(any(User.class)); // Verify if addUser was called on DAO
    }

    @Test
    void testAddUser_InvalidData() {
        // Arrange
        String name = "";
        String cardNumber = "987654321";
        String pin = "5678";
        double balance = 200.0;
        String role = "customer";

        // Act
        userController.addUser(name, cardNumber, pin, balance, role);

        // Assert
        verify(userDAOMock, times(0)).addUser(any(User.class));
    }

    @Test
    void testCheckBalance_Success() {
        // Arrange
        int userId = 1;
        User user = new User(userId, "testuser", "123456789", "1234", 500.0, "customer");

        // Mock the behavior of UserDAO to return the user with the specified ID
        when(userDAOMock.getUserById(userId)).thenReturn(user);

        // Act
        double balance = userController.checkBalance(userId);

        // Assert
        assertEquals(500.0, balance);
        verify(userDAOMock, times(1)).getUserById(userId);
    }

    @Test
    void testCheckBalance_UserNotFound() {
        // Arrange
        int userId = 99;

        // Mock the behavior of UserDAO to return null for non-existing user
        when(userDAOMock.getUserById(userId)).thenReturn(null);

        // Act
        double balance = userController.checkBalance(userId);

        // Assert
        assertEquals(-1.0, balance);
        verify(userDAOMock, times(1)).getUserById(userId);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        User user1 = new User(1, "user1", "123", "1234", 100.0, "customer");
        User user2 = new User(2, "user2", "456", "5678", 200.0, "customer");

        // Mock the behavior of UserDAO to return a list of users
        when(userDAOMock.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> users = userController.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        verify(userDAOMock, times(1)).getAllUsers(); // Verify if DAO method was called
    }
}

