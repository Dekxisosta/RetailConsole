package ui.console;

import java.io.*;
import common.util.Logger;

public class ConsoleReader {
    private BufferedReader reader;

    public static void main(String[] args){
        new ConsoleReader(new BufferedReader(new InputStreamReader(System.in))).getString("text");
    }

    @FunctionalInterface
    interface Supplier<T>{
        T get() throws IOException;
    }

    public ConsoleReader(BufferedReader reader){
        this.reader = reader;
    }

    public String getString(String inputType){
        return supply(inputType, ()->{
            String line = readLine().trim();
            if(line.isBlank()) throw new IllegalArgumentException("Field cannot be empty");
            else return line;
        });
    }

    public long getLong(String inputType){
        return supply(inputType, ()-> Long.parseLong(readLine()));
    }

    public float getFloat(String inputType){
        return supply(inputType, ()-> Float.parseFloat(readLine()));
    }

    public int getInt(String inputType){
        return supply(inputType, ()-> Integer.parseInt(readLine()));
    }

    protected String readLine() throws IOException{
        return reader.readLine();
    }
    protected <T> T supply(String inputType, Supplier<T> supplier){
        while(true){
            try {
                return supplier.get();
            } catch (Exception e) {
                Logger.logException(e, Logger.Severity.ERROR);
                System.out.printf("Enter new %s: ", inputType);
            }
        }
    }

}
