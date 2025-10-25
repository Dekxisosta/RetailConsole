package core.api.dto;

import common.util.*;
import core.domain.api.model.Record;

/**
 * Data transfer object for product. Lightweight container that
 * contains all relevant details for any module that needs it
 */
public class ProductDTO implements Record {
    private final String id;
    private final String name;
    private final double price;
    private final int stock;

    public ProductDTO(String id,
                      String name,
                      double price,
                      int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // GETTERS
    public String getId() {return id;}
    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getStock() {return stock;}

    @Override
    public String toString() {
        return String.format("""
                    %s %-9s %s| %-45s
                    """,
                    Ansi.Color.DIM_WHITE.code() + Ansi.Format.REVERSE.code(),
                    id,
                    Ansi.Format.RESET.code(),
                    name
        );
    }
}
