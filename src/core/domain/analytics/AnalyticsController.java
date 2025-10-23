package core.domain.analytics;

import common.util.*;
import config.*;

public class AnalyticsController {
    private final AnalyticsConsoleView view;
    private final AnalyticsConsolePrompter prompter;
    private final AnalyticsManager manager;

    public AnalyticsController(AnalyticsConsoleView view,
                           AnalyticsConsolePrompter prompter,
                           AnalyticsManager manager) {
        this.view = view;
        this.prompter = prompter;
        this.manager = manager;
    }

    public void runMenu(){
        String[] options = {
                "Back to Main Menu",
                "View Total Products",
                "View Total Sales Revenue",
                "View Product with Highest Total Sales",
                "View Out of Stock Products"
        };

        while(true){
            view.showBanner(AppConfig.PROGRAM_NAME + ": Analytics Menu");
            view.showOptions(options);
            String choice = prompter.getString("choice").toUpperCase();
            switch(choice){

                case "0", "BACK", "BACK TO MAIN MENU"
                        -> { return; }
                case "1", "VIEW TOTAL PRODUCTS"
                        -> view.showTotalProducts(manager.getTotalProducts());
                case "2", "VIEW TOTAL SALES REVENUE"
                        -> view.showTotalRevenue(manager.getTotalSales());
                case "3", "VIEW PRODUCT WITH HIGHEST TOTAL SALES"
                        -> view.showTopSellingProduct(manager.getTopSelling());
                case "4", "VIEW OUT OF STOCK PRODUCTS"
                        -> view.showOutOfStock(manager.getOutOfStockProducts());
                default
                        -> Logger.log(
                        "Invalid Choice: " + choice,
                        "User input is not recognized by the system. Please try again",
                        Logger.Level.NOTICE);
            }
        }
    }
}
