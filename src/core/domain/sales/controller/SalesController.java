package core.domain.sales.controller;

import common.util.*;
import config.*;
import core.domain.sales.manager.*;
import core.domain.sales.model.*;
import core.domain.sales.ui.console.*;

/**
 * The main controller class for the Sales module.
 *
 * Responsible for coordinating between the view, prompter, and manager.
 * It handles menu navigation and redirects the user to the proper operations.
 *
 * A separate controller (SalesRecordController) handles the logic for
 * recording individual sales transactions.
 *
 * @see SalesConsoleView
 * @see SalesConsolePrompter
 * @see SalesManager
 * @see SalesRecordController
 */
public class SalesController {
    private final SalesConsoleView view;
    private final SalesConsolePrompter prompter;
    private final SalesManager manager;
    private final SalesRecordController recordController;

    public SalesController(SalesConsoleView view,
                           SalesConsolePrompter prompter,
                           SalesManager manager) {
        this.view = view;
        this.prompter = prompter;
        this.manager = manager;
        // Separate controller to keep the record-specific logic isolated
        this.recordController = new SalesRecordController(view, prompter, manager);
    }

    /**
     * Displays and runs the main Sales menu loop.
     *
     * Continuously prompts the user for input until they go back to the main menu.
     * Routes each option to its respective handler.
     */
    public void runMenu(){
        String[] options = {
                "Back to Main Menu",
                "Record a Sale",
                "Display All Records",
                "Display Product Totals",
                "Generate Sales Summary"
        };

        while(true){
            view.showBanner(AppConfig.PROGRAM_NAME + ": Sales Menu");
            view.showOptions(options);
            String choice = prompter.getString("choice").toUpperCase();

            switch(choice){
                case "0", "BACK", "BACK TO MAIN MENU" -> {
                    return; // exit this menu
                }
                case "1", "RECORD", "RECORD A SALE" ->
                        recordController.runRecordMenu();

                case "2", "DISPLAY ALL SALES" ->
                        view.showSalesRecords(manager.getRecordsList());

                case "3", "DISPLAY PRODUCT TOTALS" ->
                        view.showTotals(manager.getTotalsList());

                case "4", "GENERATE SALES SUMMARY" ->
                        view.showSummary(
                                manager.getTotalSales(),
                                manager.getProductWithMostSales(),
                                manager.getProductWithMostStockSold()
                        );

                default ->
                    // Handle unexpected input gracefully and inform the user
                        Logger.log(
                                "Invalid Choice: " + choice,
                                "User input is not recognized by the system. Please try again",
                                Logger.Level.NOTICE
                        );
            }
        }
    }
}

/**
 * Handles the workflow for recording a single sales transaction.
 *
 * Keeps this logic separate to keep the main controller clean.
 *
 * @see SalesConsoleView
 * @see SalesConsolePrompter
 * @see SalesManager
 */
class SalesRecordController {
    private final SalesConsoleView view;
    private final SalesConsolePrompter prompter;
    private final SalesManager manager;

    public SalesRecordController(SalesConsoleView view,
                                 SalesConsolePrompter prompter,
                                 SalesManager manager) {
        this.view = view;
        this.prompter = prompter;
        this.manager = manager;
    }

    /**
     * Starts the menu flow for creating and finalizing a sales record.
     *
     * The user can add multiple sold products into one record.
     * Once done, the record is submitted to the manager for storage.
     */
    public void runRecordMenu(){
        SalesRecord record = prompter.getSalesRecord();

        String[] options = {
                "Back",
                "Add a product sold"
        };

        while(true){
            view.showSalesRecord(record);
            view.showBanner(AppConfig.PROGRAM_NAME + ": Sales Record Menu");
            view.showOptions(options);

            String choice = prompter.getString("choice").toUpperCase();

            switch(choice){
                case "0", "BACK", "BACK TO MAIN MENU" -> {
                    // Prevent empty records from being saved
                    if(record.isEmpty()) {
                        Logger.log(
                                "Empty List",
                                "The record must have at least one item. Please try again",
                                Logger.Level.NOTICE
                        );
                    } else {
                        // Once a valid record is created, save it via the manager
                        manager.addSalesRecord(record);
                        return;
                    }
                }

                case "1", "ADD", "ADD A PRODUCT SOLD" ->
                        addProduct(record); // Add another product to the record

                default ->
                        Logger.log(
                                "Invalid Choice: " + choice,
                                "User input is not recognized by the system. Please try again",
                                Logger.Level.NOTICE
                        );
            }
        }
    }

    /**
     * Handles the process of adding a product to the current sales record.
     *
     * Shows available products, prompts the user for which one was sold,
     * and then updates both the record and the product’s stock accordingly.
     *
     * @param record The current sales record being constructed
     */
    private void addProduct(SalesRecord record){
        // Display current totals so the user can choose a valid product
        view.showTotals(manager.getTotalsList());

        String id = prompter.getString("product id").toUpperCase();
        ProductTotals product = manager.findProductByID(id);
        if(product == null) {
            Logger.log("Invalid Product ID", "No product found with ID: " + id, Logger.Level.NOTICE);
            return;
        }

        int quantity = prompter.getInt("quantity");
        if(quantity > product.getStock()) {
            Logger.log("Insufficient Stock", "Not enough stock available for this product", Logger.Level.NOTICE);
            return;
        }

        // Create a sales item and update record and product data
        SalesItem item = new SalesItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                quantity,
                product.getPrice() * quantity
        );

        // Add the item to the record
        record.recordSale(item);

        // Reflect the purchase in the product totals
        product.recordPurchase(quantity);

        // Notify other parts of the system that stock has changed
        manager.fireStockReductionEvent(product);
    }
}
