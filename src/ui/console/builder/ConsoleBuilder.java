package ui.console.builder;

import common.util.*;

public class ConsoleBuilder {


    /**
     * Gets banner length from the different parts that contain the title,
     * inclusive of title
     *
     * @param leftBorder border on the left
     * @param rightBorder border on the right
     * @param title the title to be wrapped in banner creation
     * @param padding gets padding length, multiplied by two
     * @return total border length
     */
    public int getBorderLength(String leftBorder, String rightBorder, String title, int padding){
        return title.length()
                + leftBorder.length()
                + rightBorder.length()
                + (padding * 2);
    }



    /**
     * Creates a stylized banner for param title
     * @param title the text inside the banner
     */
    public String buildBanner (String title){
        String leftBorder = "{|";
        String rightBorder = "|}";
        int padding = 2;
        int borderLen = getBorderLength(leftBorder, rightBorder, title, padding);

        return String.format("""
                %s
                %s %s %s
                %s
                """,
                buildBorder('-', borderLen),
                leftBorder, buildTitle(title), rightBorder,
                buildBorder('-', borderLen)
        );
    }



    /**
     * Builds a title with Ansi
     * @param title the title to be wrapped
     * @return title with formatting, only if ansi_supported is ticked true
     */
    public String buildTitle(String title){
        return String.format(
                "%s %s %s",
                Ansi.Color.YELLOW.code() + Ansi.Format.REVERSE.code(),
                title,
                Ansi.Format.RESET.code()
        );
    }



    /**
     * Creates a border
     * @param symbol the repeated symbol or char
     * @param len the number of repetitions
     * @return char repeated for len times
     */
    public String buildBorder(char symbol, int len){
        return String.valueOf(symbol).repeat(len);
    }



    /**
     * Builds an option, utility for show options method
     * @param index the index to be printed out
     * @param option the text of the option
     */
    public String buildOption(int index, String option){
        return String.format("\n[%d] %s", index,  option);
    }
}
