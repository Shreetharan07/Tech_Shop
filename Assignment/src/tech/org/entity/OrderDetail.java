package tech.org.entity;

public class OrderDetail {
    private int orderDetailID;
    private Order order; // Composition
    private Product product; // Composition
    private int quantity;
    private double discount;

    public OrderDetail(int orderDetailID, Order order, Product product, int quantity, double discount) {
        this.orderDetailID = orderDetailID;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
    }

    // Getters and Setters
    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    // Methods

    public double calculateSubtotal() {
        return product.getPrice() * quantity * (1 - discount / 100);
    }

    public void getOrderDetailInfo() {
        System.out.println("OrderDetail ID : " + orderDetailID);
        System.out.println("Product        : " + product.getProductName());
        System.out.println("Quantity       : " + quantity);
        System.out.println("Subtotal       : " + calculateSubtotal());
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public void addDiscount(double newDiscount) {
        this.discount = newDiscount;
    }
}
