package common.util;

import config.*;

/*
 * The Logger class provides centralized error and system message tracking
 * throughout the application's runtime.
 *
 * It is static by design to avoid creating new instances in every class
 * that requires logging functionality.
 */
public class Logger {
    /*
     * An internal enum for severity labels is utilized by the logger
     * to ensure consistency in documenting bugs in a program.
     *
     * This makes the program scalable and maintainable
     */
    public enum Severity{
        FATAL(Ansi.Color.RED);

        private Ansi.Color color;
        Severity(Ansi.Color color){this.color = color;}
        public Ansi.Color getColor(){return color;}
    }

    /*
     * An internal enum for documenting intended outputs in the program
     *
     * This makes the program scalable and maintainable
     */
    public enum Level{
        INFO(Ansi.Color.GREEN),
        SYSTEM(Ansi.Color.YELLOW),
        DEBUG(Ansi.Color.PINK);

        private Ansi.Color color;
        Level(Ansi.Color color){this.color = color;}
        public Ansi.Color getColor(){return color;}
    }

    /**
     * Logs an exception with an appropriate severity label
     * @param e the exception caught
     * @param severity determines the error's hierarchy
     */
    public static void logException(Exception e, Severity severity){
        String label = formatLabel(severity.name(), severity.getColor(), true);
        System.out.println(String.format("%s %s: %s",
                label,
                e.getClass().getSimpleName(),
                e.getMessage()
            )
        );
    }
    /**
     * Logs an exception with an appropriate severity label,
     * flag in place for doing print stack traces to better document errors
     * @param e the exception caught
     * @param severity determines the error's hierarchy
     */
    public static void logException(Exception e, Severity severity, boolean doPrintStackTrace){
        logException(e, severity);
        if(doPrintStackTrace)
            e.printStackTrace();
    }


    // Formats a label in an intended format, adaptive to ansi support
    private static String formatLabel(String name, Ansi.Color color, boolean isError) {
        if (AppConfig.IS_ANSI_SUPPORTED) {
            return color.text(String.format("%s %s%s ", Ansi.Color.REVERSE.code(), name, (isError ? " ERROR" : "")));
        } else {
            return String.format("[%s%s]", name, (isError ? " ERROR" : ""));
        }
    }

}
