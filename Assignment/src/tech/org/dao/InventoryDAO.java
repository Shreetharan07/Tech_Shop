package tech.org.dao;

import tech.org.entity.Inventory;
import tech.org.entity.Product;
import tech.org.util.DBConnUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO implements IInventoryDAO {

    ProductDAO productDAO = new ProductDAO();

    @Override
    public boolean addStock(int productId, int quantity) {
        String insertQuery = "INSERT INTO Inventory (ProductID, QuantityInStock, LastStockUpdate) VALUES (?, ?, ?)";

        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, quantity);
            stmt.setString(3, java.time.LocalDateTime.now().toString()); // Set current time as LastStockUpdate

            return stmt.executeUpdate() > 0; // Returns true if insert was successful
        } catch (SQLException e) {
            System.out.println("Error adding stock: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean removeStock(int productId, int quantity) {
        String query = "UPDATE Inventory SET QuantityInStock = QuantityInStock - ? WHERE ProductID = ? AND QuantityInStock >= ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error removing stock: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateInventory(int productId, int quantity) {
        String query = "UPDATE Inventory SET QuantityInStock = QuantityInStock + ?, LastStockUpdate = ? WHERE ProductID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, LocalDateTime.now().toString()); // or use SQL timestamp if preferred
            stmt.setInt(3, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating inventory: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Inventory getInventoryByProductId(int productId) {
        String query = "SELECT * FROM Inventory WHERE ProductID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Product product = productDAO.getProductById(productId);
                return new Inventory(
                        rs.getInt("InventoryID"),
                        product,
                        rs.getInt("QuantityInStock"),
                        rs.getString("LastStockUpdate")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching inventory: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Inventory> getLowOrOutOfStockProducts(int threshold) {
        String query = "SELECT * FROM Inventory WHERE QuantityInStock <= ?";
        List<Inventory> list = new ArrayList<>();
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, threshold);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = productDAO.getProductById(rs.getInt("ProductID"));
                Inventory inv = new Inventory(
                        rs.getInt("InventoryID"),
                        product,
                        rs.getInt("QuantityInStock"),
                        rs.getString("LastStockUpdate")
                );
                list.add(inv);
            }
        } catch (SQLException e) {
            System.out.println("Error listing low stock: " + e.getMessage());
        }
        return list;
    }
}
