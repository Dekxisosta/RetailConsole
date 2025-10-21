package core.domain.sales.ui;

import core.domain.sales.model.*;
import core.domain.sales.util.*;
import core.shared.datastructures.*;
import core.shared.ui.console.*;

import java.io.*;

public class SalesPrompter extends ConsolePrompter {
    private final SalesRecordIDGenerator idGenerator;
    /**
     * Public constructor for ConsolePrompter. In this project, to remove
     * the necessity of creating apis, interfaces or abstractions
     * for contracted methods, this reader class is only limited to
     * console
     * <p>
     * If this project is intended to scale further to a GUI implementation,
     * then simply replace the console prompter attached to the catch statement
     * of the wrapper utility at the bottommost part of the code
     *
     * @param reader used for scanning inputs
     * @see BufferedReader
     */
    public SalesPrompter(BufferedReader reader, SalesRecordIDGenerator idGenerator) {
        super(reader);
        this.idGenerator = idGenerator;
    }

    public SalesRecord getSalesRecord(){
//        String id = idGenerator.generateID();
//        LinkedList<ProductSale> itemsSold = new LinkedList<>();
//        while(true){
//            ProductSale productSold = getProductSale();
//            itemsSold.add(productSold);
//        }
//
//        return new SalesRecord();
    }

    public ProductSale getProductSale(){

    }
}
