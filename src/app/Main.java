package app;

import common.util.*;
import core.domain.analytics.*;
import core.domain.events.*;
import core.domain.inventory.controller.*;
import core.domain.inventory.datastructures.*;
import core.domain.inventory.manager.*;
import core.domain.inventory.model.*;
import core.domain.inventory.ui.*;
import core.domain.inventory.util.*;
import core.domain.sales.*;
import core.domain.sales.model.*;
import core.domain.sales.ui.console.*;
import core.domain.sales.util.*;
import core.shared.datastructures.*;
import core.shared.dto.*;
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
 *
 */

/**
 * Class where all dependencies are wired
 */
public class Main {
    public static void main(String[] args){
        // Reader Object
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // General Utilities
        ConsoleBuilder builder = new ConsoleBuilder();
        ConsoleRenderer renderer = new ConsoleRenderer(builder);
        ConsolePrompter prompter = new ConsolePrompter(reader);

        // Events
        ProductTransferEvent<ProductDTO> productTransferEvent = new ProductTransferEvent<>();
        StockReductionEvent<ProductDTO> stockReductionEvent = new StockReductionEvent<>();

        // Lists for databases
        LinkedList<ProductTotals> totalsList = new LinkedList<>();
        LinkedList<SalesRecord> salesRecordsList = new LinkedList<>();
        InventoryList<Product> inventory = new InventoryList<>();

        // ID Generators
        ProductIDGenerator productIDGenerator = new ProductIDGenerator();
        SalesRecordIDGenerator salesRecordIDGenerator = new SalesRecordIDGenerator();

        // Extended prompters for modules
        SalesConsolePrompter salesPrompter = new SalesConsolePrompter(reader, salesRecordIDGenerator);
        InventoryPrompter inventoryPrompter = new InventoryPrompter(reader, productIDGenerator);

        // View files
        SalesConsoleView salesView = new SalesConsoleView(builder);
        InventoryView inventoryView = new InventoryView(builder);

        // Managers
        SalesManager salesManager = new SalesManager(stockReductionEvent, totalsList, salesRecordsList);
        InventoryManager inventoryManager = new InventoryManager(
                productTransferEvent,inventory);
        // Accessory controllers
        SalesRecordController salesRecordController = new SalesRecordController(
                salesView,
                salesPrompter,
                salesManager
        );
        InventoryUpdateController inventoryUpdateController= new InventoryUpdateController(
                inventoryManager,
                inventoryPrompter,
                inventoryView
        );

        // Main controllers
        SalesController salesController =  new SalesController(
                salesView,
                salesPrompter,
                salesManager,
                salesRecordController
        );
        InventoryController inventoryController = new InventoryController(
                inventoryView,
                inventoryPrompter,
                inventoryManager,
                inventoryUpdateController
        );
        AnalyticsController analyticsController = new AnalyticsController();

        // Adds listeners to events
        stockReductionEvent.addListener(inventoryManager);
        productTransferEvent.addListener(salesManager);

        // Populates the inventory with dummy data
        populateInventory(inventoryManager, productIDGenerator);

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


    public static void populateInventory(InventoryManager inventoryManager, ProductIDGenerator productIDGenerator) {
        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Lumen Pack", "LUM", 50.00),
                new StockInfo(1000, 100)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Colorful Sky", "CSK", 80.00),
                new StockInfo(30, 50)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Echoes of Time", "EOT", 120.00),
                new StockInfo(0, 30)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Chromatic Dreams", "CDM", 200.00),
                new StockInfo(300, 20)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Prismatic Night", "PNG", 150.00),
                new StockInfo(400, 40)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Eternal Voyage", "EVG", 180.00),
                new StockInfo(350, 35)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Luminous Path", "LMP", 75.00),
                new StockInfo(600, 60)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Silent Harmony", "SHY", 90.00),
                new StockInfo(500, 50)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Aurora Prism", "APR", 110.00),
                new StockInfo(450, 500)
        ));

        inventoryManager.addProduct(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Celestial Pulse", "CPX", 130.00),
                new StockInfo(400, 500)
        ));
    }
}
