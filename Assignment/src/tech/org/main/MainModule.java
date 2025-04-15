package tech.org.main;

import tech.org.dao.*;
import tech.org.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainModule {

    static ICustomerDAO customerDAO = new CustomerDAO();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
        	System.out.println("-----------------------------------------------------------------------------------------------------------");
            System.out.println("\n===== Welcome to TechShop Dashboard =====");
            System.out.println("1. Customer Operations");
            System.out.println("2. Product Operations");
            System.out.println("3. Order Operations");
            System.out.println("4. Inventory Operations");
            System.out.println("5. Payment Operations");
            System.out.println("6. Sales Report");
            System.out.println("7. productSearchAndRecommendations");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    customerMenu(sc);
                    break;
                case 2:
                    productMenu(sc);
                    break;
                case 3:
                    orderMenu(sc);
                    break;
                case 4:
                    inventoryMenu(sc);
                    break;
                case 5:
                    paymentMenu(sc);
                    break;
                case 6:
                	OrderDAO orderDAO=new OrderDAO();
                    System.out.println("\n--- Sales Report ---");
                    Map<String, Double> salesReport = orderDAO.getProductSalesReport();

                    if (salesReport.isEmpty()) {
                        System.out.println("No sales data available.");
                    } else {
                        double grandTotal = 0;
                        int productCount = 0;

                        for (Map.Entry<String, Double> entry : salesReport.entrySet()) {
                            System.out.println("Product: " + entry.getKey() + " | Sales: " + entry.getValue());
                            grandTotal += entry.getValue();
                            productCount++;
                        }

                        System.out.println("-------------------------------------");
                        System.out.println("Total Products Sold : " + productCount);
                        System.out.println("Total Sales Revenue : " + grandTotal);
                    }
                    break;

                case 7:
                    System.out.println("\n--- Product Search ---");
                    sc.nextLine(); 
                    System.out.print("Enter search keyword: ");
                    String keyword = sc.nextLine();

                    IProductDAO productDAO = new ProductDAO();
                    List<Product> searchResults = productDAO.searchProductsByName(keyword);

                    if (searchResults.isEmpty()) {
                        System.out.println("No products found with keyword: " + keyword);
                    } else {
                        System.out.println("\nSearch Results:");
                        for (Product p : searchResults) {
                            p.getProductDetails();
                            System.out.println("-------------------------");
                        }
                    }
                    break;


                    
                case 8:
                    exit = true;
                    System.out.println("Thank you for using TechShop. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }

    // Customer Menu
    private static void customerMenu(Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Register New Customer");
            System.out.println("2. View Customer Details");
            System.out.println("3. Update Customer Info");
            System.out.println("4. Calculate Total Orders");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    sc.nextLine(); 
                    System.out.print("First Name: ");
                    String fName = sc.nextLine();
                    System.out.print("Last Name: ");
                    String lName = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Address: ");
                    String address = sc.nextLine();

                    Customer newCustomer = new Customer(fName, lName, email, phone, address);
                    boolean added = customerDAO.addCustomer(newCustomer);
                    System.out.println(added ? "Customer registered successfully." : "Registration failed.");
                    break;

                case 2:
                    System.out.print("Enter Customer ID: ");
                    int id = sc.nextInt();
                    Customer customer = customerDAO.getCustomerById(id);
                    if (customer != null) {
                        customer.getCustomerDetails();
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Customer ID: ");
                    int custId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Email: ");
                    String newEmail = sc.nextLine();
                    System.out.print("New Phone: ");
                    String newPhone = sc.nextLine();
                    System.out.print("New Address: ");
                    String newAddress = sc.nextLine();
                    boolean updated = customerDAO.updateCustomerInfo(custId, newEmail, newPhone, newAddress);
                    System.out.println(updated ? "Customer updated successfully." : "Update failed.");
                    break;

                case 4:
                    System.out.print("Enter Customer ID: ");
                    int cid = sc.nextInt();
                    int totalOrders = customerDAO.getTotalOrdersByCustomer(cid);
                    System.out.println("Total Orders Placed: " + totalOrders);
                    break;

                case 5:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    //  Product Menu
    private static void productMenu(Scanner sc) {
        IProductDAO productDAO = new ProductDAO();
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Product Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Product Details");
            System.out.println("3. Update Product Info");
            System.out.println("4. Check Product Stock");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    sc.nextLine(); 
                    System.out.print("Product Name: ");
                    String name = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Price: ");
                    double price = sc.nextDouble();

                    Product product = new Product(name, desc, price);
                    boolean added = productDAO.addProduct(product);
                    System.out.println(added ? "Product added." : "Failed to add product.");
                    break;

                case 2:
                    System.out.print("Enter Product ID: ");
                    int pid = sc.nextInt();
                    Product found = productDAO.getProductById(pid);
                    if (found != null) {
                        found.getProductDetails();
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Product ID: ");
                    int upId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Description: ");
                    String newDesc = sc.nextLine();
                    System.out.print("New Price: ");
                    double newPrice = sc.nextDouble();
                    boolean updated = productDAO.updateProductInfo(upId, newDesc, newPrice);
                    System.out.println(updated ? "Product updated." : "Update failed.");
                    break;

                case 4:
                    System.out.print("Enter Product ID: ");
                    int stockId = sc.nextInt();
                    boolean inStock = productDAO.isProductInStock(stockId);
                    System.out.println(inStock ? "Product is in stock." : "Out of stock.");
                    break;

                case 5:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // order menu
    private static void orderMenu(Scanner sc) {
        IOrderDAO orderDAO = new OrderDAO();
        ICustomerDAO customerDAO = new CustomerDAO();
        IProductDAO productDAO = new ProductDAO();

        boolean back = false;

        while (!back) {
            System.out.println("\n--- Order Menu ---");
            System.out.println("1. Place Order");
            System.out.println("2. View Order Details");
            System.out.println("3. Update Order Status");
            System.out.println("4. Cancel Order");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
            case 1:
                System.out.print("Enter Customer ID: ");
                int custId = sc.nextInt();
                Customer customer = customerDAO.getCustomerById(custId);
                if (customer == null) {
                    System.out.println("Customer not found.");
                    break;
                }

                List<OrderDetail> details = new ArrayList<>();
                boolean addMore = true;
                double total = 0;

                while (addMore) {
                    System.out.print("Enter Product ID: ");
                    int pid = sc.nextInt();
                    Product product = productDAO.getProductById(pid);
                    if (product == null) {
                        System.out.println("Product not found.");
                        continue;
                    }

                    // Check inventory availability
                    InventoryDAO inventoryDAO = new InventoryDAO();
                    Inventory inventory = inventoryDAO.getInventoryByProductId(pid);
                    if (inventory == null || inventory.getQuantityInStock() <= 0) {
                        System.out.println("Product is out of stock.");
                        continue;
                    }

                    System.out.print("Quantity: ");
                    int qty = sc.nextInt();
                    if (qty > inventory.getQuantityInStock()) {
                        System.out.println("Not enough stock. Only " + inventory.getQuantityInStock() + " units available.");
                        //continue;
                        //break;
                        return;
                    }

                    System.out.print("Discount (%): ");
                    double disc = sc.nextDouble();

                    OrderDetail detail = new OrderDetail(0, null, product, qty, disc);
                    details.add(detail);
                    total += detail.calculateSubtotal();

                    System.out.print("Add more items? (yes/no): ");
                    sc.nextLine(); // consume newline
                    String more = sc.nextLine();
                    addMore = more.equalsIgnoreCase("yes");
                }

                Order order = new Order(0, customer, java.time.LocalDateTime.now(), total, "Placed");
                boolean placed = orderDAO.placeOrder(order, details);

                //  If order placed, update inventory
                if (placed) {
                    for (OrderDetail d : details) {
                        new InventoryDAO().removeStock(d.getProduct().getProductID(), d.getQuantity());
                    }
                }

                System.out.println(placed ? "Order placed successfully!" : "Order placement failed.");
                break;


                case 2:
                    System.out.print("Enter Order ID: ");
                    int oid = sc.nextInt();
                    Order foundOrder = orderDAO.getOrderById(oid);
                    
                    if (foundOrder == null) {
                        System.out.println("Order not found.");
                        break;
                    }

                    // Print Customer Details
                    System.out.println("\n--- Customer Info ---");
                    foundOrder.getCustomer().getCustomerDetails();

                    // Print Order Details (items/products)
                    System.out.println("\n--- Order Details ---");
                    List<OrderDetail> orderDetails = orderDAO.getOrderDetailsByOrderId(oid);
                    foundOrder.getOrderDetails(orderDetails);
                    break;


                case 3:
                    System.out.print("Enter Order ID: ");
                    int oId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Status (Processing/Shipped/Completed): ");
                    String newStatus = sc.nextLine();
                    boolean updated = orderDAO.updateOrderStatus(oId, newStatus);
                    System.out.println(updated ? "Status updated." : "Update failed.");
                    break;

                case 4:
                    System.out.print("Enter Order ID: ");
                    int cancelId = sc.nextInt();
                    boolean cancelled = orderDAO.cancelOrder(cancelId);
                    System.out.println(cancelled ? "Order cancelled." : "Failed to cancel.");
                    break;

                case 5:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }


    private static void inventoryMenu(Scanner sc) {
        IInventoryDAO inventoryDAO = new InventoryDAO();
        IProductDAO productDAO = new ProductDAO();

        boolean back = false;

        while (!back) {
            System.out.println("\n--- Inventory Menu ---");
            System.out.println("1. Add Stock");
            System.out.println("2. Remove Stock");
            System.out.println("3. View Inventory Info");
            System.out.println("4. List Low/Out-of-Stock Products");
            System.out.println("5. Update Inventory (Quantity + Timestamp)");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter Product ID: ");
                    int pidAdd = sc.nextInt();
                    System.out.print("Quantity to Add: ");
                    int qtyAdd = sc.nextInt();
                    boolean added = inventoryDAO.addStock(pidAdd, qtyAdd);
                    System.out.println(added ? "Stock added." : "Failed to add stock.");
                    break;

                case 2:
                    System.out.print("Enter Product ID: ");
                    int pidRemove = sc.nextInt();
                    System.out.print("Quantity to Remove: ");
                    int qtyRemove = sc.nextInt();
                    boolean removed = inventoryDAO.removeStock(pidRemove, qtyRemove);
                    System.out.println(removed ? "Stock removed." : "Failed to remove stock (check quantity).");
                    break;

                case 3:
                    System.out.print("Enter Product ID: ");
                    int pidInfo = sc.nextInt();
                    Inventory inv = inventoryDAO.getInventoryByProductId(pidInfo);
                    if (inv != null) {
                        System.out.println("\nInventory ID   : " + inv.getInventoryID());
                        System.out.println("Product Name   : " + inv.getProduct().getProductName());
                        System.out.println("Quantity Stock : " + inv.getQuantityInStock());
                        System.out.println("Last Updated   : " + inv.getLastStockUpdate());
                    } else {
                        System.out.println("Inventory not found for given Product ID.");
                    }
                    break;

                case 4:
                    System.out.print("Enter threshold value: ");
                    int threshold = sc.nextInt();
                    List<Inventory> lowStockList = inventoryDAO.getLowOrOutOfStockProducts(threshold);
                    if (lowStockList.isEmpty()) {
                        System.out.println("All products are sufficiently stocked.");
                    } else {
                        System.out.println("Low or Out-of-Stock Products:");
                        for (Inventory i : lowStockList) {
                            System.out.println("- " + i.getProduct().getProductName() + " | Qty: " + i.getQuantityInStock());
                        }
                    }
                    break;

                case 5:
                    System.out.print("Enter Product ID: ");
                    int pidUpdate = sc.nextInt();
                    System.out.print("Enter quantity to add to inventory: ");
                    int qtyUpdate = sc.nextInt();
                    boolean updated = inventoryDAO.updateInventory(pidUpdate, qtyUpdate);
                    System.out.println(updated ? "Inventory updated." : "Update failed.");
                    break;

                case 6:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    


    private static void paymentMenu(Scanner sc) {
        IPaymentDAO paymentDAO = new PaymentDAO();
        IOrderDAO orderDAO = new OrderDAO();
        

        boolean back = false;

        while (!back) {
            System.out.println("\n--- Payment Menu ---");
            System.out.println("1. Record Payment");
            System.out.println("2. View Payment Status");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter Order ID: ");
                    int orderId = sc.nextInt();

                    Order order = orderDAO.getOrderById(orderId);
                    if (order == null) {
                        System.out.println("Order not found.");
                        break;
                    }

                    //Get previous payment info
                    double totalAmount = order.getTotalAmount();
                    double previouslyPaid = paymentDAO.getTotalPaidForOrder(orderId);
                    double remaining = totalAmount - previouslyPaid;

                    // Show customer how much to pay
                    System.out.println("Order Total     : " + totalAmount);
                    System.out.println("Already Paid    : " + previouslyPaid);
                    System.out.println("Balance to Pay  : " + remaining);

                    if (remaining <= 0) {
                        System.out.println("Payment already completed for this order.");
                        break;
                    }

                    System.out.print("Enter the amount you are paying now: ₹");
                    double amount = sc.nextDouble();

                    if (amount <= 0) {
                        System.out.println("Invalid amount.");
                        break;
                    }

                    // Proceed with recording payment
                    Payment payment = new Payment(0, orderId, amount, "", java.time.LocalDateTime.now());

                    boolean success = paymentDAO.recordPayment(payment);
                    System.out.println(success ? "Payment recorded successfully." : "Failed to record payment.");
                    break;


                case 2:
                    System.out.print("Enter Order ID: ");
                    int oId = sc.nextInt();
                    Order temp = orderDAO.getOrderById(oId);
                    
                    Payment p = paymentDAO.getPaymentByOrderId(oId);
                    if (p != null) {
                        System.out.println("\nPayment ID     : " + p.getPaymentID());
                        System.out.println("Order ID       : " + p.getOrderID());
                        //System.out.println("Amount Paid    : ₹" + p.getAmountPaid());
                        double total = temp.getTotalAmount();
                        double previous = paymentDAO.getTotalPaidForOrder(oId);
                        System.out.println("Total amount: "+total);
                        System.out.println("Paid amount: "+ previous);
                        System.out.println("Remaining amount: "+ (total - previous));
                        //System.out.println("Payment Status : " + p.getPaymentStatus());
                        System.out.println("Payment Date   : " + p.getPaymentDate());
                    } else {
                        System.out.println("No payment found for this order.");
                    }
                    break;

                case 3:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        
        
    }

}
