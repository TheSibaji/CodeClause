package com.library.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LibraryDatabase {
    public static void createDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            // Create database and tables
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS library";
            statement.executeUpdate(createDatabaseSQL);

            // Use library database
            String useDatabaseSQL = "USE library";
            statement.executeUpdate(useDatabaseSQL);

            String createBooksTableSQL = "CREATE TABLE IF NOT EXISTS books (" +
                                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                                        "title VARCHAR(255) NOT NULL," +
                                        "author VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createBooksTableSQL);

            // Create users table
            String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                                        "name VARCHAR(255) NOT NULL," +
                                        "email VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createUsersTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
