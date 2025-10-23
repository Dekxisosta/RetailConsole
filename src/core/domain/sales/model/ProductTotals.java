package core.domain.sales.model;

import common.util.*;
import core.domain.api.model.Record;

public class ProductTotals implements Record {
    private final String id;
    private String name;
    private double price;
    private int stock;
    private double totalSales;
    private int totalStockSold;

    public ProductTotals(String id,
                         String name,
                         double price,
                         int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        totalSales = 0;
        totalStockSold = 0;
    }

    // GETTERS
    public String getId() {return id;}
    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getStock() {return stock;}

    // SETTERS
    public void setName(String name){this.name=name;}
    public void setPrice(double price){this.price=price;}
    public void setStock(int stock){this.stock=stock;}

    // MUTATORS
    public void recordPurchase(int stockSold){
        if(stockSold<1) throw new IllegalArgumentException("Stock sold must at least be 1");
        totalSales += price * stockSold;
        totalStockSold += stockSold;
        stock-=stockSold;
    }
    public void voidPurchase(int stockSold, double sales){
        totalStockSold -= stockSold;
        totalSales -= sales;
    }

    @Override
    public String toString() {
        return String.format("%n%s %-9s %s| %-20s | %-10.2f | %-5d | %-11.2f | %-5d%n",
                Ansi.Color.DIM_WHITE.code() + Ansi.Format.REVERSE.code(),
                id,
                Ansi.Format.RESET.code(),
                name,
                price,
                stock,
                totalSales,
                totalStockSold);
    }
}
