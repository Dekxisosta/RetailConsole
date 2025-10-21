package core.domain.inventory.model;

/**
 * This class is a segment of the product model. This holds all relevant
 * information about the product
 */
public class ProductInfo {
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
