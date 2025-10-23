package core.domain.sales.model;

import common.util.*;
import core.domain.api.model.Record;

public class SalesItem implements Record {
    private final String id;
    private final String name;
    private final double price;
    private final int quantity;
    private final double totalPrice;

    public SalesItem(String id,
                     String name,
                     double price,
                     int quantity,
                     double totalPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // GETTERS
    public String getId() {return id;}
    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getQuantity() {return quantity;}
    public double getTotalPrice() {return totalPrice;}

    @Override
    public String toString() {
        return String.format(" %s %-10s %s| %-25s | %-10.2f | %-5d | %-10.2f%n",
                Ansi.Color.DIM_WHITE.code() + Ansi.Format.REVERSE.code(),
                id,
                Ansi.Format.RESET.code(),
                name,
                price,
                quantity,
                totalPrice);
    }
}
