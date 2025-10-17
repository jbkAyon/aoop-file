package com.example.go;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Db {

	private static final String DEFAULT_MYSQL_URL = "jdbc:mysql://localhost:3306/go_app?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
	private static final String DEFAULT_SQLITE_URL = "jdbc:sqlite:users.db";
	private static final String DEFAULT_USER = "root"; // XAMPP default
	private static final String DEFAULT_PASS = "";     // XAMPP default empty password

	private Db() {}

	public static Connection getConnection() throws SQLException {
		// If DB_URL is explicitly provided, honor it as-is
		String configuredUrl = System.getenv("DB_URL");
		if (configuredUrl != null && !configuredUrl.isBlank()) {
			String user = getenvOrDefault("DB_USER", DEFAULT_USER);
			String pass = getenvOrDefault("DB_PASS", DEFAULT_PASS);
			return DriverManager.getConnection(configuredUrl, user, pass);
		}

		// Try MySQL first (developer default). If it fails, fall back to SQLite file
		try {
			return DriverManager.getConnection(DEFAULT_MYSQL_URL, DEFAULT_USER, DEFAULT_PASS);
		} catch (SQLException mysqlEx) {
			// Fall back to local SQLite (no server required)
			try {
				return DriverManager.getConnection(DEFAULT_SQLITE_URL);
			} catch (SQLException sqliteEx) {
				// Preserve original cause but include sqlite failure for context
				SQLException combined = new SQLException("Both MySQL and SQLite connections failed.");
				combined.addSuppressed(mysqlEx);
				combined.addSuppressed(sqliteEx);
				throw combined;
			}
		}
	}

	public static void initialize() {
		try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
			// SQL compatible with both MySQL and SQLite
			st.executeUpdate(
					"CREATE TABLE IF NOT EXISTS users (" +
							"email VARCHAR(255) PRIMARY KEY, " +
							"password VARCHAR(255) NOT NULL, " +
							"name VARCHAR(255) NOT NULL" +
							")"
			);
		} catch (SQLException e) {
			throw new RuntimeException("Failed to initialize database", e);
		}
	}

	private static String getenvOrDefault(String key, String fallback) {
		String v = System.getenv(key);
		return (v == null || v.isBlank()) ? fallback : v;
	}
}


