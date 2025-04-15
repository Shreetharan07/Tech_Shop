package tech.org.entity;

import java.util.List;

public class Inventory {
    private int inventoryID;
    private Product product; // Composition
    private int quantityInStock;
    private String lastStockUpdate;

    public Inventory(int inventoryID, Product product, int quantityInStock, String lastStockUpdate) {
        this.inventoryID = inventoryID;
        this.product = product;
        this.quantityInStock = quantityInStock;
        this.lastStockUpdate = lastStockUpdate;
    }

    // Getters and Setters
    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getLastStockUpdate() {
        return lastStockUpdate;
    }

    public void setLastStockUpdate(String lastStockUpdate) {
        this.lastStockUpdate = lastStockUpdate;
    }

    // Methods

    public void getProduct1() {
        System.out.println("Product Name   : " + product.getProductName());
        System.out.println("Quantity in stock: " + quantityInStock);
    }

    public void addToInventory(int quantity) {
        this.quantityInStock += quantity;
    }

    public void removeFromInventory(int quantity) {
        if (quantity <= quantityInStock) {
            this.quantityInStock -= quantity;
        } else {
            System.out.println("Insufficient stock");
        }
    }

    public void updateStockQuantity(int newQuantity) {
        this.quantityInStock = newQuantity;
    }

    public boolean isProductAvailable(int quantityToCheck) {
        return quantityToCheck <= quantityInStock;
    }

    public double getInventoryValue() {
        return product.getPrice() * quantityInStock;
    }

    public void listLowStockProducts(int threshold) {
        if (quantityInStock < threshold) {
            System.out.println("Low stock: " + product.getProductName());
        }
    }

    public void listOutOfStockProducts() {
        if (quantityInStock == 0) {
            System.out.println("Out of stock: " + product.getProductName());
        }
    }

    public void listAllProducts(List<Inventory> inventoryList) {
        for (Inventory inv : inventoryList) {
            System.out.println("Product: " + inv.getProduct().getProductName() + " | Quantity: " + inv.getQuantityInStock());
        }
    }
}
