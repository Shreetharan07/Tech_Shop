package tech.org.dao;

import tech.org.entity.Product;
import tech.org.util.DBConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {

    @Override
    public boolean addProduct(Product product) {
        String query = "INSERT INTO Products (ProductName, Description, Price) VALUES (?, ?, ?)";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Product getProductById(int productId) {
        String query = "SELECT * FROM Products WHERE ProductID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getDouble("Price")               
                        
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving product: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateProductInfo(int productId, String newDescription, double newPrice) {
        String query = "UPDATE Products SET Description = ?, Price = ? WHERE ProductID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newDescription);
            pstmt.setDouble(2, newPrice);
            pstmt.setInt(3, productId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating product info: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isProductInStock(int productId) {
        String query = "SELECT QuantityInStock FROM Inventory WHERE ProductID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt("QuantityInStock");
                return stock > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking product stock: " + e.getMessage());
        }
        return false;
    }
    
    
    @Override
    public List<Product> searchProductsByName(String keyword) {
        String query = "SELECT * FROM Products WHERE ProductName LIKE ?";
        List<Product> results = new ArrayList<>();

        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%"); // wildcard search
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
                );
                results.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Error during product search: " + e.getMessage());
        }

        return results;
    }


}
