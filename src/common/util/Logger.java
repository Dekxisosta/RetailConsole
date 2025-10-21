package common.util;

import config.*;

/**
 * The Logger class provides centralized error and system message tracking
 * throughout the application's runtime.
 *
 * It is static by design to avoid creating new instances in every class
 * that requires logging functionality.
 *
 * @see AppConfig
 * @see Ansi
 */
public final class Logger {

    /*
     * An internal enum for severity labels is utilized by the logger
     * to ensure consistency in documenting bugs in a program.
     *
     * This makes the program scalable and maintainable
     */
    public enum Severity{
        NOTICE(Ansi.Color.YELLOW),
        ERROR(Ansi.Color.ORANGE),
        FATAL_ERROR(Ansi.Color.RED);

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
        NOTICE(Ansi.Color.YELLOW),
        INFO(Ansi.Color.GREEN),
        SYSTEM(Ansi.Color.DIM_WHITE),
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
    public static void log(Exception e, Severity severity){
        String label = formatLabel(severity.name(), severity.getColor());
        System.out.println(String.format("%s %s: %s",
                label,
                e.getClass().getSimpleName().substring(0, e.getClass().getSimpleName().indexOf("Exception")),
                e.getMessage()
            )
        );
    }

    /**
     * Logs a message with an appropriate level label
     * @param text the text of the log
     * @param level determines the error's hierarchy
     */
    public static void log(String title, String text, Level level){
        String label = formatLabel(level.name(), level.getColor());
        System.out.println(String.format("""
                        
                        %s %s
                        %s
                        """,
                        label,
                        title,
                        text
                )
        );
    }

    /**
     * Logs an exception with an appropriate severity label,
     * flag in place for doing print stack traces to better document errors
     * @param e the exception caught
     * @param severity determines the error's hierarchy
     */
    public static void log(Exception e, Severity severity, boolean doPrintStackTrace){
        log(e, severity);
        if(doPrintStackTrace)
            e.printStackTrace();
    }


    // Formats a label in an intended format, adaptive to ansi support
    private static String formatLabel(String name, Ansi.Color color) {
        if (AppConfig.IS_ANSI_SUPPORTED) {
            return color.text(String.format("%s %s ", Ansi.Format.REVERSE.code(), name));
        } else {
            return String.format("[%s]", name);
        }
    }
}
