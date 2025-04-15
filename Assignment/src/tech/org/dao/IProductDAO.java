package tech.org.dao;

import java.util.List;

import tech.org.entity.Product;

public interface IProductDAO {
    boolean addProduct(Product product);
    Product getProductById(int productId);
    boolean updateProductInfo(int productId, String newDescription, double newPrice);
    boolean isProductInStock(int productId);
    public List<Product> searchProductsByName(String keyword);
    
}

