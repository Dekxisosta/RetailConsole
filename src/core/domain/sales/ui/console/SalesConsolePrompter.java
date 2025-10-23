package core.domain.sales.ui.console;

import core.domain.sales.model.*;
import core.shared.datastructures.*;
import core.shared.id.*;
import core.shared.ui.console.*;

import java.io.*;

public class SalesConsolePrompter extends ConsolePrompter {
    /**
     * Generates sales record IDs. This class extends the id generator
     * abstraction which needs designated prefixes
     * @see IDGenerator
     */
    public static class SalesRecordIDGenerator extends IDGenerator {
        @Override
        public String getPrefix(){
            return "SAL";
        }
    }

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
    public SalesConsolePrompter(BufferedReader reader) {
        super(reader);
        this.idGenerator = new SalesRecordIDGenerator();
    }

    public String generateID(){
        return idGenerator.generateID();
    }

    public SalesRecord getSalesRecord(){
        String id = idGenerator.generateID();
        RecordList<SalesItem> list = new RecordList<>();
        return new SalesRecord(id, list);
    }
}
