                                                    
                                             -- TechShop - an electronic gadgets shop                                    
                                                    -- Task:1 Database Design

-- Step 1: Create the database
CREATE DATABASE TechShop;
USE TechShop;

-- Step 2: Create Customers Table
CREATE TABLE Customers (
    CustomerID VARCHAR(10) PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100) UNIQUE,
    Phone VARCHAR(20),
    Address TEXT
);

-- Step 3: Create Products Table
CREATE TABLE Products (
    ProductID VARCHAR(10) PRIMARY KEY,
    ProductName VARCHAR(100),
    Description TEXT,
    Price DECIMAL(10,2)
);

-- Step 4: Create Orders Table
CREATE TABLE Orders (
    OrderID VARCHAR(10) PRIMARY KEY,
    CustomerID VARCHAR(10),
    OrderDate DATE,
    TotalAmount DECIMAL(10,2),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);

-- Step 5: Create OrderDetails Table
CREATE TABLE OrderDetails (
    OrderDetailID VARCHAR(10) PRIMARY KEY,
    OrderID VARCHAR(10),
    ProductID VARCHAR(10),
    Quantity INT,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE
);

-- Step 6: Create Inventory Table
CREATE TABLE Inventory (
    InventoryID VARCHAR(10) PRIMARY KEY,
    ProductID VARCHAR(10),
    QuantityInStock INT,
    LastStockUpdate DATE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE
);

-- Insert 10 records into Customers table
INSERT INTO Customers (CustomerID, FirstName, LastName, Email, Phone, Address) VALUES
('C1', 'John', 'Doe', 'john@example.com', '9876543210', '123 Main St'),
('C2', 'Alice', 'Smith', 'alice@example.com', '8765432109', '456 Oak St'),
('C3', 'Bob', 'Johnson', 'bob@example.com', '7654321098', '789 Pine St'),
('C4', 'David', 'Brown', 'david@example.com', '6543210987', '101 Maple St'),
('C5', 'Emily', 'White', 'emily@example.com', '5432109876', '202 Elm St'),
('C6', 'Michael', 'Clark', 'michael@example.com', '4321098765', '303 Cedar St'),
('C7', 'Sarah', 'Lewis', 'sarah@example.com', '3210987654', '404 Birch St'),
('C8', 'Daniel', 'Walker', 'daniel@example.com', '2109876543', '505 Redwood St'),
('C9', 'Sophia', 'Hall', 'sophia@example.com', '1098765432', '606 Cherry St'),
('C10', 'James', 'Allen', 'james@example.com', '1987654321', '707 Walnut St');

-- Insert 10 records into Products table
INSERT INTO Products (ProductID, ProductName, Description, Price) VALUES
('P1', 'Laptop', 'High-performance laptop', 60000.00),
('P2', 'Smartphone', 'Latest model smartphone', 30000.00),
('P3', 'Smartwatch', 'Fitness tracking smartwatch', 10000.00),
('P4', 'Tablet', '10-inch screen tablet', 20000.00),
('P5', 'Wireless Earbuds', 'Noise-canceling earbuds', 5000.00),
('P6', 'Gaming Console', 'Next-gen gaming console', 45000.00),
('P7', 'Camera', 'Professional DSLR camera', 70000.00),
('P8', 'Monitor', '27-inch 4K monitor', 25000.00),
('P9', 'Keyboard', 'Mechanical gaming keyboard', 8000.00),
('P10', 'Mouse', 'Wireless ergonomic mouse', 4000.00);

-- Insert 10 records into Orders table
INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalAmount) VALUES
('O1', 'C1', '2025-03-21', 70000.00),
('O2', 'C2', '2025-03-22', 40000.00),
('O3', 'C3', '2025-03-23', 50000.00),
('O4', 'C4', '2025-03-24', 20000.00),
('O5', 'C5', '2025-03-25', 90000.00),
('O6', 'C6', '2025-03-26', 10000.00),
('O7', 'C7', '2025-03-27', 15000.00),
('O8', 'C8', '2025-03-28', 35000.00),
('O9', 'C9', '2025-03-29', 55000.00),
('O10', 'C10', '2025-03-30', 75000.00);

-- Insert 10 records into OrderDetails table
INSERT INTO OrderDetails (OrderDetailID, OrderID, ProductID, Quantity) VALUES
('OD1', 'O1', 'P1', 1),
('OD2', 'O1', 'P3', 1),
('OD3', 'O2', 'P2', 1),
('OD4', 'O3', 'P4', 1),
('OD5', 'O4', 'P5', 2),
('OD6', 'O5', 'P6', 1),
('OD7', 'O6', 'P7', 1),
('OD8', 'O7', 'P8', 1),
('OD9', 'O8', 'P9', 1),
('OD10', 'O9', 'P10', 2);

-- Insert 10 records into Inventory table
INSERT INTO Inventory (InventoryID, ProductID, QuantityInStock, LastStockUpdate) VALUES
('I1', 'P1', 50, '2025-03-01'),
('I2', 'P2', 30, '2025-03-02'),
('I3', 'P3', 20, '2025-03-03'),
('I4', 'P4', 25, '2025-03-04'),
('I5', 'P5', 100, '2025-03-05'),
('I6', 'P6', 15, '2025-03-06'),
('I7', 'P7', 10, '2025-03-07'),
('I8', 'P8', 40, '2025-03-08'),
('I9', 'P9', 35, '2025-03-09'),
('I10', 'P10', 45, '2025-03-10');

-- ----------------------------------------------------------------------------------------------------------------------------------

												-- Tasks 2: Select, Where, Between, AND, LIKE
                                                    
use techshop;

-- 1. Write an SQL query to retrieve the names and emails of all customers. 
select concat(FirstName," ",Lastname)as Name, Email from customers;

-- 2. Write an SQL query to list all orders with their order dates and corresponding customer names.
select o.*, CONCAT(c.FirstName," ",c.Lastname) as Name from orders o,customers c where o.customerID=c.customerID;

-- 3. Write an SQL query to insert a new customer record into the "Customers" table. Include customer information such as name, email, and address.
insert into customers Values('C11','Shreetharan','ST','shree07@gmail.com','9876542312','16/H Tiruppur');

-- 4. Write an SQL query to update the prices of all electronic gadgets in the "Products" table by increasing them by 10%
update products set price=price+((10/100)*price);


-- 5. Write an SQL query to delete a specific order and its associated order details from the "Orders" and "OrderDetails" tables. Allow users to input the order ID as a parameter.

delimiter //
create procedure delete_By_ID(in user_ID varchar(10))
begin
	delete from orders where orderID=user_ID;
end//
delimiter ;

call delete_BY_ID('O10');

-- 6.  Write an SQL query to insert a new order into the "Orders" table. Include the customer ID, order date, and any other necessary information.
insert into orders values('O10', 'C10', '2025-03-30', 75000.00);
select * from orders;

-- 7. Write an SQL query to update the contact information (e.g., email and address) of a specific customer in the "Customers" table. 
-- Allow users to input the customer ID and new contact information

delimiter //
create procedure update_BY_ID(in ID varchar(10),in U_FirstName varchar(50),in U_LastName varchar(50),
in U_email varchar(50), in U_phone varchar(20),in U_address text)
begin
	update customers set FirstName= coalesce(U_FirstName,FirstName),
						 LastName=  coalesce(U_LastName,LastName),
                         email=     coalesce(U_email,email),
                         phone=     coalesce(U_phone,phone),
                         address=   coalesce(U_address,address) where customerID=ID;
end//
delimiter ;

call update_BY_ID('C1','Dhoni','MS','msdhoni07@gmail.com',null,null);

-- 8. Write an SQL query to recalculate and update the total cost of each order in the "Orders" table based on the prices and quantities in the "OrderDetails" table.
update orders o set totalAmount=(
	select sum(od.quantity*p.price) from orderDetails od join products p on od.productId= p.productId where o.orderId=od.orderId);
    
-- 9. Write an SQL query to delete all orders and their associated order details for a specific customer from the "Orders" and "OrderDetails" tables. Allow users to input the customer ID as a parameter
 -- The answer is same as question 5
 
 -- 10. Write an SQL query to insert a new electronic gadget product into the "Products" table, including product name, category, price, and any other relevant details
 insert into products values('P11','modem','High speed internet',3000.00);
 
 -- 11. Write an SQL query to update the status of a specific order in the "Orders" table
 -- (e.g., from "Pending" to "Shipped"). Allow users to input the order ID and the new status.
 
 -- There is no specific column called Status..So we creating "status" column
 alter table orders add column status varchar(20) default 'Pending';
 update orders set status='Shipped' where orderID='O8';

-- 12. Write an SQL query to calculate and update the number of orders placed by each customer in the "Customers" table based on the data in the "Orders" table.
alter table customers add column No_of_orders int default 0;
update customers c set No_of_orders=(select count(*) from orders o where o.customerID=c.customerID);

-- --------------------------------------------------------------------------------------------------------------------------------------



                         -- Task 3. Aggregate functions, Having, Order By, GroupBy and Joins

use techshop;

-- 1. Write an SQL query to retrieve a list of all orders along with customer information (e.g., customer name) for each order.
select o.*,concat(c.FirstName,' ',c.LastName ) as Name from orders o inner join customers c on o.customerID=c.customerID;

-- 2. Write an SQL query to find the total revenue generated by each electronic gadget product. Include the product name and the total revenue
select p.productname, sum(p.price* od.quantity) as revenue from products p inner join orderdetails od on p.productID=od.productID group by p.productname;

-- 3. Write an SQL query to list all customers who have made at least one purchase. Include their names and contact information.
select * from customers;
select concat(firstName,' ',LastName) as name,phone,No_of_orders from customers where No_of_orders>0;    

-- 4. Write an SQL query to find the most popular electronic gadget, which is the one with the highest total quantity ordered. 
-- Include the product name and the total quantity ordered.

with temp_table as(
select p.productname as P_name,sum(od.quantity) as total from products p join orderdetails od on p.productID = od.productID
group by p.productName)

select P_name,total from temp_table where total=(select max(total) from temp_table);

-- 5. Write an SQL query to retrieve a list of electronic gadgets along with their corresponding categories.

-- There is no column called categories. so I assume there is category column in products
-- select product_name,category from products;

-- 6. Write an SQL query to calculate the average order value for each customer. Include the customer's name and their average order value.
select firstName, avg(totalAmount) as Average_order_value from customers c join orders o on  c.customerID=o.customerID group by c.customerID;     

-- 7. Write an SQL query to find the order with the highest total revenue. Include the order ID, customer information, and the total revenue.
select orderId,c.customerID,concat(firstName,' ',lastName) as Name,totalamount from orders o join customers c 
on o.customerID=c.customerID where totalamount=(select max(totalamount) from orders);

-- 8. Write an SQL query to list electronic gadgets and the number of times each product has been ordered.
select p.productName, count(orderID) as No_times_ordered from orderdetails od join products p on p.productID=od.productID group by od.productID;   

-- 9. Write an SQL query to find customers who have purchased a specific electronic gadget product.Allow users to input the product name as a parameter.
set @pname ='Mouse';
select c.customerID,c.firstName,p.productName from customers c join orders o on c.customerID=o.customerID join
orderdetails od on o.orderID=od.orderID join
products p on p.productID=od.productID 
where productName= @pname;

-- 10. Write an SQL query to calculate the total revenue generated by all orders placed within a specific time period. 
-- Allow users to input the start and end dates as parameters.

select sum(totalamount) from orders where orderdate between'2025-03-22' and '2025-03-26';            



-- ----------------------------------------------------------------------------------------------------------------------------------



                                                -- Task 4. Subquery and its type:
                                                
use techshop;

-- 1. Write an SQL query to find out which customers have not placed any orders.
select c.FirstName from customers c left join orders o on c.CustomerID=o.CustomerID where o.OrderID is null;

-- 2. Write an SQL query to find the total number of products available for sale. 
select count(*)total_product from inventory where QuantityInStock>0;

-- 3. Write an SQL query to calculate the total revenue generated by TechShop. 
select sum(Quantity*price) as total_revenue from orderdetails od join products p on p.ProductID=od.ProductID;    

-- 4. Write an SQL query to calculate the average quantity ordered for products in a specific category.
-- Allow users to input the category name as a parameter.

-- There is no 'category' column. I just assume there is category column in the products table.

select category,avg(quantity) as avg_quantity from products p join orderdetails od on p.ProductID=od.ProductID
where category='Wearables' group by category;

-- 5. Write an SQL query to calculate the total revenue generated by a specific customer. Allow users to input the customer ID as a parameter.
SET @var='C3';
select c.customerID,concat(FirstName,' ',LastName) as Name,sum(TotalAmount) as revenue from customers c join orders o 
on c.CustomerID=o.CustomerID where o.CustomerID=@var group by o.CustomerID;

-- 6. Write an SQL query to find the customers who have placed the most orders. List their names and the number of orders they've placed.
with temp_table as(
select firstname,count(*) as No_of_orders from customers c join orders o on c.customerID=o.customerID group by c.customerID)

select firstname,No_of_orders from temp_table where No_of_orders=(select max(No_of_orders) from temp_table);

-- 7. Write an SQL query to find the most popular product category, which is the one with the highest total quantity ordered across all orders.
-- We don't have category. Instead we can find most popular product.

with temp_table as(
select productName, sum(quantity) as Total_quantity from products p join orderdetails od on p.productID = od.productID group by p.productID)

select productName, Total_quantity from temp_table where Total_quantity=(select max(Total_quantity) from temp_table);

-- 8. Write an SQL query to find the customer who has spent the most money (highest total revenue) on electronic gadgets. 
-- List their name and total spending.

with temp_table as(
select firstname , sum(totalamount) as total from customers c join orders o on c.customerID=o.customerID group by c.customerID)

select firstname , total from temp_table where total=(select max(total) from temp_table);

-- 9. Write an SQL query to calculate the average order value (total revenue divided by the number of orders) for all customers.
select firstName, avg(totalAmount) as average from customers c join orders o on c.customerID=o.customerID group by c.customerID;

-- 10. Write an SQL query to find the total number of orders placed by each customer and list their names along with the order count.
select firstName, count(orderID) as order_count from customers c join orders o on c.customerID=o.customerID group by c.customerID;  


-- ---------------------------------------------------------------------------------------------------------------------------------------
