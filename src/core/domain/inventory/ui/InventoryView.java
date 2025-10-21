package core.domain.inventory.ui;

import core.domain.inventory.model.*;
import core.shared.datastructures.*;
import core.shared.ui.console.*;

public class InventoryView extends ConsoleRenderer {
    /**
     * Public constructor, needs a builder for
     * formats and stylized outputs
     *
     * @param builder
     */
    public InventoryView(ConsoleBuilder builder) {
        super(builder);
    }

    public void showOldest(LinkedList<Product> inventory){
        showTableHeader();
        System.out.print(inventory.toString());
    }
    public void showLatest(LinkedList<Product> inventory){
        showTableHeader();
        System.out.print(inventory.toStringReverse());
    }
    public void showProduct(Product product){
        showTableHeader();
        System.out.print(product.toString());
    }
    private void showTableHeader(){
        System.out.print("""
                Product ID | Product Name         | Manufacturer | Price   | Stock   | ROP   | Stock Status
                --------------------------------------------------------------------------------------
                """);
    }
}
