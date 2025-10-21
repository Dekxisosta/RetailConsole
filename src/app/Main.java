package app;

import common.util.*;
import core.domain.analytics.*;
import core.domain.inventory.controller.*;
import core.domain.inventory.datastructures.*;
import core.domain.inventory.manager.*;
import core.domain.inventory.model.*;
import core.domain.inventory.ui.*;
import core.domain.inventory.util.*;
import core.domain.sales.*;
import core.domain.sales.ui.*;
import core.domain.sales.util.*;
import core.shared.datastructures.*;
import core.shared.ui.console.*;

import java.io.*;

public class Main {
    public static void main(String[] args){
        // Reader Object
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // General Utilities
        ConsoleBuilder builder = new ConsoleBuilder();
        ConsoleRenderer renderer = new ConsoleRenderer(builder);
        ConsolePrompter prompter = new ConsolePrompter(reader);

        // ID Generators
        ProductIDGenerator productIDGenerator = new ProductIDGenerator();
        SalesRecordIDGenerator salesRecordIDGenerator = new SalesRecordIDGenerator();

        // Sales Module
        SalesPrompter salesPrompter = new SalesPrompter(reader, salesRecordIDGenerator);
        SalesView salesView = new SalesView(builder);
        SalesManager salesManager = new SalesManager();
        SalesController salesController = new SalesController(
                salesView,
                salesPrompter,
                salesManager
        );

        // Inventory Module
        InventoryList<Product> inventory = new InventoryList<>();
        populateInventory(inventory, productIDGenerator);
        InventoryManager inventoryManager = new InventoryManager(inventory);
        InventoryView inventoryView = new InventoryView(builder);
        InventoryPrompter inventoryPrompter = new InventoryPrompter(reader, productIDGenerator);
        InventoryUpdateController inventoryUpdateController= new InventoryUpdateController(
                inventoryManager,
                inventoryPrompter,
                inventoryView
        );
        InventoryController inventoryController = new InventoryController(
                        inventoryView,
                        inventoryPrompter,
                        inventoryManager,
                        inventoryUpdateController
        );

        // Analytics Module
        AnalyticsController analyticsController = new AnalyticsController();


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
    public static void populateInventory(LinkedList<Product> inventory, ProductIDGenerator productIDGenerator) {
        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Lumen Pack", "LUM", 50.00),
                new StockInfo(1000, 100)
        ));

        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Colorful Sky", "CSK", 80.00),
                new StockInfo(30, 50)
        ));

        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Echoes of Time", "EOT", 120.00),
                new StockInfo(0, 30)
        ));

        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Chromatic Dreams", "CDM", 200.00),
                new StockInfo(300, 20)
        ));

        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Prismatic Night", "PNG", 150.00),
                new StockInfo(400, 40)
        ));

        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Eternal Voyage", "EVG", 180.00),
                new StockInfo(350, 35)
        ));

        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Luminous Path", "LMP", 75.00),
                new StockInfo(600, 60)
        ));

        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Silent Harmony", "SHY", 90.00),
                new StockInfo(500, 50)
        ));

        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Aurora Prism", "APR", 110.00),
                new StockInfo(450, 500)
        ));

        inventory.add(new Product(
                productIDGenerator.generateID(),
                new ProductInfo("Celestial Pulse", "CPX", 130.00),
                new StockInfo(400, 500)
        ));
    }
}
