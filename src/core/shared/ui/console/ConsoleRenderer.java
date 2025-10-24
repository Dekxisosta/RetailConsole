package core.shared.ui.console;

import common.util.*;

/**
 * Base renderer class for console-based UIs.
 * <p>
 * {@code ConsoleRenderer} provides general-purpose utilities
 * for rendering menus, banners, and text output.
 * It is intended to be extended by presentation-layer classes
 * across different modules.
 * <p>
 * This class delegates most string formatting to the
 * {@link ConsoleBuilder} utility, which handles styling and layout.
 *
 * @version 1.0
 * @see Ansi
 */
public class ConsoleRenderer {
    private final ConsoleBuilder builder;

    /**
     * Constructs a new {@code ConsoleRenderer} with its
     * own internal {@link ConsoleBuilder} instance.
     * <p>
     * This builder handles stylized formatting for menus
     * and text output.
     */
    public ConsoleRenderer() {
        this.builder = new ConsoleBuilder();
    }

    /**
     * Displays a numbered list of options.
     * <p>
     * The numbering starts from 1 and ends with 0, which is reserved
     * for “Back” or “Exit” type options.
     *
     * @param options the list of options to display,
     *                where {@code options[0]} is reserved for 0.
     */
    public void showOptions(String[] options) {
        for (int i = 1; i < options.length; i++) {
            System.out.println(builder.buildOption(i, options[i]));
        }
        System.out.println(builder.buildOption(0, options[0]));
    }

    /**
     * Displays a stylized banner for a given title.
     *
     * @param title the text to display inside the banner
     */
    public void showBanner(String title) {
        System.out.print(builder.buildBanner(title));
    }

    /**
     * Displays a standalone hero banner or any preformatted text.
     * <p>
     * This method is typically used for ASCII art or
     * large introductory banners.
     *
     * @param heroBanner the text or banner to display
     */
    public void showHero(String heroBanner) {
        System.out.println(heroBanner);
    }
}

/**
 * Utility class for building stylized console strings.
 * <p>
 * Provides methods for generating banners, option lists,
 * and formatted titles using ANSI escape codes.
 * <p>
 * This class is package-private, as it is only meant to
 * support {@link ConsoleRenderer}.
 */
class ConsoleBuilder {

    /**
     * Computes the total border length for a banner.
     * <p>
     * The length is determined by summing the left and right border
     * lengths, the title length, and twice the padding value.
     *
     * @param leftBorder  the left border string
     * @param rightBorder the right border string
     * @param title       the title text inside the banner
     * @param padding     the amount of space on each side of the title
     * @return the total border length
     */
    public int getBorderLength(String leftBorder, String rightBorder, String title, int padding) {
        return title.length()
                + leftBorder.length()
                + rightBorder.length()
                + (padding * 2);
    }

    /**
     * Builds a stylized banner with a given title.
     * <p>
     * The banner is surrounded by borders and includes
     * formatted ANSI styling.
     *
     * @param title the text to display in the banner
     * @return a formatted banner string
     */
    public String buildBanner(String title) {
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
     * Builds a title string using ANSI formatting.
     *
     * @param title the text to format
     * @return the formatted title string
     */
    public String buildTitle(String title) {
        return String.format(
                "%s %s %s",
                Ansi.Color.DIM_WHITE.code() + Ansi.Format.REVERSE.code(),
                title,
                Ansi.Format.RESET.code()
        );
    }

    /**
     * Creates a borderline made of a repeated character.
     *
     * @param symbol the character to repeat
     * @param len    the number of repetitions
     * @return the generated border string
     */
    public String buildBorder(char symbol, int len) {
        return String.valueOf(symbol).repeat(len);
    }

    /**
     * Builds a single menu option line.
     *
     * @param index  the option index
     * @param option the description text for the option
     * @return a formatted option string
     */
    public String buildOption(int index, String option) {
        return String.format("[%d] %s", index, option);
    }
}
