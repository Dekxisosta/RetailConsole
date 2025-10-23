package core.domain.analytics;

import core.shared.datastructures.*;
import core.shared.dto.*;
import core.shared.ui.console.*;

/**
 * Presentation layer of the analytics module
 */
public class AnalyticsConsoleView extends ConsoleRenderer {

    /**
     * Shows the total number of products in the inventory module
     * @param totalProducts the number of available stock in the inventory module
     */
    public void showTotalProducts(int totalProducts) {
        System.out.println("Total Products: " + totalProducts);
    }

    /**
     * Shows the total revenue garnered by the sales records in the sales module
     * @param totalSales the overall amount of money garnered in the sales module
     */
    public void showTotalRevenue(double totalSales) {
        System.out.printf("Total Sales Revenue: %.2f%n", totalSales);
    }

    /**
     * Shows details about the top-selling product in the sales module.
     * If the dto is non-existent, then does nothing
     * @param product the product data transfer object that holds all
     *                relevant data of the top-selling product
     */
    public void showTopSellingProduct(ProductDTO product) {
        if (product == null) return;
        System.out.println("Product with Highest Total Sales:");
        System.out.printf("%-10s | %-20s | %-10s | %-5s%n", "ID", "Name", "Price", "Stock");
        System.out.printf("%-10s | %-20s | %-10.2f | %-5d%n",
                product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    /**
     * Shows a list of products that are currently out of stock in the
     * inventory module. If the list contains nothing, then does nothing
     * @param products list of products that are out of stock
     */
    public void showOutOfStock(RecordList<ProductDTO> products) {
        if (products == null || products.isEmpty()) return;
        System.out.printf("""
                ___________________________________
                  Out of Stock Products
                -----------------------------------
                %s
                """,
                products.toStringReverse());
    }
}
