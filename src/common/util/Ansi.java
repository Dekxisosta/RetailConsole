package common.util;

import config.*;

/**
 * To make the CLI or Console-based application
 * look more professional, since most terminals support
 * ANSI or escape codes for formatting, this app utilizes as such
 * but since codiva.io does not support ANSI, a boolean flag for
 * Ansi support lives in central app config
 */
public final class Ansi {
    // Prevents instantiation
    private Ansi() {}

    /**
     * Only stores escape codes that are used by the program
     */
    public enum Color {
        RESET("\u001B[0m"),
        REVERSE("\u001B[7m"),
        DIM_WHITE("\u001B[10m"),
        RED("\u001B[91m"),
        GREEN("\u001B[92m"),
        YELLOW("\u001B[93m"),
        PINK("\u001B[95m");

        private final String code;

        Color(String code) {
            this.code = AppConfig.IS_ANSI_SUPPORTED ? code : "";
        }

        public String code() {
            return code;
        }

        public String text(String msg) {
            return code + msg + RESET.code;
        }
    }
}