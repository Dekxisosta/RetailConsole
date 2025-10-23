package core.domain.sales;

import common.util.*;
import core.domain.events.*;
import core.domain.sales.model.*;
import core.shared.datastructures.*;
import core.shared.dto.*;

public class SalesManager implements Event.Listener<ProductDTO>  {
    private final StockReductionEvent<ProductDTO> stockReductionEvent;
    private final LinkedList<ProductTotals> totalsList;
    private final LinkedList<SalesRecord> recordsList;

    public SalesManager(StockReductionEvent<ProductDTO> stockReductionEvent,
                        LinkedList<ProductTotals> totalsList,
                        LinkedList<SalesRecord> salesRecordList) {
        this.stockReductionEvent = stockReductionEvent;
        this.totalsList = totalsList;
        this.recordsList = salesRecordList;
    }

    @Override
    public void handle(ProductDTO product) {
        if(!totalsList.contains(product.getId())) {
            totalsList.add(new ProductTotals(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
            ));
        }else{
            ProductTotals productToUpdate = totalsList.get(product.getId());
            productToUpdate.setName(product.getName());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setStock(product.getStock());
        }
    }

    public void fireStockReductionEvent(ProductTotals productTotals) {
        stockReductionEvent.fire(convertProductToDTO(productTotals));
    }


    public void addSalesRecord(SalesRecord salesRecord) {
        recordsList.add(salesRecord);
    }

    public ProductTotals findProductByID(String id){
        try{
            return totalsList.get(id);
        }catch(LinkedList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }

    public SalesRecord findSalesRecordByID(String id){
        try{
            return recordsList.get(id);
        }catch(LinkedList.ListException e){
            Logger.log(e, Logger.Severity.NOTICE);
            return null;
        }
    }


    public LinkedList<ProductTotals> getTotalsList() {
        if(totalsList.isEmpty()){
            Logger.log("Empty List",
                    "Cannot provide any information because list is empty.",
                    Logger.Level.NOTICE);
            return null;
        }

        return totalsList;
    }

    public LinkedList<SalesRecord> getRecordsList() {
        if(recordsList.isEmpty()){
            Logger.log("Empty List",
                    "Cannot provide any information because list is empty.",
                    Logger.Level.NOTICE);
            return null;
        }

        return recordsList;
    }

    private ProductDTO convertProductToDTO(ProductTotals productTotals){
        return new ProductDTO(
                productTotals.getId(),
                productTotals.getName(),
                productTotals.getPrice(),
                productTotals.getStock()
        );
    }
}
