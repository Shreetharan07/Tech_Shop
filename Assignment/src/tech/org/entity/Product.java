package tech.org.entity;

public class Product {
    private int productID;
    private String productName;
    private String description;
    private double price;
    

    public Product() {}

    public Product(int productID, String productName, String description, double price) {
        setProductID(productID);
        setProductName(productName);
        setDescription(description);
        setPrice(price);
        
    }
    public Product(String productName, String description, double price) {
        setProductName(productName);
        setDescription(description);
        setPrice(price);
        
    }
    
    
    

    // Getters and Setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        if (productID <= 0) throw new IllegalArgumentException("Product ID must be positive.");
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty())
            throw new IllegalArgumentException("Product name cannot be empty.");
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price must be non-negative.");
        this.price = price;
    }

   
   
    

    // Required Methods

    public void getProductDetails() {
        System.out.println("Product ID    : " + productID);
        System.out.println("Name          : " + productName);
        System.out.println("Description   : " + description);
        System.out.println("Price         : " + price);
        
    }

    public void updateProductInfo(double newPrice, String newDescription) {
        setPrice(newPrice);
        setDescription(newDescription);
        System.out.println("Product info updated successfully.");
    }

    
}
