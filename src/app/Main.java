package app;

import common.util.*;
import core.domain.analytics.*;
import core.domain.inventory.controller.*;
import core.domain.inventory.datastructures.*;
import core.domain.inventory.manager.*;
import core.domain.inventory.model.*;
import core.domain.inventory.ui.*;
import core.domain.sales.*;
import core.domain.sales.datastructures.*;
import core.domain.sales.model.*;
import core.domain.sales.ui.console.*;
import core.shared.datastructures.*;
import core.shared.ui.console.*;

import java.io.*;
/*
 * Author notes:
 * In this project, I tried implementing an event-driven architecture
 * with loose-coupling, meaning modules don't rely too much on one another
 * and can act on their own.
 *
 * This project doesn't use built-in libraries, so
 * there are plenty of workarounds in place to foster a more
 * modular approach
 *
 * Since there are no explicit warnings in using what
 * could be considered advanced code, we basically went haywire
 * with the approaches we used
 */

/**
 * Class where all dependencies are wired which is extremely inconvenient
 * for people looking to scale the project even more,
 * but to reduce complexity of creating a dependency injection container,
 * the main class is designed as so
 */
public class Main {

    /**
     * Entry point of the program. Instead of the usual print stack trace,
     * a logger utility is used for better debugging prints
     * @param args [not used]
     */
    public static void main(String[] args){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ConsoleRenderer renderer = new ConsoleRenderer();
        ConsolePrompter prompter = new ConsolePrompter(reader);

        SalesController salesController = initSalesModule(reader);
        InventoryController inventoryController = initInventoryModule(reader);
        AnalyticsController analyticsController = initAnalyticsModule(reader);

        try{
            new AppRunner(
                    renderer,
                    prompter,
                    inventoryController,
                    salesController,
                    analyticsController).run();
        }catch(Exception e){
            Logger.log(e, Logger.Severity.FATAL_ERROR, true);
        }
    }

    /**
     * Method for initializing the sales module.
     * Different classes like its manager, prompter, view and lists
     * are found to be created and passed along to whichever class
     * that needs them
     *
     * @param reader the reader needed by the prompter instance of this module
     * @return sales controller that orchestrates the functionality of each class
     */
    public static SalesController initSalesModule(BufferedReader reader){
        RecordList<SalesRecord> salesRecordsList = new RecordList<>();
        SalesList<ProductTotals> totalsList = new SalesList<>();
        SalesManager salesManager = new SalesManager(
                totalsList,
                salesRecordsList);
        SalesConsolePrompter salesPrompter = new SalesConsolePrompter(reader);
        SalesConsoleView salesView = new SalesConsoleView();

        return new SalesController(
                salesView,
                salesPrompter,
                salesManager
        );
    }

    /**
     * Method for initializing the analytics module.
     * Like the sales module, this method creates its appropriate classes
     * to ensure proper functionality is achieved by the module
     *
     * @param reader the reader needed by the prompter instance of this module
     * @return analytics controller that orchestrates the functionality of each class
     */
    public static AnalyticsController initAnalyticsModule(BufferedReader reader){
        AnalyticsConsolePrompter analyticsPrompter = new AnalyticsConsolePrompter(reader);
        AnalyticsConsoleView analyticsView = new AnalyticsConsoleView();
        AnalyticsManager analyticsManager = new AnalyticsManager();

        return new AnalyticsController(
                analyticsView,
                analyticsPrompter,
                analyticsManager
        );
    }

    /**
     * Method for initializing the inventory module.
     * Like the two aforementioned modules, it creates the classes it needs
     * to ensure proper functionality
     *
     * @param reader the reader needed by the prompter instance of this module
     * @return inventory controller that orchestrates the functionality of each class
     */
    public static InventoryController initInventoryModule(BufferedReader reader){
        InventoryList<Product> inventoryList = new InventoryList<>();
        InventoryPrompter inventoryPrompter = new InventoryPrompter(reader);
        InventoryView inventoryView = new InventoryView();
        InventoryManager inventoryManager = new InventoryManager(
                inventoryList);

        populateInventory(inventoryManager, inventoryPrompter);

        return new InventoryController(
                inventoryView,
                inventoryPrompter,
                inventoryManager
        );
    }

    /**
     * Populates the inventory list inside the manager. The reason why
     * we use the manager instance to add products instead of directly adding
     * products to the list, is to ensure all changes are reflected
     * across all modules
     *
     * Under the hood, the manager fires data transfer objects blindly
     * whilst adding or updating its respective product list,
     * anyone who listens to the corresponding fires will consume the transferred
     * data and handles them in any way they like (mostly for updating their
     * separate databases). To see more on how it works, refer to the README file
     * of this project's Github repository
     *
     * @param inventoryManager manager instance used to add products to the inventory module's list
     * @param productIDGenerator product id generator instance to ensure immutable id references
     */
    public static void populateInventory(InventoryManager inventoryManager,
                                         InventoryPrompter productIDGenerator) {
        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Lumen Pack", "LUM", 50.00),
                new Product.StockInfo(1000, 100)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Colorful Sky", "CSK", 80.00),
                new Product.StockInfo(30, 50)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Echoes of Time", "EOT", 120.00),
                new Product.StockInfo(0, 30)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Chromatic Dreams", "CDM", 200.00),
                new Product.StockInfo(300, 20)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Prismatic Night", "PNG", 150.00),
                new Product.StockInfo(400, 40)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Eternal Voyage", "EVG", 180.00),
                new Product.StockInfo(350, 35)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Luminous Path", "LMP", 75.00),
                new Product.StockInfo(600, 60)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Silent Harmony", "SHY", 90.00),
                new Product.StockInfo(500, 50)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Aurora Prism", "APR", 110.00),
                new Product.StockInfo(450, 500)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new Product.ProductInfo("Celestial Pulse", "CPX", 130.00),
                new Product.StockInfo(400, 500)
        ));
    }
}
