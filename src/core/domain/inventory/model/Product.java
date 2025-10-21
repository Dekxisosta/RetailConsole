package core.domain.inventory.model;

import common.util.*;
import core.domain.api.model.Record;

/**
 * This class is the model for the product. It contains stock information,
 * and product information
 */
public class Product implements Record {
    private final String id;
    private final ProductInfo productInfo;
    private final StockInfo stockInfo;

    /**
     * Public constructor for product model
     * @param productInfo product information
     * @param stockInfo stock information
     */
    public Product(String id, ProductInfo  productInfo, StockInfo stockInfo) {
        this.id = id;
        this.productInfo = productInfo;
        this.stockInfo = stockInfo;
    }

    // GETTERS
    public String getId() {return id;}
    public ProductInfo getProductInfo() {return productInfo;}
    public StockInfo getStockInfo() {return stockInfo;}

    @Override
    public String toString(){
        return String.format("""
                %s %-9s %s| %-45s | %-30s
                """,
                Ansi.Color.DIM_WHITE.code() + Ansi.Format.REVERSE.code(),
                id,
                Ansi.Format.RESET.code(),
                productInfo.toString(),
                stockInfo.toString()
                );
    }
}



