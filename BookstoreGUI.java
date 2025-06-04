package com.readnest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

public class BookstoreGUI extends JFrame implements ActionListener {
    private InventoryManager inventoryManager;
    private Cart cart;
    private User currentUser; // Basic user for simplicity

    private JList<Book> bookList;
    private DefaultListModel<Book> bookListModel;
    private JList<String> cartList;
    private DefaultListModel<String> cartListModel;
    private JLabel totalPriceLabel;
    private JTextField quantityField;

    public BookstoreGUI() {
        super("ReadNest Online Bookstore");

        inventoryManager = new InventoryManager();
        cart = new Cart();
        currentUser = new User("customer", "password"); // Simple predefined user

        // Set up the main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // --- Book Catalog Panel ---
        JPanel catalogPanel = new JPanel(new BorderLayout());
        catalogPanel.setBorder(BorderFactory.createTitledBorder("Book Catalog"));

        bookListModel = new DefaultListModel<>();
        loadBooks();
        bookList = new JList<>(bookListModel);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane bookScrollPane = new JScrollPane(bookList);
        catalogPanel.add(bookScrollPane, BorderLayout.CENTER);

        JPanel catalogControls = new JPanel();
        quantityField = new JTextField("1", 5);
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(this);
        catalogControls.add(new JLabel("Quantity:"));
        catalogControls.add(quantityField);
        catalogControls.add(addToCartButton);
        catalogPanel.add(catalogControls, BorderLayout.SOUTH);

        // --- Shopping Cart Panel ---
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane cartScrollPane = new JScrollPane(cartList);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel cartControls = new JPanel();
        JButton removeItemButton = new JButton("Remove Item");
        removeItemButton.addActionListener(this);
        JButton updateQuantityButton = new JButton("Update Quantity");
        updateQuantityButton.addActionListener(this);
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(this);
        totalPriceLabel = new JLabel("Total: $0.00");

        cartControls.add(removeItemButton);
         cartControls.add(updateQuantityButton);
        cartControls.add(new JLabel("  ")); // Spacer
        cartControls.add(totalPriceLabel);
        cartControls.add(checkoutButton);
        cartPanel.add(cartControls, BorderLayout.SOUTH);

        // --- Simulate Multithreading Panel ---
        JPanel multiPanel = new JPanel();
        multiPanel.setBorder(BorderFactory.createTitledBorder("Simulate Concurrent Purchases"));
        JButton simulateButton = new JButton("Run Simulation (3 customers buy 1 of first book)");
        simulateButton.addActionListener(this);
        multiPanel.add(simulateButton);

        // --- Main Content Panel ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, catalogPanel, cartPanel);
        splitPane.setResizeWeight(0.5);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(multiPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);
    }

    private void loadBooks() {
        bookListModel.clear();
        for (Book book : inventoryManager.getAllBooks().values()) {
            bookListModel.addElement(book);
        }
    }

    private void updateCartList() {
        cartListModel.clear();
        double total = 0;
        for (Map.Entry<Book, Integer> entry : cart.getItems().entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            cartListModel.addElement(book.getTitle() + " x " + quantity + " - $" + String.format("%.2f", book.getPrice() * quantity));
            total += book.getPrice() * quantity;
        }
        totalPriceLabel.setText("Total: $" + String.format("%.2f", total));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("Add to Cart".equals(command)) {
            Book selectedBook = bookList.getSelectedValue();
            if (selectedBook != null) {
                try {
                    int quantity = Integer.parseInt(quantityField.getText());
                    if (quantity <= 0) {
                         JOptionPane.showMessageDialog(this, "Quantity must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                         return;
                    }
                     // Check if enough stock is available before adding to cart
                    if (inventoryManager.getBook(selectedBook.getTitle()).getQuantity() < quantity + cart.getItems().getOrDefault(selectedBook, 0)) {
                         JOptionPane.showMessageDialog(this, "Not enough stock available.", "Inventory Error", JOptionPane.ERROR_MESSAGE);
                         return;
                    }

                    cart.addItem(selectedBook, quantity);
                    updateCartList();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to add.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if ("Remove Item".equals(command)) {
             int selectedIndex = cartList.getSelectedIndex();
            if (selectedIndex != -1) {
                // This is a bit hacky, ideally the cart list would store Book objects directly
                // For simplicity, we'll find the book by matching title from the displayed string
                String selectedItemString = cartListModel.getElementAt(selectedIndex);
                String title = selectedItemString.split(" x ")[0];
                 Book bookToRemove = null;
                 for(Book book : cart.getItems().keySet()){
                     if(book.getTitle().equals(title)){
                         bookToRemove = book;
                         break;
                     }
                 }
                if(bookToRemove != null){
                     cart.removeItem(bookToRemove);
                     updateCartList();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to remove.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if ("Update Quantity".equals(command)) {
             int selectedIndex = cartList.getSelectedIndex();
            if (selectedIndex != -1) {
                 String selectedItemString = cartListModel.getElementAt(selectedIndex);
                 String title = selectedItemString.split(" x ")[0];
                 Book bookToUpdate = null;
                 for(Book book : cart.getItems().keySet()){
                     if(book.getTitle().equals(title)){
                         bookToUpdate = book;
                         break;
                     }
                 }

                 if(bookToUpdate != null){
                     String newQuantityString = JOptionPane.showInputDialog(this, "Enter new quantity for " + bookToUpdate.getTitle() + ":", cart.getItems().get(bookToUpdate));
                     if(newQuantityString != null){
                         try{
                             int newQuantity = Integer.parseInt(newQuantityString);
                               if (newQuantity < 0) {
                                 JOptionPane.showMessageDialog(this, "Quantity cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                                 return;
                              }
                             // Check stock for the new total quantity in cart
                             int currentQuantityInCart = cart.getItems().getOrDefault(bookToUpdate, 0);
                             int quantityDifference = newQuantity - currentQuantityInCart;

                             if(quantityDifference > 0 && inventoryManager.getBook(bookToUpdate.getTitle()).getQuantity() < quantityDifference){
                                   JOptionPane.showMessageDialog(this, "Not enough stock available to increase quantity.", "Inventory Error", JOptionPane.ERROR_MESSAGE);
                                 return;
                             }

                             cart.updateQuantity(bookToUpdate, newQuantity);
                             updateCartList();
                         } catch (NumberFormatException ex) {
                              JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                         }
                     }
                 }
            } else {
                 JOptionPane.showMessageDialog(this, "Please select an item to update.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }

        }else if ("Checkout".equals(command)) {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Your cart is empty.", "Checkout Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Simulate processing each item in the cart
            try {
                // Create a list of items to process to avoid ConcurrentModificationException
                // while iterating and modifying the cart indirectly via inventory update
                Map<Book, Integer> itemsToProcess = new HashMap<>(cart.getItems());

                for (Map.Entry<Book, Integer> entry : itemsToProcess.entrySet()) {
                    Book book = entry.getKey();
                    int quantity = entry.getValue();
                    // Process each purchase (this uses the synchronized method)
                    inventoryManager.processPurchase(book, quantity);
                }
                JOptionPane.showMessageDialog(this, "Purchase successful!", "Checkout Complete", JOptionPane.INFORMATION_MESSAGE);
                cart.clearCart();
                updateCartList();
                loadBooks(); // Refresh book list to show updated stock

            } catch (InventoryManager.InsufficientStockException ex) {
                JOptionPane.showMessageDialog(this, "Checkout failed: " + ex.getMessage(), "Checkout Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                 JOptionPane.showMessageDialog(this, "Checkout failed: " + ex.getMessage(), "Checkout Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if ("Run Simulation (3 customers buy 1 of first book)".equals(command)) {
            // Simulate multiple threads buying the same book concurrently
            if (bookListModel.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "No books available to simulate.", "Simulation Error", JOptionPane.WARNING_MESSAGE);
                 return;
            }
            Book bookToSimulate = bookListModel.getElementAt(0); // Get the first book
            int numThreads = 3;

            for (int i = 0; i < numThreads; i++) {
                new Thread(() -> {
                    try {
                        inventoryManager.processPurchase(bookToSimulate, 1);
                        // Update GUI on the EDT after simulation purchase
                        SwingUtilities.invokeLater(() -> {
                             loadBooks(); // Refresh book list to show updated stock
                             updateCartList(); // Update cart in case the simulated book was in it
                        });
                    } catch (InventoryManager.InsufficientStockException ex) {
                        SwingUtilities.invokeLater(() -> {
                             JOptionPane.showMessageDialog(this, "Simulation purchase failed: " + ex.getMessage(), "Simulation Error", JOptionPane.ERROR_MESSAGE);
                        });
                    } catch (IllegalArgumentException ex) {
                         SwingUtilities.invokeLater(() -> {
                              JOptionPane.showMessageDialog(this, "Simulation purchase failed: " + ex.getMessage(), "Simulation Error", JOptionPane.ERROR_MESSAGE);
                         });
                    }
                }).start();
            }
        }
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new BookstoreGUI();
        });
    }
}
