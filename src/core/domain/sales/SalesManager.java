package core.domain.sales;

import common.util.*;
import core.domain.bindables.*;
import core.domain.sales.datastructures.*;
import core.domain.sales.model.*;
import core.shared.datastructures.*;
import core.shared.dto.*;


public class SalesManager{
    private final SalesList<ProductTotals> totalsList;
    private final RecordList<SalesRecord> recordsList;

    public SalesManager(SalesList<ProductTotals> totalsList,
                        RecordList<SalesRecord> salesRecordList) {
        Events.ProductAdded.addListener(this::handleProductAdded);
        Events.ProductUpdated.addListener(this::handleProductUpdated);
        Requests.TotalSales.setSupplier(this::getTotalSales);
        Requests.TopSellingProduct.setSupplier(()-> {
            ProductTotals productToExport = getProductWithMostSales();
            return (productToExport!=null)? convertProductToDTO(productToExport): null;
        });
        this.totalsList = totalsList;
        this.recordsList = salesRecordList;
    }

    public void handleProductAdded(Object o) {
        if(!(o instanceof ProductDTO)) return;
        ProductDTO productDTO = (ProductDTO) o;

        if(!totalsList.contains(productDTO.getId()))
            totalsList.add(convertDTOToProduct(productDTO));
    }

    public void handleProductUpdated(Object o){
        if(!(o instanceof ProductDTO)) return;
        ProductDTO productDTO = (ProductDTO) o;
        updateProductDetails(productDTO);
    }

    public void fireStockReductionEvent(ProductTotals productTotals) {
        Events.StockReduction.fire(convertProductToDTO(productTotals));
    }


    public void addSalesRecord(SalesRecord salesRecord) {
        recordsList.add(salesRecord);
    }

    public ProductTotals findProductByID(String id){
        try{
            return totalsList.get(id);
        }catch(RecordList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }

    public SalesRecord findSalesRecordByID(String id){
        try{
            return recordsList.get(id);
        }catch(RecordList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }


    public RecordList<ProductTotals> getTotalsList() {
        if(totalsList.isEmpty()){
            Logger.log("Empty List",
                    "Cannot provide any information because list is empty.",
                    Logger.Level.NOTICE);
            return null;
        }

        return totalsList;
    }

    public RecordList<SalesRecord> getRecordsList() {
        if(recordsList.isEmpty()){
            Logger.log("Empty List",
                    "Cannot provide any information because list is empty.",
                    Logger.Level.NOTICE);
            return null;
        }

        return recordsList;
    }
    public double getTotalSales(){
        try{
            return totalsList.getTotalSales();
        }catch(RecordList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return 0;
        }
    }

    public ProductTotals getProductWithMostSales(){
        try{
            return totalsList.getProductWithMostSales();
        }catch(RecordList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }

    public ProductTotals getProductWithMostStockSold(){
        try{
            return totalsList.getProductWithMostStockSold();
        }catch(RecordList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }

    private ProductDTO convertProductToDTO(ProductTotals productTotals){
        try{
            return new ProductDTO(
                    productTotals.getId(),
                    productTotals.getName(),
                    productTotals.getPrice(),
                    productTotals.getStock()
            );
        }catch(RecordList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }

    }

    private ProductTotals convertDTOToProduct(ProductDTO product){
        return new ProductTotals(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
    private void updateProductDetails(ProductDTO product){
        ProductTotals productToUpdate = totalsList.get(product.getId());
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setStock(product.getStock());
    }
}
