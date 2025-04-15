package tech.org.dao;

import tech.org.entity.Customer;
import java.util.List;

public interface ICustomerDAO {
    boolean addCustomer(Customer customer);
    Customer getCustomerById(int customerId);
    boolean updateCustomerInfo(int customerId, String newEmail, String newPhone, String newAddress);
    int getTotalOrdersByCustomer(int customerId);
    List<Customer> getAllCustomers();
}
