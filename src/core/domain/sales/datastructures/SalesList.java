package core.domain.sales.datastructures;

import core.domain.api.datastructures.*;
import core.domain.sales.model.*;

public class SalesList <T extends ProductTotals> extends RecordList<T> {
    public double getTotalSales(){
        double totalSales = 0;
        ListNode<T> current = head;
        while(current!=null){
            totalSales += current.getData().getTotalSales();
            current = current.getNext();
        }

        return totalSales;
    }

    public ProductTotals getProductWithMostSales(){
        ProductTotals mostSales= head.getData();
        ListNode<T> current = head.getNext();
        while(current!=null){
            if(current.getData().getTotalSales()>mostSales.getTotalSales())
                mostSales=current.getData();

            current = current.getNext();
        }
        if(mostSales.getTotalSales()==0)
            throw new ListException("Nothing is sold yet. Unable to get product with most sales");

        return mostSales;
    }

    public ProductTotals getProductWithMostStockSold(){
        ProductTotals mostSold=head.getData();
        ListNode<T> current = head.getNext();
        while(current!=null){
            if(current.getData().getTotalStockSold()>mostSold.getTotalStockSold())
                mostSold=current.getData();

            current = current.getNext();
        }

        if(mostSold.getTotalStockSold()==0)
            throw new ListException("Nothing is sold yet. Unable to get product with most stock sold");

        return mostSold;
    }
}
