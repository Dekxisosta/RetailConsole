package core.domain.analytics.manager;

import core.domain.bindables.*;
import core.shared.datastructures.*;
import core.shared.dto.*;

/**
 * Manager class for the analytics module.
 * For now, since the reports and analytics module merely
 * presents data already known in other modules, it just requests
 * the data to be supplied
 *
 * If however, the analytics module might compare trends in the
 * future, then a service class as well as a more extensive use of
 * methods could be implemented
 */
public final class AnalyticsManager {
    /**
     * Requests to see the total sales revenue
     * from the set supplier of the below request, which in this case
     * is the manager of the sales module
     * @return a double reflective of the total sales
     */
    public double getTotalSales(){
        return (double) Requests.TotalSales.request();
    }

    /**
     * Requests a list of out of stock products
     * from the manager of the sales module
     * @return a RecordList containing out of stock products
     */
    public RecordList<ProductDTO> getOutOfStockProducts(){
        return (RecordList<ProductDTO>) Requests.OutOfStock.request();
    }

    /**
     * Requests to see the total available stock in
     * the inventory module. The term, total products, is quite misleading
     * in the given program brief. The group decided it signifies the
     * number of available stock instead of showing a list of all products, etc.
     *
     * @return an Integer object of the number of available stock
     */
    public Integer getTotalProducts(){
        return (Integer) Requests.TotalProducts.request();
    }

    /**
     * Requests to see the top-selling product from the
     * manager of the sales module
     * @return Product DTO that holds lightweight data about
     *         top-selling product
     */
    public ProductDTO getTopSelling(){
        return (ProductDTO) Requests.TopSellingProduct.request();
    }
}
