package core.domain.sales.ui.console;

import common.util.*;
import config.*;
import core.domain.api.datastructures.*;
import core.domain.sales.model.*;
import core.shared.ui.console.*;

/**
 * Handles all console-based rendering for the sales module.
 * <p>
 * {@code SalesConsoleView} extends {@link ConsoleRenderer} and is responsible
 * for displaying sales-related data in a structured and readable format,
 * including product totals, individual sales records, and summary reports.
 * <p>
 * This class focuses solely on presentation — it does not perform any
 * business logic or user input handling.
 *
 * @see SalesConsolePrompter
 * @see SalesRecord
 * @see ProductTotals
 */
public class SalesConsoleView extends ConsoleRenderer {

    /**
     * Displays a formatted list of product totals.
     * Each row contains product information along with
     * its remaining stock, total sales value, and total stock sold.
     *
     * @param totals the list of {@link ProductTotals} to display
     */
    public void showTotals(RecordList<ProductTotals> totals){
        if (totals == null) return;
        showTotalsHeader();
        System.out.print(totals.toStringReverse() + "\n");
    }

    /**
     * Displays a table of all recorded sales.
     *
     * @param recordList the list of {@link SalesRecord}s to display
     */
    public void showSalesRecords(RecordList<SalesRecord> recordList){
        if (recordList == null) return;
        showRecordsHeader();
        System.out.print("\n" + recordList + "\n");
    }

    /**
     * Displays a detailed view of a specific {@link SalesRecord},
     * including the list of items sold within that transaction.
     *
     * @param salesRecord the sales record to display
     */
    public void showSalesRecord(SalesRecord salesRecord){
        if (salesRecord == null) return;
        System.out.print(salesRecord.toStringWithList());
    }

    /**
     * Displays a formatted summary report for the current sales data.
     * Includes total sales, product with the highest sales value,
     * and product with the most stock sold.
     *
     * @param totalSales total amount of sales recorded
     * @param mostSales the product that generated the highest total sales
     * @param mostStockSold the product with the most units sold
     */
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
                "TOTAL SALES", "", totalSales,
                "PRODUCT WITH MOST SALES", mostSales.getName(), mostSales.getTotalSales(),
                "PRODUCT WITH MOST STOCK SOLD", mostStockSold.getName(), mostStockSold.getTotalStockSold()
        );
    }

    /** Prints the header for the product totals table. */
    private void showTotalsHeader() {
        System.out.print("""
                _____________________________________________________________________________________
                PRODUCT ID | PRODUCT NAME         | PRICE      | STOCK | TOTAL SALES | QTY SOLD
                -------------------------------------------------------------------------------------
                """);
    }

    /** Prints the header for the sales record table. */
    private void showRecordsHeader(){
        System.out.print("""
                ___________________________________________________
                PRODUCT ID  | TOTAL SALES               | QTY SOLD
                ---------------------------------------------------
                """);
    }
}
