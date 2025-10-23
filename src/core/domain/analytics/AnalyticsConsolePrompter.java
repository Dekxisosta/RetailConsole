package core.domain.analytics;

import core.shared.ui.console.*;

import java.io.*;

public class AnalyticsConsolePrompter extends ConsolePrompter {
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
    public AnalyticsConsolePrompter(BufferedReader reader) {
        super(reader);
    }
}
