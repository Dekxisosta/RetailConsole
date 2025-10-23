package core.domain.analytics;

import core.shared.ui.console.*;

import java.io.*;

/**
 * Class for prompting inputs, made especially for the analytics module.
 * For now, it just extends the {@link ConsolePrompter} class to use its
 * primary built-in methods
 */
public class AnalyticsConsolePrompter extends ConsolePrompter {
    /**
     * Passes a buffered reader to its superclass, which is ConsolePrompter
     * @param reader
     */
    public AnalyticsConsolePrompter(BufferedReader reader) {
        super(reader);
    }
}
