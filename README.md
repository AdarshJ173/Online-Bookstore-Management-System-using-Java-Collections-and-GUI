# 📚 Online Bookstore Management System 🛒

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-4A90E2?style=for-the-badge)
![Collections](https://img.shields.io/badge/Collections-2C3E50?style=for-the-badge)

## ✨ Features

- 📖 Browse and view available books with stock information
- 🛍️ Add books to shopping cart with quantity validation
- 🧮 Update quantities and remove items from cart
- 💳 Complete checkout process with inventory updates
- 🔄 Real-time stock management with thread safety
- 🧵 Multithreaded purchase simulation
- 🛡️ Robust exception handling for error scenarios

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, or NetBeans) or terminal

### 🔧 Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/AdarshJ173/Online-Bookstore-Management-System-using-Java-Collections-and-GUI.git
   cd Online-Bookstore-Management-System-using-Java-Collections-and-GUI
   ```

2. **Compile the project**
   ```bash
   javac -d . *.java
   ```

3. **Run the application**
   ```bash
   java com.readnest.BookstoreGUI
   ```

## 🎮 How to Use

### 🧑‍💻 User Interface

1. **Browse Books**
   - The left panel displays the book catalog with title, author, price, and stock information
   - Select a book from the list to purchase

2. **Shopping Process**
   - Enter the desired quantity in the text field
   - Click "Add to Cart" to add the selected book to your cart
   - Your cart will be displayed in the right panel

   ```java
   // How books are added to cart in the application
   public void addItem(Book book, int quantity) {
       if (quantity <= 0) {
           throw new IllegalArgumentException("Quantity must be positive.");
       }
       items.put(book, items.getOrDefault(book, 0) + quantity);
   }
   ```

3. **Cart Management**
   - Select an item in your cart to modify it
   - Click "Remove Item" to remove it from your cart
   - Click "Update Quantity" to change the quantity
   - The total price is displayed at the bottom of the cart panel

4. **Checkout**
   - Click "Checkout" to complete your purchase
   - The system will verify stock availability for all items
   - If successful, your purchase will be processed and the inventory updated
   - The cart will be cleared after a successful purchase

5. **Concurrent Purchase Simulation**
   - Click "Run Simulation" to see how the application handles multiple simultaneous purchases
   - This demonstrates thread safety in the inventory management system

## 🏗️ Actual Project Structure

```
Online-Bookstore-Management-System-using-Java-Collections-and-GUI/
├── Book.java                  # Book entity with title, author, price, quantity
├── BookstoreGUI.java          # Main application with Swing GUI implementation
├── Cart.java                  # Shopping cart implementation using HashMap
├── InventoryManager.java      # Thread-safe inventory management
├── User.java                  # Basic user authentication
├── README.md                  # Project documentation
└── com/
    └── readnest/              # Compiled class files
```

## 🧠 Core Logic Explained

### 📊 Data Management

The application uses Java Collections Framework for efficient data management:

```java
// Books are stored in a HashMap for quick lookup by title
private Map<String, Book> inventory = new HashMap<>();

// Shopping cart uses a HashMap to track book quantities
private Map<Book, Integer> items = new HashMap<>();
```

### 🛒 Shopping Cart Implementation

```java
public class Cart {
    private Map<Book, Integer> items;

    public Cart() {
        this.items = new HashMap<>();
    }
    
    public void addItem(Book book, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        items.put(book, items.getOrDefault(book, 0) + quantity);
    }
    
    // Other methods: removeItem, updateQuantity, getTotalPrice, etc.
}
```

### 🔒 Thread-Safe Inventory Management

The application uses synchronized methods to ensure thread safety during concurrent operations:

```java
// Synchronized method for safe concurrent access
public synchronized void processPurchase(Book book, int quantity) 
    throws InsufficientStockException {
    Book bookInStock = inventory.get(book.getTitle());

    if (bookInStock == null) {
        throw new IllegalArgumentException("Book not found in inventory.");
    }

    if (bookInStock.getQuantity() < quantity) {
        throw new InsufficientStockException("Insufficient stock for " + book.getTitle());
    }

    // Simulate some processing time to make concurrency more apparent
    try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }

    bookInStock.setQuantity(bookInStock.getQuantity() - quantity);
}
```

### 🖥️ UI Implementation with Java Swing

The application uses Java Swing components to create a user-friendly interface:

```java
// Main application window setup
public BookstoreGUI() {
    super("ReadNest Online Bookstore");

    inventoryManager = new InventoryManager();
    cart = new Cart();
    currentUser = new User("customer", "password"); // Simple predefined user

    // Set up the main frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLayout(new BorderLayout());
    
    // Initialize and configure UI components...
}
```

## 📝 Design Patterns & OOP Principles

1. **MVC Pattern** - Separates data (Book, Cart), user interface (BookstoreGUI), and control logic
2. **Singleton Pattern** - For inventory management
3. **Encapsulation** - Private fields with getters/setters to control access
4. **Exception Handling** - Custom exceptions for business logic validation

## 💡 Exception Handling

The application handles various exceptional scenarios:

```java
// Custom exception for insufficient stock
public static class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}

// Example of exception handling in the checkout process
try {
    // Process each purchase (this uses the synchronized method)
    inventoryManager.processPurchase(book, quantity);
} catch (InventoryManager.InsufficientStockException ex) {
    JOptionPane.showMessageDialog(this, "Checkout failed: " + ex.getMessage(), 
        "Checkout Error", JOptionPane.ERROR_MESSAGE);
} catch (IllegalArgumentException ex) {
    JOptionPane.showMessageDialog(this, "Checkout failed: " + ex.getMessage(), 
        "Checkout Error", JOptionPane.ERROR_MESSAGE);
}
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📜 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgements

- Java Swing documentation
- Java Collections Framework tutorials
- Java Multithreading concepts

---

⭐ **Star this repository if you found it useful!** ⭐

📧 Contact: [Adarsh J](https://github.com/AdarshJ173)
