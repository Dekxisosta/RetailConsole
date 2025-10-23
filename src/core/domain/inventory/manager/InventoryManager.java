package core.domain.inventory.manager;

import common.util.*;
import core.domain.bindables.*;
import core.domain.inventory.datastructures.*;
import core.domain.inventory.model.*;
import core.shared.datastructures.*;
import core.shared.dto.*;

/**
 * Class that manages inventory, it also makes publisher
 * fire appropriate events on data mutation / update
 *
 * @see InventoryList
 * @see Product
 * @see RecordList
 * @see RecordList.ListException
 * @see ProductDTO
 */
public class InventoryManager {
    private final InventoryList<Product> inventory;
    /**
     * Public constructor for the inventory manager class
     * @param inventory
     */
    public InventoryManager(InventoryList<Product> inventory) {
        Events.StockReduction.addListener(this::handle);
        Requests.OutOfStock.setSupplier(this::getOutOfStockProducts);
        Requests.TotalProducts.setSupplier(this::getNumberOfProducts);
        this.inventory = inventory;

    }

    public void handle(Object o) {
        if(!(o instanceof ProductDTO)) return;
        ProductDTO productDTO = (ProductDTO) o;
        Product product = inventory.get(productDTO.getId());
        product.getStockInfo().setAvailableStock(productDTO.getStock());
    }

    /* ===============================================
     *  INVENTORY MANAGER PUBLIC APIs
     ================================================*/

    /**
     * Adds a product to the last position of the list.
     * This also makes publisher fire a Data Transfer Object
     * for other modules to listen to
     * @param product the product to be added
     */
    public void addProduct(Product product){
        inventory.add(product);
        Events.ProductAdded.fire(convertProductToDTO(product));
    }

    /**
     *
     * The publisher doesn't reflect product removal.
     * @param id
     */
    public void removeProduct(String id){
        inventory.remove(id);
    }

    /**
     * Finds a product with the corresponding ID in the list
     * @param id the id to be searched
     */
    public Product findProductById(String id){
        try{
            return inventory.get(id);
        }catch(RecordList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }

    public RecordList<ProductDTO> getOutOfStockProducts(){
        return inventory.getOutOfStockProducts().convertListToDTO();
    }

    public int getNumberOfProducts(){
        return inventory.getNumberOfProducts();
    }

    /**
     * Finds a first occurrence of a product having the given name, case ignored
     * @param name
     * @return
     */
    public Product findProductByName(String name){
        try{
            return inventory.getProductByName(name);
        }catch(RecordList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }

    public void updateProductExternalReferences(Product product){
        Events.ProductUpdated.fire(convertProductToDTO(product));
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


    public RecordList<Product> getInventory(){
        return inventory;
    }

    private ProductDTO convertProductToDTO(Product product){
        return new ProductDTO(
                product.getId(),
                product.getProductInfo().getName(),
                product.getProductInfo().getPrice(),
                product.getStockInfo().getAvailableStock()
        );
    }


}
