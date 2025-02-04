package org.example.model;
import org.example.DatabaseConfig;

import java.sql.*;
//Data access object
public class ResourcesDAO {
    public Resources getResources() {
        String query = "SELECT * FROM resources WHERE id = 1";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                Resources resources = new Resources();
                resources.setInk(rs.getInt("ink"));
                resources.setPaper(rs.getInt("paper"));
                resources.setCash(rs.getInt("cash"));
                resources.setSoftwareUpdate(rs.getInt("software_update"));
                return resources;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateResources(Resources resources) {
        String query = "UPDATE resources SET ink = ?, paper = ?, cash = ?, software_update = ? WHERE id = 1";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, resources.getInk());
            stmt.setInt(2, resources.getPaper());
            stmt.setDouble(3, resources.getCash());
            stmt.setInt(4, resources.getSoftwareUpdate());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean refillResources() {
        String query = "UPDATE resources SET ink = 100, paper = 100, cash = 1000, software_update = 100 WHERE id = 1";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsUpdated = stmt.executeUpdate(query);
            return rowsUpdated > 0;  // Return true if rows were updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
