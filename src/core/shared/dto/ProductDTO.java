package core.shared.dto;

import core.domain.api.model.Record;

public class ProductDTO implements Record {
    private final String id;
    private final String name;
    private final double price;

    public ProductDTO(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // GETTERS
    public String getId() {return id;}
    public String getName() {return name;}
    public double getPrice() {return price;}
}
