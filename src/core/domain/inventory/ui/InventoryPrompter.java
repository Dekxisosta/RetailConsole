package core.domain.inventory.ui;

import core.domain.inventory.model.*;
import core.domain.inventory.util.*;
import core.shared.ui.console.*;

import java.io.*;

public class InventoryPrompter extends ConsolePrompter {
    private ProductIDGenerator idGenerator;
    /**
     * Public constructor for ConsolePrompter. In this project, to remove
     * the necessity of creating apis, interfaces or abstractions
     * for contracted methods, this reader class is only limited to
     * console
     * <p>
     * If this project is intended to scale further to a GUI implementation,
     * then simply replace the console prompter attached to the catch statement
     * of the wrapper utility at the bottommost part of the code
     *
     * @param reader used for scanning inputs
     * @see BufferedReader
     */
    public InventoryPrompter(BufferedReader reader,
                             ProductIDGenerator idGenerator) {
        super(reader);
        this.idGenerator = idGenerator;
    }

    public Product getProduct(){
        String id = idGenerator.generateID();

        String name = getString("product name");
        String manufacturer = getString("product manufacturer");
        double price = getDouble("product price");
        ProductInfo productInfo = new ProductInfo(name, manufacturer, price);

        int stock = getInt("product stock");
        int reorderPoint = getInt("product reorder point");
        StockInfo stockInfo = new StockInfo(stock, reorderPoint);

        return new Product(id, productInfo, stockInfo);
    }
}
