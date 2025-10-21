package app;

import common.util.*;
import config.*;
import core.domain.analytics.*;
import core.domain.inventory.controller.*;
import core.domain.sales.*;
import core.shared.ui.console.*;

public class AppRunner {
    private final ConsoleRenderer renderer;
    private final ConsolePrompter prompter;
    private final InventoryController inventoryController;
    private final SalesController salesController;
    private final AnalyticsController analyticsController;

    public AppRunner(ConsoleRenderer renderer,
                     ConsolePrompter prompter,
                     InventoryController inventoryController,
                     SalesController salesController,
                     AnalyticsController analyticsController) {
        this.renderer = renderer;
        this.prompter = prompter;
        this.inventoryController = inventoryController;
        this.salesController = salesController;
        this.analyticsController = analyticsController;
    }
    public void run() {
        boolean isRunning = true;
        String[] options = {
                "Exit Program",
                "Check Inventory",
                "Check Sales",
                "Check Analytics"
        };

        while(isRunning){
            renderer.showBanner(AppConfig.PROGRAM_NAME);
            renderer.showOptions(options);
            String choice = prompter.getString("choice").trim().toUpperCase();
            switch(choice){
                case "1",
                     "INVENTORY",
                     "CHECK INVENTORY"
                        -> inventoryController.runMenu();
                case "2",
                     "SALES",
                     "CHECK SALES"
                        -> salesController.runMenu();
                case "3",
                     "ANALYTICS",
                     "CHECK ANALYTICS"
                        -> analyticsController.run();
                case "0",
                     "EXIT",
                     "EXIT PROGRAM"
                        -> isRunning = false;
                default
                        -> Logger.log(
                                "Invalid Choice: " + choice,
                        "User input is not recognized by the system. Please try again",
                        Logger.Level.NOTICE);
            }
        }
    }
}
