package core.shared.ui.console;

import common.util.*;

/**
 * Renderer class meant to be extended by controllers in different modules.
 * May scale more if the need arises
 * @version 1.0
 * @see Ansi
 */
public class ConsoleRenderer {
    private ConsoleBuilder builder;

    /**
     * Public constructor, needs a builder for
     * formats and stylized outputs
     */
    public ConsoleRenderer() {
        this.builder = new ConsoleBuilder();
    }
    /**
     * Shows numbered options 1-n, then 0, reserved
     * for back or exit options
     * @param options the list of options to be shown
     */
    public void showOptions(String[] options){
        for(int i = 1; i < options.length; i++){
            System.out.println(builder.buildOption(i, options[i]));
        }
        System.out.println(builder.buildOption(0, options[0]));
    }

    /**
     * Shows a banner with the title
     * @param title
     */
    public void showBanner(String title){
        System.out.print(builder.buildBanner(title));
    }

    public void showHero(String heroBanner) {
        System.out.println(heroBanner);
    }
}

/**
 * Builder/Factory class for creating stylized strings. Also includes utilities
 * for getting lengths
 */
class ConsoleBuilder {
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
                Ansi.Color.DIM_WHITE.code() + Ansi.Format.REVERSE.code(),
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
        return String.format("[%d] %s", index,  option);
    }
}
