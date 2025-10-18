package common.util;

import config.*;

/**
 * To make the CLI or Console-based application
 * look more professional, since most terminals support
 * ANSI or escape codes for formatting, this app utilizes as such
 * but since codiva.io does not support ANSI, a boolean flag for
 * Ansi support lives in central app config
 *
 * @see AppConfig
 */
public final class Ansi {
    // Prevents instantiation
    private Ansi() {}

    /**
     * Stores ansi formatters
     */
    public enum Format {
        REVERSE("\u001B[7m"),
        RESET("\u001B[0m");

        private final String code;

        Format(String code) {
            this.code = AppConfig.IS_ANSI_SUPPORTED ? code : "";
        }
        /** @return the code for the ansi */
        public String code() {
            return code;
        }
    }

    /**
     * Stores ansi colors
     */
    public enum Color {
        ORANGE("\033[38;5;214m"),
        DIM_WHITE("\u001B[10m"),
        RED("\u001B[91m"),
        GREEN("\u001B[92m"),
        YELLOW("\u001B[93m"),
        PINK("\u001B[95m");

        private final String code;

        Color(String code) {
            this.code = AppConfig.IS_ANSI_SUPPORTED ? code : "";
        }

        /** @return the code for the ansi */
        public String code() {
            return code;
        }

        /**
         * @param msg the message to be wrapped with an Ansi formatter
         * @return messaged wrapped in ansi with reset
         */
        public String text(String msg) {
            return code + msg + Format.RESET.code();
        }
    }
}