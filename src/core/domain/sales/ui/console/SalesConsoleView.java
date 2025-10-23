package core.domain.sales.ui.console;

import core.domain.sales.model.*;
import core.shared.datastructures.*;
import core.shared.ui.console.*;

public class SalesConsoleView extends ConsoleRenderer {
    /**
     * Public constructor, needs a builder for
     * formats and stylized outputs
     *
     * @param builder
     */
    public SalesConsoleView(ConsoleBuilder builder) {
        super(builder);
    }

    public void showTotals(LinkedList<ProductTotals> totals){
        if(totals == null) return;
        showTotalsHeader();
        System.out.print(totals.toStringReverse());
    }

    public void showSalesRecords(LinkedList<SalesRecord> recordList){
        if (recordList == null) return;
        showRecordsHeader();
        System.out.print(recordList);
    }
    public void showSalesRecord(SalesRecord salesRecord){
        if (salesRecord == null) return;
        System.out.print(salesRecord.toStringWithList());
    }


    private void showTotalsHeader() {
        System.out.print(
                String.format("""
                PRODUCT ID | PRODUCT NAME         | PRICE      | STOCK | TOTAL SALES | QTY SOLD
                -------------------------------------------------------------------------------------
                """,
                "ID", "Name", "Price", "Stock", "Total Sales", "Sold"));
    }
    private void showRecordsHeader(){
        System.out.print(
                String.format("""
                PRODUCT ID | TOTAL SALES | QTY SOLD
                -----------------------------------------
                """));
    }
}
