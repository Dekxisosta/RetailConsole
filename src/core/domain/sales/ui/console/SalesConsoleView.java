package core.domain.sales.ui.console;

import common.util.*;
import config.*;
import core.domain.sales.model.*;
import core.shared.datastructures.*;
import core.shared.ui.console.*;

public class SalesConsoleView extends ConsoleRenderer {
    public void showTotals(RecordList<ProductTotals> totals){
        if(totals == null) return;
        showTotalsHeader();
        System.out.print(totals.toStringReverse() + "\n");
    }

    public void showSalesRecords(RecordList<SalesRecord> recordList){
        if (recordList == null) return;
        showRecordsHeader();
        System.out.print("\n" + recordList + "\n");
    }

    public void showSalesRecord(SalesRecord salesRecord){
        if (salesRecord == null) return;
        System.out.print(salesRecord.toStringWithList());
    }

    public void showSummary(double totalSales,
                            ProductTotals mostSales,
                            ProductTotals mostStockSold){
        if (mostSales == null || mostStockSold == null) return;
        System.out.printf("""
                
                ===============================================
                  %s %s SALES SUMMARY REPORT %s
                -----------------------------------------------
                %-30s | %-20s %-20.2f
                %-30s | %-20s %-20.2f
                %-30s | %-20s %d units
                -----------------------------------------------
                
                """,
                Ansi.Color.YELLOW.code() + Ansi.Format.REVERSE.code(),
                AppConfig.PROGRAM_NAME,
                Ansi.Format.RESET.code(),
                "TOTAL SALES",
                "",
                totalSales,
                "PRODUCT WITH MOST SALES",
                mostSales.getName(),
                mostSales.getTotalSales(),
                "PRODUCT WITH MOST STOCK SOLD",
                mostStockSold.getName(),
                mostStockSold.getTotalStockSold()
        );
    }

    private void showTotalsHeader() {
        System.out.print(
                String.format("""
                _____________________________________________________________________________________
                PRODUCT ID | PRODUCT NAME         | PRICE      | STOCK | TOTAL SALES | QTY SOLD
                -------------------------------------------------------------------------------------
                """,
                "ID", "Name", "Price", "Stock", "Total Sales", "Sold"));
    }
    private void showRecordsHeader(){
        System.out.print("""
                ___________________________________________________
                PRODUCT ID  | TOTAL SALES               | QTY SOLD
                ---------------------------------------------------
                """);
    }
}
