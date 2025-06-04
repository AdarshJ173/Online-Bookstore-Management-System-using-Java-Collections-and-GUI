# Online Bookstore Management System using Java Collections and GUI

## Overview

This project is a Java-based Online Bookstore Management System developed for the fictional startup 'ReadNest'. The application provides a graphical user interface (GUI) using Java Swing and manages book data using Java Collections (`ArrayList` and `HashMap`). It supports user management, book catalog display, shopping cart functionality, purchase processing, real-time inventory updates, and simulated multithreaded operations. The system is designed to be functional, efficient, and robust with comprehensive exception handling.

## Features

*   **User Management:**
    *   Basic user registration and login functionality (simulated with a predefined user).
*   **Book Catalog Management:**
    *   Displaying available books with essential details (title, author, price, quantity/stock).
    *   Utilizes `HashMap` to store and manage book data.
*   **Shopping Cart Functionality:**
    *   Users can add books to a shopping cart.
    *   Ability to view, remove, and update quantities of items in the cart.
*   **Purchase & Order Confirmation:**
    *   Processes orders from the shopping cart.
    *   Provides clear confirmation of successful purchases.
*   **Real-time Inventory Updates:**
    *   Stock levels are updated immediately upon purchase to reflect real-time availability.
*   **Multithreaded Operations:**
    *   Implements and simulates concurrent purchase operations using Java threads.
*   **Robust Exception Handling:**
    *   Handles common scenarios like insufficient stock, invalid user inputs, and attempting to purchase from an empty cart.
*   **Graphical User Interface (GUI):**
    *   User-friendly GUI using Java Swing.
    *   Facilitates displaying the book catalog, interacting with the shopping cart, and confirming purchases.

## Architecture

The project consists of the following main classes:

*   **`Book`:** Represents a book with attributes like title, author, price, and quantity. Includes getter and setter methods for accessing and modifying book properties.
*   **`User`:** Represents a user with attributes like username and password. Includes a basic authentication method.
*   **`Cart`:** Manages the shopping cart using a `HashMap` to store books and their quantities. Provides methods for adding, removing, updating, and retrieving items, as well as calculating the total price.
*   **`InventoryManager`:** Manages the book inventory using a `HashMap`. Provides methods for adding books, retrieving books, and processing purchases. The `processPurchase` method is synchronized to ensure thread safety during concurrent inventory updates. Also includes a custom `InsufficientStockException` class.
*   **`BookstoreGUI`:** The main class that creates the GUI using Java Swing. It displays the book catalog, manages the shopping cart, handles user interactions, and simulates multithreaded purchases. It uses `JList` to show the catalog and the cart, and it uses `JButton` and `JTextField` for user actions.

## Logic Explanation

1.  **Initialization:**
    *   The `BookstoreGUI` initializes the `InventoryManager`, `Cart`, and a predefined `User`.
    *   The `InventoryManager` is populated with sample books.

2.  **Displaying the Book Catalog:**
    *   The `loadBooks` method retrieves all books from the `InventoryManager` and adds them to the `JList`.

3.  **Adding Items to the Cart:**
    *   When the user clicks the "Add to Cart" button, the selected book and quantity are retrieved from the GUI.
    *   The code checks if sufficient stock is available and displays an error message if not.
    *   If sufficient stock is available, the `addItem` method in the `Cart` class is called to add the book and quantity to the cart.
    *   The `updateCartList` method is called to refresh the cart display.

4.  **Removing Items from the Cart:**
    *   When the user clicks the "Remove Item" button, the selected item is removed from the cart.
    *   The `removeItem` method in the `Cart` class is called to remove the book from the cart.
    *   The `updateCartList` method is called to refresh the cart display.

5.  **Updating Item Quantities in the Cart:**
     * When the user clicks the "Update Quantity" button, a dialog box asks the user to input the new quantity.
     * Input validation is performed to ensure the quantity is not negative.
     * The cart is updated using the `updateQuantity` method in the `Cart` class.
     * The `updateCartList` method is called to refresh the cart display.

6.  **Checkout Process:**
    *   When the user clicks the "Checkout" button, the code iterates through the items in the cart.
    *   For each item, the `processPurchase` method in the `InventoryManager` is called to update the stock level.
    *   The `processPurchase` method is synchronized to prevent race conditions during concurrent updates.
    *   If the purchase is successful, a confirmation message is displayed, and the cart is cleared.
    *   If there is insufficient stock, an error message is displayed.

7.  **Simulating Multithreaded Purchases:**
    *   When the user clicks the "Run Simulation" button, multiple threads are created.
    *   Each thread attempts to purchase one unit of the first book in the catalog.
    *   The `processPurchase` method in the `InventoryManager` handles the concurrent updates to the stock level.
    *   The GUI is updated after each thread completes its purchase.

## Exception Handling

The project includes robust exception handling to ensure the application behaves correctly in various scenarios:

*   **`InsufficientStockException`:** Thrown when attempting to purchase more books than available in stock.
*   **`NumberFormatException`:** Thrown when the user enters an invalid quantity (non-numeric input).
*   **`IllegalArgumentException`:** Thrown when the quantity is negative or when attempting to purchase from an empty cart.

## How to Run the Application

1.  **Prerequisites:**
    *   Java Development Kit (JDK) installed on your system.
    *   Ensure the `java` and `javac` commands are accessible from your terminal's PATH.

2.  **Compilation:**
    *   Open a terminal or command prompt.
    *   Navigate to the project directory:
        ```shell
        cd c:\AAJ\1st Year - 2nd Sem\Projects\Oops\Online Bookstore Management System using Java Collections and GUI
        ```
    *   Compile the Java files:
        ```shell
        javac -d . *.java
        ```

3.  **Execution:**
    *   Run the application:
        ```shell
        java com.readnest.BookstoreGUI
        ```

## Object-Oriented Programming (OOP) Principles

The project adheres to OOP principles:

*   **Encapsulation:** Data and methods that operate on that data are bundled within a single unit (e.g., `Book` class containing title, author, price, and methods for inventory updates, with private fields and public getters/setters).
*   **Abstraction:** Complex implementation details are hidden, and only essential features are exposed (e.g., users interacting with a simplified GUI without needing to know backend complexities).

## Syllabus Integration (CLOs)

*   **CLO1:** Solve real-world problems using OOP techniques (demonstrated by Java class hierarchy for Books, Users, Cart).
*   **CLO2:** Identify and handle exceptions, use multithreading (demonstrated by Inventory update threads, stock validation).
*   **CLO3:** Develop GUI-based apps using collections, Swing (demonstrated by GUI for catalog, cart, and payment).

## Notes

*   This project provides a basic implementation of an online bookstore management system. It can be further extended to include more features such as user roles, order management, and database integration.
*   The GUI is designed to be functional and easy to use. It can be enhanced with more advanced features and a more visually appealing design.
