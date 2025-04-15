package tech.org.dao;

import tech.org.entity.Payment;

public interface IPaymentDAO {
    boolean recordPayment(Payment payment);
    Payment getPaymentByOrderId(int orderId);
    public double getTotalPaidForOrder(int orderId);
}
