package tech.org.entity;

import tech.org.exception.InvalidDataException;

public class Customer {

    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    // Constructor
    public Customer(int customerID, String firstName, String lastName, String email, String phone, String address) {
        setCustomerID(customerID);
        setFirstName(firstName);
        setLastName(lastName);
        try {
            setEmail(email);
            setPhone(phone);
        } catch (InvalidDataException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        setAddress(address);
    }

    // Constructor
    public Customer(String firstName, String lastName, String email, String phone, String address) {
        setFirstName(firstName);
        setLastName(lastName);
        try {
            setEmail(email);
            setPhone(phone);
        } catch (InvalidDataException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        setAddress(address);
    }

    
    
    
    // Getters and Setters
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        if (customerID <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive.");
        }
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty.");
        }
        this.lastName = lastName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidDataException {
        if (email == null || !email.contains("@")) {
            throw new InvalidDataException("Invalid email format.");
        }
        this.email = email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws InvalidDataException {
        if (phone == null || phone.length() < 10) {
            throw new InvalidDataException("Phone number must be at least 10 digits.");
        }
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.address = address.trim();
    }

    // Methods
    public void getCustomerDetails() {
        System.out.println("Customer ID   : " + customerID);
        System.out.println("Name          : " + firstName + " " + lastName);
        System.out.println("Email         : " + email);
        System.out.println("Phone         : " + phone);
        System.out.println("Address       : " + address);
    }

    public void updateCustomerInfo(String email, String phone, String address) {
        try {
            setEmail(email);
            setPhone(phone);
        } catch (InvalidDataException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        setAddress(address);
    }

    public int calculateTotalOrders() {
        
        return 0; 
    }
}
