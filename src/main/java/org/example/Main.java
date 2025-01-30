package org.example;

import org.example.view.ConsoleView;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            if (conn != null) {
                System.out.println("Database connected");
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database");
            e.printStackTrace();
            return;
        }
        new ConsoleView().showMenu();
    }
}
