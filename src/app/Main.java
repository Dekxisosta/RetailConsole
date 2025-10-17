package app;

import common.util.*;

public class Main {
    public static void main(String[] args){



        // Runs the program, if any top-level exceptions are
        // observed in the program, logged with fatal severity
        try{
            Integer.parseInt("Tite");
            new AppRunner().run();
        }catch(Exception e){
            Logger.logException(e, Logger.Severity.FATAL, true);
        }

    }
}
