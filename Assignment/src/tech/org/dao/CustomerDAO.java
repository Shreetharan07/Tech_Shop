package tech.org.dao;

import tech.org.entity.Customer;
import tech.org.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDAO {

    @Override
    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Customer getCustomerById(int customerId) {
        String query = "SELECT * FROM Customers WHERE CustomerID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Address")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateCustomerInfo(int customerId, String newEmail, String newPhone, String newAddress) {
        String query = "UPDATE Customers SET Email = ?, Phone = ?, Address = ? WHERE CustomerID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newEmail);
            pstmt.setString(2, newPhone);
            pstmt.setString(3, newAddress);
            pstmt.setInt(4, customerId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating customer info: " + e.getMessage());
        }
        return false;
    }

    @Override
    public int getTotalOrdersByCustomer(int customerId) {
        String query = "SELECT COUNT(*) FROM Orders WHERE CustomerID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving total orders: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customers";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Address")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
        }
        return customers;
    }
}
