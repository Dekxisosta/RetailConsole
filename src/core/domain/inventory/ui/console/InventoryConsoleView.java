package core.domain.inventory.ui.console;

import core.domain.api.datastructures.*;
import core.domain.inventory.model.*;
import core.shared.ui.console.*;

public class InventoryConsoleView extends ConsoleRenderer {
    public void showOldest(RecordList<Product> inventory){
        showTableHeader();
        System.out.print(inventory.toString());
    }
    public void showLatest(RecordList<Product> inventory){
        showTableHeader();
        System.out.print(inventory.toStringReverse());
    }
    public void showProduct(Product product){
        if(product==null) return;
        showTableHeader();
        System.out.print(product.toString());
    }
    private void showTableHeader(){
        System.out.print("""
                ______________________________________________________________________________________________
                Product ID | Product Name         | Manufacturer | Price   | Stock   | ROP   | Stock Status
                ----------------------------------------------------------------------------------------------
                """);
    }
}
