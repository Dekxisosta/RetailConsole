package core.shared.ui.console;

import java.io.*;
import common.util.Logger;
import common.util.functions.*;

/**
 * The console prompter includes low-level parsers. This class
 * is meant to be extended by Controllers in other modules
 *
 * To add functionality in this class, simply use the
 * supply() utility method by passing in an inputType argument
 * and a lambda that returns the data type you wish to be
 * supplied
 *
 * @version 1.1
 * @see Logger
 * @see BufferedReader
 * @see IOException
 */
public class ConsolePrompter {
    private BufferedReader reader;

    /**
     * Functional interface for getting return values
     * @param <T> type of data to be supplied
     */
    @FunctionalInterface
    interface Supplier<T>{
        T get() throws IOException;
    }
    /**
     * Public constructor for ConsolePrompter. In this project, to remove
     * the necessity of creating apis, interfaces or abstractions
     * for contracted methods, this reader class is only limited to
     * console
     *
     * If this project is intended to scale further to a GUI implementation,
     * then simply replace the console prompter attached to the catch statement
     * of the wrapper utility at the bottommost part of the code
     *
     * @param reader used for scanning inputs
     * @see BufferedReader
     */
    public ConsolePrompter(BufferedReader reader){
        this.reader = reader;
    }

    /**
     * Gets a string, if empty then prompt again
     *
     * @param inputType the type of string to be supplied (e.g. Product name)
     * @return supplied string value / text
     */
    public String getString(String inputType){
        return supply(inputType, ()->{
            String line = readLine().trim();
            if(line.isBlank()) throw new IllegalArgumentException("Field cannot be empty");
            else return line;
        });
    }


    /**
     * Gets a double primitive data type
     *
     * @param inputType the type of double to be supplied (e.g. Product Price)
     * @return supplied double value
     */
    public double getDouble(String inputType){
        return supply(inputType, ()-> Double.parseDouble(readLine()));
    }



    /**
     * Gets an integer primitive data type
     *
     * @param inputType the type of integer to be supplied (e.g. index of choice, etc.)
     * @return supplied integer value
     */
    public int getInt(String inputType){
        return supply(inputType, ()-> Integer.parseInt(readLine()));
    }

    /**
     * Gets an integer primitive data type that is positive
     *
     * @param inputType the type of integer to be supplied (e.g. index of choice, etc.)
     * @return supplied positive integer value
     */
    public int getNonNegativeInteger(String inputType){
        return supply(inputType, ()-> {
            int input = Integer.parseInt(readLine());
            if(input<0) throw new IllegalArgumentException("Input cannot be negative");
            else return input;
        });
    }

    /**
     * Wrapper for reader.readLine() method
     * @return string
     * @throws IOException when invalid input/output
     */
    protected String readLine() throws IOException{
        return reader.readLine();
    }



    /**
     * Wrapper method which is the basis for all the getter methods
     * in this class file. Also comes with a prompter that handles checked
     * exceptions until a valid input is inputted
     *
     * @param inputType the type to be supplied
     *                  The original design used to pass the class (e.g. Integer.class)
     *                  of the supplier's type parameter. However, that
     *                  negatively affects the ux design of the CLI /
     *                  Console tool, so a string-based param is used
     *
     * @param supplier the function that returns a data of type
     * @return the data to be supplied
     * @param <T> the parameter to be supplied
     */
    public <T> T supply(String inputType, Supplier<T> supplier){
        showEnterPrompt(inputType);

        while(true){
            try {
                return supplier.get();
            } catch (Exception e) {
                Logger.log(e, Logger.Severity.ERROR);
                showEnterPrompt(inputType);
            }
        }
    }



    /**
     * Utility method for supply wrapper. Makes code more self-documenting
     * @param inputType type of input to be prompted
     */
    private void showEnterPrompt(String inputType){
        System.out.printf("Enter %s: ", inputType);
    }

}
