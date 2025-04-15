package tech.org.dao;

import tech.org.entity.Inventory;

import java.util.List;

public interface IInventoryDAO {
    boolean addStock(int productId, int quantity);
    boolean removeStock(int productId, int quantity);
    boolean updateInventory(int productId, int quantity); 
    Inventory getInventoryByProductId(int productId);
    List<Inventory> getLowOrOutOfStockProducts(int threshold);
}

