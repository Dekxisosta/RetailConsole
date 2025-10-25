package core.domain.inventory.datastructures;

import core.api.dto.*;
import core.domain.api.datastructures.*;
import core.domain.inventory.model.*;

public class InventoryList<T extends Product> extends RecordList<T> {
    /**
     * Gets the first occurrence of a product that contains the
     * name to be searched
     *
     * @param name the name of to be searched
     * @return the first occurrence of  which contains
     */
    public Product getProductByName(String name){
        ListNode<T> current = head;
        while(current != null){
            if(current.getData().getProductInfo().getName().equalsIgnoreCase(name)){
                return current.getData();
            }
            current = current.getNext();
        }
        throw new ListException("Unable to find product with name " + name);
    }

    /**
     * Gets the total available stock in the inventory list
     *
     * @return an int containing the number of the
     * total amount of available stock
     */
    public int getNumberOfProducts(){
        int num = 0;
        ListNode<T> current = head;
        while(current != null){
            num+= current.getData().getStockInfo().getAvailableStock();
            current = current.getNext();
        }
        return num;
    }

    /**
     *
     * @return
     */
    public InventoryList<Product> getOutOfStockProducts(){
        InventoryList<Product> list = new InventoryList<>();

        ListNode<T> current = head;
        while(current != null){
            if(current.getData().getStockInfo().getStatus() == Product.StockInfo.Status.OUT_OF_STOCK)
                list.add(current.getData());
            current = current.getNext();
        }
        return list;
    }

    /**
     * Gives a RecordList composed of ProductDTOs converted inventory list to a
     *
     * @return RecordList of ProductDTOs
     */
    public RecordList<ProductDTO> convertListToDTO(){
        RecordList<ProductDTO> list = new RecordList<>();

        ListNode<T> current = head;
        while(current != null){
            Product data = current.getData();
            list.add(new ProductDTO(
               data.getId(),
               data.getProductInfo().getName(),
               data.getProductInfo().getPrice(),
               data.getStockInfo().getAvailableStock()
            ));
            current = current.getNext();
        }
        return list;
    }
}
