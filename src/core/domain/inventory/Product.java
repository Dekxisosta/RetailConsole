package core.domain.inventory;

public class Product {
    private final String id;
    private String name;
    private double price;
    private int availableStock;
    private int reorderPoint;
    private Status status;

    enum Status{
        OUT_OF_STOCK,
        LOW_ON_STOCK,
        AVAILABLE
    }

    public static void main(String[] args){
    }
    public Product(String id,
                   String name,
                   double price,
                   int availableStock,
                   int reorderPoint) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.availableStock = availableStock;
        this.reorderPoint = reorderPoint;
        this.status = Status.OUT_OF_STOCK;

    }

    public Status getStatus() {
        return availableStock == 0
                ? Status.OUT_OF_STOCK
                : (availableStock > reorderPoint ? Status.AVAILABLE
                : Status.LOW_ON_STOCK);
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
}
