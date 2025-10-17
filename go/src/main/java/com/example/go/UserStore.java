package com.example.go;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserStore {

    private UserStore() {}

    public static boolean registerUser(String email, String password, String name) {
        String normalizedEmail = normalizeEmail(email);
        if (normalizedEmail.isEmpty()) {
            return false;
        }

        final String insert = "INSERT INTO users(email, password, name) VALUES (?, ?, ?)";
        try (Connection conn = Db.getConnection(); PreparedStatement ps = conn.prepareStatement(insert)) {
            ps.setString(1, normalizedEmail);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Likely duplicate email (PRIMARY KEY) or other SQL error
            return false;
        }
    }

    public static boolean validateLogin(String email, String password) {
        String normalizedEmail = normalizeEmail(email);
        final String query = "SELECT password FROM users WHERE email = ?";
        try (Connection conn = Db.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, normalizedEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                String stored = rs.getString(1);
                return stored != null && stored.equals(password);
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }
}