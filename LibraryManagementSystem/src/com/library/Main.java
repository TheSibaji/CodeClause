package com.library;

import com.library.database.LibraryDatabase;
import com.library.model.Book;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize the database
        LibraryDatabase.createDatabase();

        // Initialize services
        BookService bookService = new BookService();
        UserService userService = new UserService();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Library Management System!");
            System.out.println("1. Add a Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. Show All Books");
            // System.out.println("4. Search for Books");
            // System.out.println("4. Show Available Books");
            System.out.println("4. Isssue a Book");
            System.out.println("5. Return a Book");
            System.out.println("6. Add a User");
            System.out.println("7. Remove an User");
            System.out.println("8. Show All Users");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.nextLine();
                    System.out.print("Enter author name: ");
                    String authorName = scanner.nextLine();

                    Book newBook = new Book();
                    newBook.setTitle(bookTitle);
                    newBook.setAuthor(authorName);
                    bookService.addBook(newBook);
                    System.out.println("Book added successfully!");
                    break;

                case 2:
                    System.out.print("Enter book ID to Remove: ");
                    int bookIdToDelete = scanner.nextInt();
                    bookService.deleteBook(bookIdToDelete);
                    System.out.println("Book with ID: " + bookIdToDelete + " removed successfully.");
                    break;

                case 3:
                    List<Book> allBooks = bookService.getAllBooks();
                    displayBooks(allBooks);
                    break;

                case 4:
                    System.out.print("Enter book ID to issue: ");
                    int bookIdToIssue = scanner.nextInt();
                    System.out.print("Enter user ID to whom the book will be issued: ");
                    int userIdToIssue = scanner.nextInt();
                    bookService.issueBook(bookIdToIssue, userIdToIssue);
                    break;

                case 5:
                    System.out.print("Enter book ID to return: ");
                    int bookIdToReturn = scanner.nextInt();
                    bookService.returnBook(bookIdToReturn);
                    break;

                case 6:
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter user email: ");
                    String userEmail = scanner.nextLine();

                    User newUser = new User();
                    newUser.setName(userName);
                    newUser.setEmail(userEmail);
                    userService.addUser(newUser);
                    System.out.println("User added successfully!");
                    break;

                case 7:
                    System.out.print("Enter user ID to Remove: ");
                    int userIdToDelete = scanner.nextInt();
                    userService.deleteUser(userIdToDelete);
                    System.out.println("User with ID: " + userIdToDelete + " Removed successfully.");
                    break;

                case 8:
                    List<User> allUsers = userService.getAllUsers();
                    displayUsers(allUsers);
                    break;

                case 9:
                    System.out.println("Exiting the Library Management System. Goodbye!");
                    System.exit(0);
                    scanner.close();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void displayBooks(List<Book> books) {
        System.out.println("\nBooks:");
        for (Book book : books) {
            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
        }
    }

    private static void displayUsers(List<User> users) {
        System.out.println("\nUsers:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        }
    }
}
