package core.domain.sales;

import common.util.*;
import core.domain.sales.ui.*;

public class SalesController {
    private final SalesView view;
    private final SalesPrompter prompter;
    private final SalesManager manager;

    public SalesController(SalesView view,
                           SalesPrompter prompter,
                           SalesManager manager) {
        this.view = view;
        this.prompter = prompter;
        this.manager = manager;
    }

    public void runMenu(){
        String[] options = {
                "Back to Main Menu",
                "Record a Sale",
                "Void a Sale",
                "Display All Records",
                "Generate Sales Summary"
        };

        while(true){
            view.showBanner("Asacoco Sales Menu");
            view.showOptions(options);
            String choice = prompter.getString("choice").toUpperCase();
            switch(choice){
                case "0", "BACK", "BACK TO MAIN MENU"
                        -> { return; }
                case "1"
                default
                        -> Logger.log(
                        "Invalid Choice: " + choice,
                        "User input is not recognized by the system. Please try again",
                        Logger.Level.NOTICE);
            }
        }
    }
}
