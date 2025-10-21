package core.domain.inventory.manager;

import common.util.*;
import core.domain.inventory.datastructures.*;
import core.domain.inventory.model.*;
import core.shared.datastructures.*;

public class InventoryManager {
    private InventoryList<Product> inventory;

    public InventoryManager(InventoryList<Product> inventory){
        this.inventory = inventory;
    }

    public void addProduct(Product product){
        inventory.add(product);
    }
    public void removeProduct(String id){
        inventory.remove(id);
    }
    public Product findProductById(String id){
        try{
            return inventory.get(id);
        }catch(LinkedList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }
    public Product findProductByName(String name){
        try{
            return inventory.getProductByName(name);
        }catch(LinkedList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }
    public void updateAvailableStock(Product product, int newValue){
        product.getStockInfo().setAvailableStock(newValue);
    }
    public void updateReorderPoint(Product product, int newValue){
        product.getStockInfo().setReorderPoint(newValue);
    }
    public void updateName(Product product, String name){
        product.getProductInfo().setName(name);
    }
    public void updateManufacturer(Product product, String manufacturer){
        product.getProductInfo().setManufacturer(manufacturer);
    }
    public void updatePrice(Product product, double price){
        product.getProductInfo().setPrice(price);
    }
    public LinkedList<Product> getInventory(){
        return inventory;
    }
}
