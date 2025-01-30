package org.example.controller;
import org.example.DatabaseConfig;
import org.example.model.User;
import org.example.model.UserDAO;

import org.example.view.ConsoleView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

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
        return null; // Return null if user not found
    }

    public double checkBalance(String cardNumber) {
        User user = userDAO.getUserByCardNumber(cardNumber);
        return (user != null) ? user.getBalance() : -1;
    }

    public void updateBalance(int userid, double newBalance) {
        userDAO.updateUserBalance(userid, newBalance);
    }

    private String getUsernameById(int userid) {
        for (User user : userDAO.getAllUsers()) {
            if (user.getId() == userid) {
                return user.getName();
            }
        }
        return null;
    }
}

