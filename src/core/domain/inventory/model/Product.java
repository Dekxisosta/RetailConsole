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

    /**
     * This class is a segment of the product model. This holds all relevant
     * information about the product
     */
    public static class ProductInfo {
        private String name;
        private String manufacturer;
        private double price;

        /**
         * Public constructor for product information
         * @param name product's name
         * @param price product's price
         */
        public ProductInfo(String name, String manufacturer, double price) {
            this.name = name;
            this.manufacturer = manufacturer;
            this.price = price;
        }

        // GETTERS
        public String getName() {return name;}
        public String getManufacturer() {return manufacturer;}
        public double getPrice() {return price;}

        // SETTERS
        public void setName(String name) {this.name = name;}
        public void setManufacturer(String manufacturer) {this.manufacturer = manufacturer;}
        public void setPrice(double price) {this.price = price;}

        @Override
        public String toString(){
            return String.format("%-20s | %-12s | %-5.2f",  name, manufacturer, price);
        }
    }

    /**
     * This class is a segment of the product model. This holds all relevant
     * information about the product relating to stock
     */
    public static class StockInfo {
        private int availableStock;
        private int reorderPoint;

        public enum Status{
            OUT_OF_STOCK(Ansi.Color.RED),
            LOW_ON_STOCK(Ansi.Color.ORANGE),
            AVAILABLE(Ansi.Color.GREEN);

            private Ansi.Color color;
            Status(Ansi.Color color){this.color = color;}
            public Ansi.Color getColor(){return color;}
        }

        public StockInfo(int availableStock, int reorderPoint) {
            this.availableStock = availableStock;
            this.reorderPoint = reorderPoint;
        }

        public Status getStatus() {
            return availableStock == 0
                    ? Status.OUT_OF_STOCK
                    : (availableStock > reorderPoint ? Status.AVAILABLE
                    : Status.LOW_ON_STOCK);
        }
        public int getAvailableStock() {return availableStock;}
        public int getReorderPoint() {return reorderPoint;}

        public void setAvailableStock(int availableStock) {this.availableStock = availableStock;}
        public void setReorderPoint(int reorderPoint) {this.reorderPoint = reorderPoint;}

        @Override
        public String toString(){
            Status status = getStatus();
            return String.format("""
                    %-7d | %-5d | %s %-13s %s
                    """,
                    availableStock,
                    reorderPoint,
                    status.getColor().code() + Ansi.Format.REVERSE.code(),
                    status.name(),
                    Ansi.Format.RESET.code()

            );
        }
    }
}



