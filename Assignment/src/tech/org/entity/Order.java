package tech.org.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderID;
    private Customer customer; // Composition
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status;

    public Order(int orderID, Customer customer, LocalDateTime orderDate, double totalAmount, String status) {
        this.orderID = orderID;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    
    

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Methods

    public void calculateTotalAmount(List<OrderDetail> orderDetails) {
        totalAmount = 0;
        for (OrderDetail orderDetail : orderDetails) {
            totalAmount += orderDetail.calculateSubtotal();
        }
    }

    public void getOrderDetails(List<OrderDetail> orderDetails) {
        for (OrderDetail detail : orderDetails) {
            detail.getOrderDetailInfo();
        }
    }

    public void updateOrderStatus(String newStatus) {
        this.status = newStatus;
    }

    public void cancelOrder(List<Inventory> inventoryList) {
        for (Inventory inventory : inventoryList) {
            inventory.removeFromInventory(1); // Adjust this based on your needs
        }
        this.status = "Cancelled";
    }
}
