package app;

import common.util.*;
import config.*;
import core.domain.analytics.*;
import core.domain.inventory.controller.*;
import core.domain.sales.*;
import core.shared.ui.console.*;

/**
 * The class that contains the root menu of the program.
 * This contains the main controllers of all modules to
 * ensure quick, easy and direct access
 */
public class AppRunner {
    private final ConsoleRenderer renderer;
    private final ConsolePrompter prompter;
    private final InventoryController inventoryController;
    private final SalesController salesController;
    private final AnalyticsController analyticsController;

    /**
     * Public constructor of the class. Uses generic console I/O
     * tools shared in the system and controllers for system
     * navigation
     */
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

    /**
     * Main program loop. Redirects flow to module
     * controllers depending on user choice
     */
    public void run() {
        String[] options = {
                "Exit Program",
                "Check Inventory",
                "Check Sales",
                "Check Analytics",
                "Settings"
        };

        renderer.showHero(AppConfig.PROGRAM_BANNER);

        while(true){
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
                        -> analyticsController.runMenu();
                case "4",
                     "SETTINGS"
                        -> changeSettings();
                case "0",
                     "EXIT",
                     "EXIT PROGRAM"
                        -> { return; }
                default
                        -> Logger.log(
                                "Invalid Choice: " + choice,
                        "User input is not recognized by the system. Please try again",
                        Logger.Level.NOTICE);
            }
        }
    }

    /**
     * Separate method for changing settings
     * at program's runtime
     */
    private void changeSettings(){
        String[] options = {
                "Back to Main Menu",
                "Toggle Ansi Colors",
                "Change Program Name",
                "Change Program Tagline"
        };

        while(true){
            renderer.showBanner(AppConfig.PROGRAM_NAME);
            renderer.showOptions(options);
            String choice = prompter.getString("choice").trim().toUpperCase();
            switch(choice) {
                case "1",
                     "COLORS",
                     "TOGGLE ANSI COLORS" -> AppConfig.IS_ANSI_SUPPORTED = !AppConfig.IS_ANSI_SUPPORTED;
                case "2",
                     "CHANGE PROGRAM NAME" -> AppConfig.PROGRAM_NAME = prompter.getString("new program name");
                case "0",
                     "BACK",
                     "BACK TO MAIN MENU" -> {
                    return;
                }
                default -> Logger.log(
                        "Invalid Choice: " + choice,
                        "User input is not recognized by the system. Please try again",
                        Logger.Level.NOTICE);
            }
        }
    }
}
