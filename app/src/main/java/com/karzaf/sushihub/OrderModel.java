package com.karzaf.sushihub;

public class OrderModel {
    private String uuid;
    private String orderNumber;
    private String orderItems;
    private int total;
    private int tax;
    private int subtotal;
    private int itemCount;
    private long timestamp;

    public OrderModel() {
    }

    public OrderModel(String uuid, String orderNumber, String orderItems, int total, int tax, int subtotal, int itemCount, long timestamp) {
        this.uuid = uuid;
        this.orderNumber = orderNumber;
        this.orderItems = orderItems;
        this.total = total;
        this.tax = tax;
        this.subtotal = subtotal;
        this.itemCount = itemCount;
        this.timestamp = timestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(String orderItems) {
        this.orderItems = orderItems;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
