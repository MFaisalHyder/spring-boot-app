package com.spring.project.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Config {

    private static final Logger logger = LogManager.getLogger(Log4j2Config.class);

    public void performLogging() {
        logger.info("**********-------------START-------------**********");

        logger.log(Level.DEBUG, "This is a debug0 message");
        logger.log(Level.DEBUG, "This is a debug1 message");
        logger.log(Level.DEBUG, "This is a debug2 message");

        logger.debug("This is a debug3 message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.error("This is an {} message", "error 2");
        logger.fatal("This is a fatal message");
        logger.info("**********-------------END-------------**********");
    }

}