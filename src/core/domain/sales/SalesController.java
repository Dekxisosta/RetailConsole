package core.domain.sales;

import common.util.*;
import config.*;
import core.domain.sales.ui.console.*;

public class SalesController {
    private final SalesConsoleView view;
    private final SalesConsolePrompter prompter;
    private final SalesManager manager;
    private final SalesRecordController recordController;

    public SalesController(SalesConsoleView view,
                           SalesConsolePrompter prompter,
                           SalesManager manager,
                           SalesRecordController recordController) {
        this.view = view;
        this.prompter = prompter;
        this.manager = manager;
        this.recordController = recordController;
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

                default
                        -> Logger.log(
                        "Invalid Choice: " + choice,
                        "User input is not recognized by the system. Please try again",
                        Logger.Level.NOTICE);
            }
        }
    }
}
