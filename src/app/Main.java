package app;

import common.util.*;

public class Main {
    public static void main(String[] args){
        try{
            new AppRunner().run();
        }catch(Exception e){
            Logger.logException(e, Logger.Severity.FATAL_ERROR, true);
        }

    }
}
