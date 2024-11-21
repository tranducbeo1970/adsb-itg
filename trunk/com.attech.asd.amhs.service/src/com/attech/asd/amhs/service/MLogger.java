/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;

/**
 *
 * @author andh
 */
public class MLogger {

    private static boolean debug;

    public static void setDebug(boolean value) {
        debug = value;
    }

    private Logger logger;

    protected MLogger(Logger logger) {
        this.logger = logger;
    }

    public void info(String message, Object... param) {
        logger.info(String.format(message, param));
    }

    public void info(String message) {
        logger.info(message);
    }

    public void error(String message, Object... param) {
        logger.error(String.format(message, param));
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(Exception ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        logger.error(errors.toString());
    }
    
    public void debug(Exception ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        logger.debug(errors.toString());
    }

    public void fatal(String message, Object... param) {
        logger.fatal(String.format(message, param));
    }

    public void fatal(String message) {
        logger.fatal(message);
    }

    public void debug(String message, Object... param) {
        logger.debug(String.format(message, param));
    }

    public void debug(String message) {
//        if (!debug) {
//            return;
//        }
        logger.debug(message);
    }

    public void warn(String message, Object... param) {
        logger.warn(String.format(message, param));
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public static MLogger getLogger(Class clazz) {
        return new MLogger(Logger.getLogger(clazz));
    }

    public static MLogger getLogger(String name) {
        return new MLogger(Logger.getLogger(name));
    }

}
