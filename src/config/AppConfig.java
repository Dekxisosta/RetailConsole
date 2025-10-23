package config;

/**
 * Centralized config class for the application.
 * This stores flags and program details to be reused in the program.
 * Could've used a .properties file for clearer intent
 */
public final class AppConfig {
    /**
     * Flag for ansi support, ticked false since Codiva compiler does not
     * support Ansi
     */
    public static boolean IS_ANSI_SUPPORTED = true;

    /**
     * The program's name. To be used as a personalized name for the CLI tool
     */
    public static String PROGRAM_NAME = "Asacoco Retail Store CLI";

    /**
     * The program's tagline. To be used as a personalized tagline for the CLI tool
     */
    public static String PROGRAM_BANNER = """
              .--.   .----.  .--.   .---.  .----.  .---.  .----.\s
             / {} \\ { {__   / {} \\ /  ___}/  {}  \\/  ___}/  {}  \\
            /  /\\  \\.-._} }/  /\\  \\\\     }\\      /\\     }\\      /
            `-'  `-'`----' `-'  `-' `---'  `----'  `---'  `----'\s
            Since 1969  - Over 420 Branches in the Philippines
            ==========================================================
            """;
}
