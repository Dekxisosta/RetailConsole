package ui.console.renderer;

import common.util.*;
import ui.console.builder.*;

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
     * @param builder
     */
    public ConsoleRenderer(ConsoleBuilder builder) {
        this.builder = builder;
    }
    /**
     * Shows numbered options 1-n, then 0, reserved
     * for back or exit options
     * @param options the list of options to be shown
     */
    public void showOptions(String[] options){
        for(int i = 1; i < options.length; i++){
            System.out.print(builder.buildOption(i, options[i]));
        }
        System.out.print(builder.buildOption(0, options[0]));
    }

    /**
     * Shows a banner with the title
     * @param title
     */
    public void showBanner(String title){
        System.out.print(builder.buildBanner(title));
    }


}
