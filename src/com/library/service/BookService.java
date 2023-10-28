package com.library.service;

import com.library.database.DatabaseConnection;
import com.library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookService {
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // public void updateBook(Book book) {
    //     String sql = "UPDATE books SET title = ?, author = ? WHERE id = ?";
    //     try (Connection connection = DatabaseConnection.getConnection();
    //          PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    //         preparedStatement.setString(1, book.getTitle());
    //         preparedStatement.setString(2, book.getAuthor());
    //         preparedStatement.setInt(3, book.getId());
    //         int rowsAffected = preparedStatement.executeUpdate();
            
    //         if (rowsAffected > 0) {
    //             System.out.println("Book updated successfully!");
    //         } else {
    //             System.out.println("No book found with ID: " + book.getId());
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
    

    public void deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // public List<Book> searchBooks(String keyword) {
    //     return null;
    // }

    public void issueBook(int bookId, int userId) {
        String checkAvailabilitySql = "SELECT * FROM books WHERE id = ? AND available = 1";
        String issueBookSql = "UPDATE books SET available = 0, user_id = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkAvailabilityStatement = connection.prepareStatement(checkAvailabilitySql);
             PreparedStatement issueBookStatement = connection.prepareStatement(issueBookSql)) {

            // Check if the book is available
            checkAvailabilityStatement.setInt(1, bookId);
            ResultSet resultSet = checkAvailabilityStatement.executeQuery();

            if (resultSet.next()) {
                // Book is available, issue it to the user
                issueBookStatement.setInt(1, userId);
                issueBookStatement.setInt(2, bookId);
                issueBookStatement.executeUpdate();
                System.out.println("Book (ID: " + bookId + ") issued to User (ID: " + userId + ") successfully.");
            } else {
                // Book is not available
                System.out.println("Book with ID: " + bookId + " is not available for issuance.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int bookId) {
        String sql = "UPDATE books SET available = 1, user_id = NULL WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("No book found with ID: " + bookId + " or it is already returned.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setAvailable(resultSet.getBoolean("available"));
                book.setId(resultSet.getInt("user_id"));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
}
