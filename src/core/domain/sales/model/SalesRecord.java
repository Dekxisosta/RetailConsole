package core.domain.sales.model;

import common.util.*;
import core.domain.api.model.Record;
import core.shared.datastructures.*;

public class SalesRecord implements Record {
    private final String id;
    private final LinkedList<ProductSale> productList;
    private final double totalSales;
    private final int totalStockSold;

    public SalesRecord(String id,
                       LinkedList<ProductSale> productList,
                       double totalSales,
                       int totalStockSold) {
        this.id = id;
        this.productList = productList;
        this.totalSales = totalSales;
        this.totalStockSold = totalStockSold;
    }

    public String getId() {return id;}

    public String toString(){
        return String.format("""
                %s %-9s %s| %-10.2f | %-10s
                """,
                Ansi.Color.DIM_WHITE.code() + Ansi.Format.REVERSE.code(),
                id,
                Ansi.Format.RESET.code(),
                totalSales,
                totalStockSold
        );
    }
}
