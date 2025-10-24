package core.domain.bindables;

import core.shared.datastructures.*;
import core.shared.dto.*;

/**
 * A lightweight request service similar to Roblox's BindableFunction.
 * Allows registering a Supplier that processes requests and returns responses.
 * Acts like {@link java.util.function.Supplier} to foster modularized approach
 *
 * @version 1.0
 * @see Request
 */
public final class Requests {
    // List of all requests available in the system
    public static final Request.TotalProductsRequest TotalProducts = new Request.TotalProductsRequest();
    public static final Request.TotalSalesRequest TotalSales = new Request.TotalSalesRequest();
    public static final Request.TopSellingProductRequest TopSellingProduct = new Request.TopSellingProductRequest();
    public static final Request.OutOfStockRequest OutOfStock = new Request.OutOfStockRequest();

    private Requests() {} // prevent instantiation
}

/**
 * Request class that defines a Request's functions
 * @param <T>
 */
class Request<T> {
    public static class OutOfStockRequest<T extends RecordList<ProductDTO>> extends Request<RecordList<ProductDTO>> {}
    public static class TopSellingProductRequest<T extends String> extends Request<String> {}
    public static class TotalProductsRequest<T extends Integer> extends Request<Integer> {}
    public static class TotalSalesRequest<T extends Number> extends Request<Number> {}

    private Supplier<T> supplier;

    /**
     * Functional Interface that defines how a query handles a request.
     *
     * @param <T> the input type
     */
    @FunctionalInterface
    public interface Supplier<T> {
        T get();
    }

    /**
     * Default constructor — creates a query with no handler set.
     */
    public Request() {
        this.supplier = null;
    }

    /**
     * Sets or replaces the current query handler.
     *
     * @param supplier the handler function to handle requests
     */
    public void setSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Executes the query and returns the result from the handler.
     *
     * @return the handler's response
     * @throws IllegalStateException if no handler has been assigned
     */
    public T request() {
        if (supplier == null) {
            throw new IllegalStateException("No handler assigned for this query.");
        }
        return supplier.get();
    }
}
