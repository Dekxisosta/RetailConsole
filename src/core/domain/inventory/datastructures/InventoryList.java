package core.domain.inventory.datastructures;

import core.domain.inventory.model.*;
import core.shared.datastructures.*;
import core.shared.dto.*;

public class InventoryList<T extends Product> extends RecordList<T> {
    /**
     * Gets product by name given the name
     * @param name
     * @return
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

    public int getNumberOfProducts(){
        int num = 0;
        ListNode<T> current = head;
        while(current != null){
            num+= current.getData().getStockInfo().getAvailableStock();
            current = current.getNext();
        }
        return num;
    }

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
