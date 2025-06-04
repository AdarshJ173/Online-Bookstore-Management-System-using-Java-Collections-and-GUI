package com.readnest;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, Book> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
        // Initialize with some sample books
        addBook(new Book("The Lord of the Rings", "J.R.R. Tolkien", 25.00, 10));
        addBook(new Book("Pride and Prejudice", "Jane Austen", 15.50, 15));
        addBook(new Book("1984", "George Orwell", 20.00, 8));
        addBook(new Book("To Kill a Mockingbird", "Harper Lee", 12.00, 20));
    }

    public void addBook(Book book) {
        inventory.put(book.getTitle(), book);
    }

    public Book getBook(String title) {
        return inventory.get(title);
    }

    public Map<String, Book> getAllBooks() {
        return inventory;
    }

    // Synchronized method for safe concurrent access
    public synchronized void processPurchase(Book book, int quantity) throws InsufficientStockException {
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
        System.out.println(quantity + " of " + book.getTitle() + " purchased. Remaining stock: " + bookInStock.getQuantity());
    }

    // Custom Exception for insufficient stock
    public static class InsufficientStockException extends Exception {
        public InsufficientStockException(String message) {
            super(message);
        }
    }
}
