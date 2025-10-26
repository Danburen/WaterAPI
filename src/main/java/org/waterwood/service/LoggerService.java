package org.waterwood.service;

import lombok.Getter;
import org.waterwood.enums.COLOR;
import org.waterwood.utils.Colors;

import java.util.logging.Logger;

@Getter
public class LoggerService {
    private Logger logger = null;
    public LoggerService(String loggerName){
        logger = Logger.getLogger(loggerName);
    }


    public void info(String message){
        if(logger == null) throw new RuntimeException("Logger hasn't been initialized.");
        logger.info(message);
    }

    public void warning(String message){
        if(logger == null) throw new RuntimeException("Logger hasn't been initialized.");
        logger.warning(message);
    }

    public void logMessage(String message){
        logger.info(Colors.parseColor(message));
    }

    public void logMessage(String message, COLOR color){
        logger.info(Colors.parseColor(Colors.coloredText(message,color)));
    }

}
