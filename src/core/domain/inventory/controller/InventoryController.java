package core.domain.inventory.controller;

import common.util.*;
import core.domain.inventory.manager.*;
import core.domain.inventory.model.*;
import core.domain.inventory.ui.*;
import core.shared.datastructures.*;

/**
 * Controller class for the inventory module.
 * Directs flow to where it should occur
 *
 * @see InventoryView
 * @see InventoryPrompter
 * @see InventoryManager
 * @see InventoryUpdateController
 */
public class InventoryController {
    private final InventoryView view;
    private final InventoryPrompter prompter;
    private final InventoryManager manager;
    private final InventoryUpdateController updateController;

    public InventoryController(InventoryView view,
                               InventoryPrompter prompter,
                               InventoryManager manager) {
        this.view = view;
        this.prompter = prompter;
        this.manager = manager;
        this.updateController = new InventoryUpdateController(view, prompter, manager);
    }

    public void runMenu(){
        String[] options = {
            "Back to main menu",
            "Add product",
            "View Products by Latest",
            "View Products by Oldest",
            "Update Product",
            "Remove product",
            "Search Product By ID",
            "Search Product By Name"
        };

        while(true){
            view.showBanner("Asacoco Inventory Menu");
            view.showOptions(options);
            String choice = prompter.getString("choice").toUpperCase();
            switch(choice){
                case "0", "BACK", "BACK TO MAIN MENU"
                    -> { return; }

                case "1", "ADD", "ADD PRODUCT"
                    -> manager.addProduct(prompter.getProduct());

                case "2", "LATEST", "VIEW PRODUCTS BY LATEST"
                    -> view.showLatest(manager.getInventory());

                case "3", "OLDEST", "VIEW PRODUCTS BY OLDEST"
                    -> view.showOldest(manager.getInventory());

                case "4", "UPDATE", "UPDATE PRODUCT"
                    -> updateController.updateMenu();

                case "5", "REMOVE", "REMOVE PRODUCT"
                    -> {
                    view.showLatest(manager.getInventory());
                    manager.removeProduct(prompter.getString("id"));
                }

                case "6", "SEARCH" ,"SEARCH PRODUCT BY ID"
                    -> {
                    Product product = manager.findProductById(prompter.getString("id"));
                    if(product!=null) view.showProduct(product);
                }

                case "7", "SEARCH PRODUCT BY NAME"
                        -> {
                    Product product = manager.findProductByName(prompter.getString("name"));
                    if(product!=null) view.showProduct(product);
                }

                default
                    -> Logger.log(
                        "Invalid Choice: " + choice,
                        "User input is not recognized by the system. Please try again",
                        Logger.Level.NOTICE);
            }
        }
    }

    class InventoryUpdateController {
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

        public InventoryUpdateController(InventoryView view,
                                         InventoryPrompter prompter,
                                         InventoryManager manager) {
            this.view = view;
            this.prompter = prompter;
            this.manager = manager;
        }

        /**
         * Update menu loop
         */
        public void updateMenu(){
            Product product = getProductToUpdate();
            if(product==null) return;

            while(true){
                view.showProduct(product);
                view.showBanner("Asacoco Product Update Menu");
                view.showOptions(options);
                String choice = prompter.getString("choice").toUpperCase();

                switch(choice){
                    case "0", "BACK", "BACK TO MAIN MENU"
                            -> { manager.updateProductExternalReferences(product);
                                return;
                    }

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
            }catch(RecordList.ListException e){
                Logger.log(e, Logger.Severity.NOTICE);
                return null;
            }
        }
    }
}
