package core.domain.sales.ui.console;

import core.domain.sales.model.*;
import core.shared.datastructures.*;
import core.shared.id.*;
import core.shared.ui.console.*;

import java.io.*;

/**
 * Handles user prompting and sales record creation through the console interface.
 * <p>
 * {@code SalesConsolePrompter} extends {@link ConsolePrompter} to manage
 * console-based input operations specific to the sales domain. It also provides
 * automatic ID generation for sales records and acts as a utility for building
 * new {@link SalesRecord} instances during user interaction.
 */
public class SalesConsolePrompter extends ConsolePrompter {

    /**
     * A specialized ID generator for sales records.
     * <p>
     * Extends the generic {@link IDGenerator} and provides the
     * {@code "SAL"} prefix for uniquely identifying sales records.
     */
    public static class SalesRecordIDGenerator extends IDGenerator {
        /** @return the prefix used for sales record IDs */
        @Override
        public String getPrefix(){
            return "SAL";
        }
    }

    private final SalesRecordIDGenerator idGenerator;

    /**
     * Constructs a {@code SalesConsolePrompter} using the given input reader.
     * <p>
     * This implementation is intentionally designed for console-only use,
     * simplifying the overall architecture by avoiding extra API or interface layers.
     * <p>
     * For future scalability (e.g., replacing the console with a GUI),
     * this class can be substituted by another prompter implementation,
     * ideally at the wrapper utility’s catch block near the application’s entry point.
     *
     * @param reader the {@link BufferedReader} used to read user input
     */
    public SalesConsolePrompter(BufferedReader reader) {
        super(reader);
        this.idGenerator = new SalesRecordIDGenerator();
    }

    /**
     * Generates a new unique sales record ID using the {@code SAL} prefix.
     *
     * @return a newly generated sales record ID
     */
    public String generateID(){
        return idGenerator.generateID();
    }

    /**
     * Creates and returns a new, empty {@link SalesRecord} instance
     * with an auto-generated ID and an empty {@link RecordList} of {@link SalesItem}s.
     *
     * @return a fresh {@link SalesRecord} ready for population
     */
    public SalesRecord getSalesRecord(){
        String id = idGenerator.generateID();
        RecordList<SalesItem> list = new RecordList<>();
        return new SalesRecord(id, list);
    }
}
