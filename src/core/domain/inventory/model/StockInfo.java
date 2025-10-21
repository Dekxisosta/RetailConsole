package core.domain.inventory.model;

import common.util.*;

/**
 * This class is a segment of the product model. This holds all relevant
 * information about the product relating to stock
 */
public class StockInfo {
    private int availableStock;
    private int reorderPoint;

    enum Status{
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
