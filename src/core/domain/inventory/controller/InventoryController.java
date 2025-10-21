package core.domain.inventory.controller;

import common.util.*;
import core.domain.inventory.manager.*;
import core.domain.inventory.model.*;
import core.domain.inventory.ui.*;

public class InventoryController {
    private final InventoryView view;
    private final InventoryPrompter prompter;
    private final InventoryManager manager;
    private final InventoryUpdateController updateController;

    public InventoryController(InventoryView view,
                               InventoryPrompter prompter,
                               InventoryManager inventory,
                               InventoryUpdateController updateController) {
        this.view = view;
        this.prompter = prompter;
        this.manager = inventory;
        this.updateController = updateController;
    }

    public void runMenu(){
        String[] options = {
            "Back to main menu",
            "Add product",
            "View Latest Products",
            "View Oldest Products",
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

                case "2", "LATEST", "VIEW LATEST PRODUCTS"
                    -> view.showLatest(manager.getInventory());

                case "3", "OLDEST", "VIEW OLDEST PRODUCTS"
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


}
