package core.domain.sales.model;

import common.util.*;
import core.domain.api.model.Record;
import core.shared.datastructures.*;

/**
 * Represents a single sales record or transaction entry.
 *
 * A {@code SalesRecord} contains all {@link SalesItem}s involved in a particular sale,
 * along with computed totals such as the overall sales amount and total stock sold.
 *
 * This class acts as an aggregate for a list of products sold in one transaction.
 * It provides methods to add new items and generate formatted text output suitable
 * for console display.
 */
public class SalesRecord implements Record {
    private final String id;
    private final RecordList<SalesItem> productList;
    private double totalSales;
    private int totalStockSold;

    /**
     * Constructs a new {@code SalesRecord}.
     *
     * @param id           the unique identifier of this sales record
     * @param productList  the list that stores all {@link SalesItem}s under this record
     */
    public SalesRecord(String id,
                       RecordList<SalesItem> productList) {
        this.id = id;
        this.productList = productList;
    }

    /**
     * Records a sale by adding a new {@link SalesItem} to the list
     * and updating total sales and stock sold accordingly.
     *
     * @param salesItem the sales item being recorded
     */
    public void recordSale(SalesItem salesItem){
        productList.add(salesItem);
        totalSales += salesItem.getTotalPrice();
        totalStockSold += salesItem.getQuantity();
    }

    /** @return the unique sale ID */
    public String getId() { return id; }

    /** @return {@code true} if no items have been recorded, otherwise {@code false} */
    public boolean isEmpty(){ return productList.isEmpty(); }

    /**
     * Returns a detailed string representation of the sales record,
     * including a breakdown of all items sold.
     *
     * @return a formatted multi-line string with all sale details
     */
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

    /**
     * Returns a short summary representation of the sales record,
     * showing only the sale ID and total statistics.
     *
     * @return a single-line summary string of this sale
     */
    @Override
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
