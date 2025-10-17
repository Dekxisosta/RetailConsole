package config;

/**
 * Centralized config class for the application.
 * This stores flags and program details to be reused in the program.
 * Could've used a .properties file for clearer intent
 */
public class AppConfig {
    /**
     * Flag for ansi support, ticked false since Codiva compiler does not
     * support Ansi
     */
    public final static boolean IS_ANSI_SUPPORTED = true;
}
