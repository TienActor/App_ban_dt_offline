package com.example.doan_sale.model;

public class Order {
    private int orderId;
    private String date;
    private String address;
    private double total;
    public Order(int orderId, String date, String address, double total) {
        this.orderId = orderId;
        this.date = date;
        this.address = address;
        this.total = total;
    }
    // Getters và Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
