package core.domain.sales;

import common.util.*;
import config.*;
import core.domain.sales.model.*;
import core.domain.sales.ui.console.*;

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
        this.recordController = new SalesRecordController(view, prompter, manager);
    }

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

                case "0", "BACK", "BACK TO MAIN MENU"
                        -> { return; }

                case "1",
                     "RECORD",
                     "RECORD A SALE"
                        -> recordController.runRecordMenu();

                case "2", "DISPLAY ALL SALES"
                        -> view.showSalesRecords(manager.getRecordsList());

                case "3", "DISPLAY PRODUCT TOTALS"
                        -> view.showTotals(manager.getTotalsList());
                case "4", "GENERATE SALES SUMMARY"
                        -> view.showSummary(manager.getTotalSales(),
                        manager.getProductWithMostSales(),
                        manager.getProductWithMostStockSold());
                default
                        -> Logger.log(
                        "Invalid Choice: " + choice,
                        "User input is not recognized by the system. Please try again",
                        Logger.Level.NOTICE);
            }
        }
    }
}
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
                case "0", "BACK", "BACK TO MAIN MENU"
                        -> {
                    if(record.isEmpty()) Logger.log(
                            "Empty List",
                            "The record must have at least one item. Please try again",
                            Logger.Level.NOTICE);
                    else {
                        manager.addSalesRecord(record);
                        return;
                    }
                }
                case "1", "ADD", "ADD A PRODUCT SOLD"
                        -> {
                    view.showTotals(manager.getTotalsList());
                    String id = prompter.getString("product id").toUpperCase();
                    ProductTotals product = manager.findProductByID(id);
                    if(product==null) break;

                    int quantity = prompter.getInt("quantity");
                    if(quantity>product.getStock()) break;

                    SalesItem item = new SalesItem(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            quantity,
                            product.getPrice() * quantity
                    );
                    record.add(item);
                    record.incrementTotalSales(item.getTotalPrice());
                    record.incrementTotalStockSold(item.getQuantity());
                    product.recordPurchase(quantity);
                    manager.fireStockReductionEvent(product);
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