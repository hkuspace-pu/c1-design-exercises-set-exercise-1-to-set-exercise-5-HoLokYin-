package com.karzaf.sushihub;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(String name, String price, int imageResId) {
        // Always add as new item (quantity always 1 per entry)
        cartItems.add(new CartItem(name, price, imageResId, 1));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getTotalItemCount() {
        int totalCount = 0;
        for (CartItem item : cartItems) {
            totalCount += item.getQuantity();
        }
        return totalCount;
    }

    public int getCartEntriesCount() {
        return cartItems.size();
    }

    public int getSubtotal() {
        int subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public int getTax() {
        return (int) (getSubtotal() * 0.10);
    }

    public int getTotal() {
        return getSubtotal() + getTax();
    }

    public void clearCart() {
        cartItems.clear();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
