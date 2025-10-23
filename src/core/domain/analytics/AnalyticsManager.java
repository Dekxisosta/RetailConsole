package core.domain.analytics;

import core.domain.bindables.*;
import core.shared.datastructures.*;
import core.shared.dto.*;

public final class AnalyticsManager {
    public double getTotalSales(){
        return (double) Requests.TotalSales.request();
    }
    public RecordList<ProductDTO> getOutOfStockProducts(){
        return (RecordList<ProductDTO>) Requests.OutOfStock.request();
    }
    public Integer getTotalProducts(){
        return (Integer) Requests.TotalProducts.request();
    }
    public ProductDTO getTopSelling(){
        return (ProductDTO) Requests.TopSellingProduct.request();
    }
}
