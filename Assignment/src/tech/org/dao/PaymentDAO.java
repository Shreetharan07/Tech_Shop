package tech.org.dao;

import tech.org.entity.Order;
import tech.org.entity.Payment;
import tech.org.util.DBConnUtil;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import tech.org.dao.OrderDAO;


public class PaymentDAO implements IPaymentDAO {

	@Override
	public boolean recordPayment(Payment payment) {
	    IOrderDAO orderDAO = new OrderDAO();
	    Order order = orderDAO.getOrderById(payment.getOrderID());

	    if (order == null) {
	        System.out.println("Invalid Order ID.");
	        return false;
	    }

	    double totalAmount = order.getTotalAmount();

	    // Get total paid till now
	    double previouslyPaid = getTotalPaidForOrder(payment.getOrderID());
	    double newTotalPaid = previouslyPaid + payment.getAmountPaid();

	    // Compare against order total
	    String finalStatus;
	    if (newTotalPaid >= totalAmount) {
	        finalStatus = "Completed";
	    } else {
	        System.out.println("Total paid: ₹" + newTotalPaid + " / ₹" + totalAmount + ". Still pending.");
	        finalStatus = "Pending";
	    }

	    // Insert new payment
	    String query = "INSERT INTO Payments (OrderID, AmountPaid, PaymentStatus, PaymentDate) VALUES (?, ?, ?, ?)";
	    try (Connection conn = DBConnUtil.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, payment.getOrderID());
	        stmt.setDouble(2, payment.getAmountPaid());
	        stmt.setString(3, finalStatus);
	        stmt.setTimestamp(4, Timestamp.valueOf(payment.getPaymentDate()));

	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        System.out.println("Error recording payment: " + e.getMessage());
	    }
	    return false;
	}


    @Override
    public Payment getPaymentByOrderId(int orderId) {
        String query = "SELECT * FROM Payments WHERE OrderID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Payment(
                        rs.getInt("PaymentID"),
                        rs.getInt("OrderID"),
                        rs.getDouble("AmountPaid"),
                        rs.getString("PaymentStatus"),
                        rs.getTimestamp("PaymentDate").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching payment status: " + e.getMessage());
        }
        return null;
    }
    
    
    public double getTotalPaidForOrder(int orderId) {
        String query = "SELECT SUM(AmountPaid) as totalPaid FROM Payments WHERE OrderID = ?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("totalPaid");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching total paid: " + e.getMessage());
        }
        return 0;
    }

}
