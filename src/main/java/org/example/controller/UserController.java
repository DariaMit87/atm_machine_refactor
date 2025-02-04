package org.example.controller;
import org.example.DatabaseConfig;
import org.example.model.User;
import org.example.model.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;

public class UserController {
    private UserDAO userDAO = new UserDAO();

    public User authenticateUser(String name, String pin) {
        String query = "SELECT * FROM users WHERE name = ? AND pin = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, pin);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("card_number"),
                        rs.getString("pin"),
                        rs.getDouble("balance"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(String name, String cardNumber, String pin, double balance, String role) {
        if (name.isEmpty() || cardNumber.isEmpty() || pin.isEmpty() || balance < 0 || role.isEmpty()) {
            System.out.println("Invalid user details. Please try again.");
            return;
        }

        User newUser = new User(0, name, cardNumber, pin, balance, role);
        userDAO.addUser(newUser);
        System.out.println("User added successfully.");
    }

    public double checkBalance(int id) {
        User user = userDAO.getUserById(id);
        return (user != null) ? user.getBalance() : -1;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}

