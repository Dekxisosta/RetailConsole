package core.domain.sales;

import common.datastructures.*;
import common.dto.*;

public class SalesRecord {
    private LinkedList<ProductDTO> productList;
    private float totalSales;
    private int totalStockSold;

    public SalesRecord(){
        this.productList = new LinkedList<>();
    }

    public float getTotalSales() {
        return totalSales;
    }

    int getTotalStockSold() {
        return totalStockSold;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public void setTotalStockSold(int totalStockSold) {
        this.totalStockSold = totalStockSold;
    }
}
