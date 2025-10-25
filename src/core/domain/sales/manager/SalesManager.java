package core.domain.sales.manager;

import common.util.*;
import core.api.dto.*;
import core.domain.api.datastructures.*;
import core.domain.sales.datastructures.*;
import core.domain.sales.model.*;
import core.shared.events.*;
import core.shared.requests.*;

import java.io.*;

/**
 * The {@code SalesManager} serves as the central coordinator for the Sales domain.
 * <p>
 * It bridges data between the product and sales modules, listens to product-related
 * domain events, and exposes high-level request operations for analytics and reporting.
 * </p>
 *
 * <p>Primary responsibilities:</p>
 * <ul>
 *     <li>React to product-related events (add, update, remove).</li>
 *     <li>Maintain and update cumulative sales data.</li>
 *     <li>Provide sales analytics to external modules via {@link Requests}.</li>
 * </ul>
 */
public final class SalesManager {
    private final SalesList<ProductTotals> totalsList;
    private final RecordList<SalesRecord> recordsList;

    /**
     * Constructs a new {@link SalesManager} instance.
     * <p>
     * During initialization:
     * <ul>
     *     <li>Automatically subscribes to product lifecycle events.</li>
     *     <li>Registers request suppliers for total sales and top-selling products.</li>
     * </ul>
     *
     * @param totalsList      The list containing cumulative product totals.
     * @param salesRecordList The list containing all recorded sales transactions.
     */
    public SalesManager(SalesList<ProductTotals> totalsList,
                        RecordList<SalesRecord> salesRecordList) {
        // Subscribe to domain events
        Events.ProductRemoved.addListener(this::handleProductRemoved);
        Events.ProductAdded.addListener(this::handleProductAdded);
        Events.ProductUpdated.addListener(this::handleProductUpdated);

        // Register suppliers for external requests
        Requests.TotalSales.setSupplier(this::exportTotalSales);
        Requests.TopSellingProduct.setSupplier(this::exportTopSellingProductDTO);

        this.totalsList = totalsList;
        this.recordsList = salesRecordList;
    }

    /* =============================================
     *  EVENT HANDLERS / EVENT FIRES / REQUEST SUPPLIERS
     * ============================================*/

    /**
     * Handles product removal events.
     * <p>
     * When a product is removed, its remaining stock is set to zero
     * to prevent further sales from being recorded.
     *
     * @param o The event payload (expected to be a {@link ProductDTO}).
     */
    private void handleProductRemoved(Object o) {
        if (!(o instanceof ProductDTO productDTO)) return;
        totalsList.get(productDTO.getId()).setStock(0);
    }

    /**
     * Handles product addition events.
     * <p>
     * Creates a new {@link ProductTotals} entry from the provided product DTO.
     *
     * @param o The event payload (expected to be a {@link ProductDTO}).
     */
    private void handleProductAdded(Object o) {
        if (!(o instanceof ProductDTO productDTO)) return;
        totalsList.add(SalesConverter.convertDTOToProduct(productDTO));
    }

    /**
     * Handles product update events.
     * <p>
     * Updates the corresponding product record in the totals list
     * to reflect the most recent product data.
     *
     * @param o The event payload (expected to be a {@link ProductDTO}).
     */
    private void handleProductUpdated(Object o) {
        if (!(o instanceof ProductDTO productDTO)) return;
        updateProductDetails(productDTO);
    }

    /**
     * Exports total sales to requester
     * @return total sales amount
     */
    private double exportTotalSales(){
        return getTotalSales();
    }

    /**
     * Converts the top-selling product into a transferable DTO.
     * <p>
     * Used as a supplier for TopSellingProductRequest
     *
     * @return The top-selling product as a {@link ProductDTO}, or {@code null} if none exists.
     */
    private ProductDTO exportTopSellingProductDTO() {
        ProductTotals top = getProductWithMostSales();
        return (top != null) ? SalesConverter.convertProductToDTO(top) : null;
    }

    /**
     * Fires a stock reduction event.
     * <p>
     * Used when product stock is reduced due to a sale.
     * The event is consumed by subscribed listeners such as
     * product or analytics modules.
     *
     * @param productTotals The product whose stock has changed.
     */
    public void fireStockReductionEvent(ProductTotals productTotals) {
        Events.StockReduction.fire(SalesConverter.convertProductToDTO(productTotals));
    }
    /*============================================
     * SALES MANAGER PUBLIC APIs
     ============================================*/
    /**
     * Calculates the total accumulated sales value across all products.
     *
     * @return The computed total sales value.
     */
    public double getTotalSales() {
        return SafeSupplier.supply(totalsList::getTotalSales);
    }

    /**
     * Records a new sale in the system.
     *
     * @param salesRecord The sales record to add.
     */
    public void addSalesRecord(SalesRecord salesRecord) {
        recordsList.add(salesRecord);
    }

    /**
     * Finds a product by its unique ID.
     *
     * @param id The product ID.
     * @return The corresponding {@link ProductTotals} instance.
     * @throws IllegalStateException if the product cannot be found.
     */
    public ProductTotals findProductByID(String id) {
        return SafeSupplier.supply(() -> totalsList.get(id));
    }

    /**
     * Retrieves all cumulative product totals.
     *
     * @return The list of product totals.
     * @throws IllegalStateException if the list is empty.
     */
    public RecordList<ProductTotals> getTotalsList() {
        return SafeSupplier.supply(()->{
            if (totalsList.isEmpty())
                throw new IllegalStateException("Totals list is empty.");
            return totalsList;
        });
    }

    /**
     * Retrieves all recorded sales transactions.
     *
     * @return The list of recorded sales.
     * @throws IllegalStateException if the list is empty.
     */
    public RecordList<SalesRecord> getRecordsList() {
        return SafeSupplier.supply(()->{
            if (recordsList.isEmpty())
                throw new IllegalStateException("Sales records list is empty.");
            return recordsList;
        });

    }


    /**
     * Retrieves the product with the highest sales value.
     *
     * @return The product with the most total sales.
     */
    public ProductTotals getProductWithMostSales() {
        return SafeSupplier.supply(totalsList::getProductWithMostSales);
    }

    /**
     * Retrieves the product with the most stock sold overall.
     *
     * @return The product with the highest quantity sold.
     */
    public ProductTotals getProductWithMostStockSold() {
        return SafeSupplier.supply(totalsList::getProductWithMostStockSold);
    }
    /* ======================================================
     *  OTHER UTILITIES
     * =====================================================*/
    /**
     * Updates an existing product entry with new details.
     * <p>
     * Retains cumulative totals but synchronizes name, price, and stock.
     *
     * @param product The updated product data from the product module.
     */
    private void updateProductDetails(ProductDTO product) {
        ProductTotals totals = totalsList.get(product.getId());
        totals.setName(product.getName());
        totals.setPrice(product.getPrice());
        totals.setStock(product.getStock());
    }
}

/* ======================================================
 *  CONVERTER UTILITIES
 * =====================================================*/

/**
 * Utility class responsible for converting between
 * {@link ProductTotals} and {@link ProductDTO}.
 * <p>
 * Ensures that all conversions are safely executed using
 * {@link SafeSupplier} to handle internal exceptions gracefully.
 */
final class SalesConverter {

    /**
     * Converts a {@link ProductTotals} entity into a {@link ProductDTO}.
     *
     * @param productTotals The product totals entity.
     * @return The converted DTO.
     */
    static ProductDTO convertProductToDTO(ProductTotals productTotals) {
        return SafeSupplier.supply(() -> new ProductDTO(
                productTotals.getId(),
                productTotals.getName(),
                productTotals.getPrice(),
                productTotals.getStock()
        ));
    }

    /**
     * Converts a {@link ProductDTO} into a {@link ProductTotals} entity.
     *
     * @param product The product DTO.
     * @return The converted totals entity.
     */
    static ProductTotals convertDTOToProduct(ProductDTO product) {
        return SafeSupplier.supply(() -> new ProductTotals(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        ));
    }
}

/* ======================================================
 *  SAFE SUPPLIER WRAPPER
 * =====================================================*/

/**
 * Utility class that executes supplier operations safely,
 * logging and wrapping any runtime or I/O exceptions into
 * a unified {@link IllegalStateException}.
 * <p>
 * This helps maintain concise and consistent error handling
 * throughout the Sales module.
 */
final class SafeSupplier {
    @FunctionalInterface
    public interface Supplier<T> {
        T get() throws IOException, RuntimeException;
    }

    /**
     * Safely executes a supplier function.
     * <p>
     * Any thrown {@link RuntimeException} or {@link IOException} is caught,
     * logged, and rethrown as an {@link IllegalStateException}.
     *
     * @param supplier The supplier to execute.
     * @param <T>      The return type.
     * @return The supplied value.
     * @throws IllegalStateException if the supplier fails.
     */
    static <T> T supply(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (RuntimeException | IOException e) {
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }
}
