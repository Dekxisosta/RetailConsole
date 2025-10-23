package core.domain.analytics;

import core.shared.datastructures.*;
import core.shared.dto.*;
import core.shared.ui.console.*;

public class AnalyticsConsoleView extends ConsoleRenderer {
    public void showTotalProducts(int totalProducts) {
        System.out.println("Total Products: " + totalProducts);
    }

    public void showTotalRevenue(double totalSales) {
        System.out.printf("Total Sales Revenue: %.2f%n", totalSales);
    }

    public void showTopSellingProduct(ProductDTO product) {
        if (product == null) return;
        System.out.println("Product with Highest Total Sales:");
        System.out.printf("%-10s | %-20s | %-10s | %-5s%n", "ID", "Name", "Price", "Stock");
        System.out.printf("%-10s | %-20s | %-10.2f | %-5d%n",
                product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

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
