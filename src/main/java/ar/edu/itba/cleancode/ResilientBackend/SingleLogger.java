package ar.edu.itba.cleancode.resilientbackend;

import java.util.logging.Logger;
  
// Singleton wrapper for the Logger
public class SingleLogger {

    private static final Logger logger = Logger.getLogger(SingleLogger.class.getName());

    private SingleLogger() {
        // Private constructor to prevent instantiation
    }

    public static Logger getLogger() {
        return logger;
    }

}
