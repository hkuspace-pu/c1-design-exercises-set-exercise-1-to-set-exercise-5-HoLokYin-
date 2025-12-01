package com.karzaf.sushihub;

public class CartItem {
    private String name;
    private String price;
    private int imageResId;
    private int quantity;
    private int priceValue; // Numeric price for calculations

    public CartItem(String name, String price, int imageResId, int quantity) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.quantity = quantity;
        this.priceValue = extractPriceValue(price);
    }

    private int extractPriceValue(String price) {
        try {
            String numericPrice = price.replaceAll("[^0-9]", "");
            return Integer.parseInt(numericPrice);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        this.priceValue = extractPriceValue(price);
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public int getTotalPrice() {
        return priceValue * quantity;
    }
}