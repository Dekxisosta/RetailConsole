package core.domain.inventory.controller;

import common.util.*;
import core.domain.inventory.manager.*;
import core.domain.inventory.model.*;
import core.domain.inventory.ui.*;
import core.shared.datastructures.*;

public class InventoryUpdateController {
    private InventoryManager manager;
    private InventoryPrompter prompter;
    private InventoryView view;
    private final String[] options = {
            "Back to Inventory Menu",
            "Change Product Name",
            "Change Product Manufacturer",
            "Change Product Price",
            "Change Available Stock",
            "Change Reorder Point"
    };

    public InventoryUpdateController(InventoryManager manager,
                                     InventoryPrompter prompter,
                                     InventoryView view) {
        this.manager = manager;
        this.prompter = prompter;
        this.view = view;
    }

    public void updateMenu(){
        Product product = getProductToUpdate();
        if(product==null) return;

        while(true){
            view.showProduct(product);
            view.showBanner("Asacoco Product Update Menu");
            view.showOptions(options);
            String choice = prompter.getString("choice").toUpperCase();

            switch(choice){
                case "0", "BACK", "BACK TO MAIN MENU" -> { return; }

                case "1", "NAME", "CHANGE PRODUCT NAME"
                        -> manager.updateName(product,
                        prompter.getString("new product name"));

                case "2", "MANUFACTURER", "CHANGE PRODUCT MANUFACTURER"
                        -> manager.updateManufacturer(product,
                        prompter.getString("new manufacturer"));

                case "3", "PRICE", "CHANGE PRODUCT PRICE"
                        -> manager.updatePrice(product,
                        prompter.getDouble("new price"));

                case "4", "STOCK", "CHANGE AVAILABLE STOCK"
                        -> manager.updateAvailableStock(product,
                        prompter.getNonNegativeInteger("new available stock"));

                case "5", "REORDER", "CHANGE REORDER POINT"
                        -> manager.updateReorderPoint(product,
                        prompter.getNonNegativeInteger("new reorder point"));

                default -> Logger.log(
                        "Invalid Choice: " + choice,
                        "User input is not recognized by the system. Please try again",
                        Logger.Level.NOTICE);
            }
        }
    }
    private Product getProductToUpdate(){
        view.showLatest(manager.getInventory());
        try{
            return manager.findProductById(prompter.getString("id"));
        }catch(LinkedList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }
}
