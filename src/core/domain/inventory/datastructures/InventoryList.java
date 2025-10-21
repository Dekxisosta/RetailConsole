package core.domain.inventory.datastructures;

import core.domain.inventory.model.*;
import core.shared.datastructures.*;

public class InventoryList<T extends Product> extends LinkedList<T> {
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
}
