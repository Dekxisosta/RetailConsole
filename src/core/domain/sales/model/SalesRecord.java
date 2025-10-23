package core.domain.sales.model;

import common.util.*;
import core.domain.api.model.Record;
import core.shared.datastructures.*;

public class SalesRecord implements Record {
    private final String id;
    private final LinkedList<SalesItem> productList;
    private double totalSales;
    private int totalStockSold;

    public SalesRecord(String id,
                       LinkedList<SalesItem> productList) {
        this.id = id;
        this.productList = productList;
    }
    public void add(SalesItem salesItem){productList.add(salesItem);}
    public String getId() {return id;}
    public boolean isEmpty(){return productList.isEmpty();}
    public void incrementTotalSales(double sales){totalSales += sales;}
    public void incrementTotalStockSold(int stockSold) {totalStockSold += stockSold;}


    public String toStringWithList(){
        return String.format("""
                         ____________________________________________________________________
                        | SALE ID    | TOTAL SALES               | TOTAL STOCK SOLD          |         
                        |--------------------------------------------------------------------|
                        |%s %-10s %s| %-25.2f | %-25d |
                        +--------------------------------------------------------------------+
                         PRODUCT ID  | PRODUCT NAME              | PRICE      | QTY   | TOTAL             
                         --------------------------------------------------------------------
                        %s
                        """,
                Ansi.Color.PINK.code() + Ansi.Format.REVERSE.code(),
                id,
                Ansi.Format.RESET.code(),
                totalSales,
                totalStockSold,
                productList.toString()
        );
    }

    public String toString(){
        return String.format("""
                %s %-10s %s| %-25.2f | %-25d 
                """,
                Ansi.Color.PINK.code() + Ansi.Format.REVERSE.code(),
                id,
                Ansi.Format.RESET.code(),
                totalSales,
                totalStockSold
        );
    }
}