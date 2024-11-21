/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;

/**
 *
 * @author NhuongND
 */
public class ExHandler {
    
    private static final Logger logger = Logger.getLogger("Program");

    public synchronized static void handle(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        logger.error(sw.toString());
    }

    public synchronized static void handle(Exception ex, String cls) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.write(cls + "\n\r");
        ex.printStackTrace(pw);
        logger.error(sw.toString());
    }
    
    
    public synchronized static void handle(Exception ex, Class cls) {
        
        MLogger errorLog =  MLogger.getLogger(cls);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        errorLog.error(pw.toString());
    }
}