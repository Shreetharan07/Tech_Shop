package tech.org.dao;

import tech.org.entity.Customer;
import tech.org.entity.Order;
import tech.org.entity.OrderDetail;
import tech.org.entity.Product;
import tech.org.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO implements IOrderDAO {

    @Override
    public boolean placeOrder(Order order, List<OrderDetail> orderDetails) {
        String orderQuery = "INSERT INTO Orders (CustomerID, OrderDate, TotalAmount, Status) VALUES (?, ?, ?, ?)";
        String detailQuery = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Discount) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnUtil.getDbConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getCustomer().getCustomerID());
            orderStmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            orderStmt.setDouble(3, order.getTotalAmount());
            orderStmt.setString(4, order.getStatus());

            int rows = orderStmt.executeUpdate();
            if (rows == 0) {
                conn.rollback();
                return false;
            }

            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                PreparedStatement detailStmt = conn.prepareStatement(detailQuery);
                for (OrderDetail detail : orderDetails) {
                    detailStmt.setInt(1, orderId);
                    detailStmt.setInt(2, detail.getProduct().getProductID());
                    detailStmt.setInt(3, detail.getQuantity());
                    detailStmt.setDouble(4, detail.getDiscount());
                    detailStmt.addBatch();
                }

                detailStmt.executeBatch();
                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Error placing order: " + e.getMessage());
        }

        return false;
    }


    
    public Order getOrderById(int orderId) {
        String query = "SELECT * FROM Orders WHERE OrderID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int customerId = rs.getInt("CustomerID");

                // Load customer using CustomerDAO
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerById(customerId);

                return new Order(
                        rs.getInt("OrderID"),
                        customer,
                        rs.getTimestamp("OrderDate").toLocalDateTime(),
                        rs.getDouble("TotalAmount"),
                        rs.getString("Status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order: " + e.getMessage());
        }
        return null;
    }


    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        String query = "SELECT * FROM OrderDetails WHERE OrderID = ?";
        List<OrderDetail> list = new ArrayList<>();
        
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            ProductDAO productDAO = new ProductDAO(); // Create DAO to fetch product

            while (rs.next()) {
                int productId = rs.getInt("ProductID");        // Get ProductID from result
                Product product = productDAO.getProductById(productId); // Fetch full product

                OrderDetail detail = new OrderDetail(
                    rs.getInt("OrderDetailID"),
                    null,         // order (optional here)
                    product,      // Now product is set!
                    rs.getInt("Quantity"),
                    rs.getDouble("Discount")
                );

                list.add(detail);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving order details: " + e.getMessage());
        }

        return list;
    }


    @Override
    public boolean updateOrderStatus(int orderId, String newStatus) {
        String query = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating order status: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean cancelOrder(int orderId) {
        return updateOrderStatus(orderId, "Cancelled");
    }
    
    
    @Override
    public Map<String, Double> getProductSalesReport() {
        String query = """
            SELECT 
                p.ProductName, 
                SUM(od.Quantity * p.Price) AS totalSales 
            FROM OrderDetails od
            JOIN Products p ON od.ProductID = p.ProductID
            GROUP BY p.ProductName
        """;

        Map<String, Double> report = new LinkedHashMap<>();

        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String productName = rs.getString("ProductName");
                double total = rs.getDouble("totalSales");
                report.put(productName, total);
            }

        } catch (SQLException e) {
            System.out.println("Error generating sales report: " + e.getMessage());
        }

        return report;
    }

}
