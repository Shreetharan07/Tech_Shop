package tech.org.dao;

import tech.org.entity.Order;
import tech.org.entity.OrderDetail;

import java.util.List;
import java.util.Map;

public interface IOrderDAO {
    boolean placeOrder(Order order, List<OrderDetail> orderDetails);
    Order getOrderById(int orderId);
    List<OrderDetail> getOrderDetailsByOrderId(int orderId);
    boolean updateOrderStatus(int orderId, String newStatus);
    boolean cancelOrder(int orderId);
    public Map<String, Double> getProductSalesReport();
}


