package com.readnest;

import java.util.HashMap;
import java.util.Map;

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

    public void removeItem(Book book) {
        if (items.containsKey(book)) {
            items.remove(book);
        }
    }

    public void updateQuantity(Book book, int quantity) {
         if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        if (items.containsKey(book)) {
            if (quantity == 0) {
                removeItem(book);
            } else {
                items.put(book, quantity);
            }
        }
    }

    public Map<Book, Integer> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<Book, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void clearCart() {
        items.clear();
    }

     public boolean isEmpty() {
        return items.isEmpty();
    }
}
