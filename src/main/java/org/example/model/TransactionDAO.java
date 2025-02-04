package org.example.model;

import org.example.DatabaseConfig;

import java.sql.*;
import org.example.DatabaseConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
//Data access object
public class TransactionDAO {
    public void addTransaction(int userid, String type, double amount) {
        String query = "INSERT INTO transactions (userid, type, amount, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userid);
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getTransactionsByUserId(int userid){
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE userid = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getInt("userid"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}

