/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.asd.daemon.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;

/**
 *
 * @author andh
 */
public class ExceptionHandler {
    
    private static final Logger logger = Logger.getLogger(ExceptionHandler.class);
    
    public static void handle(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        logger.error(sw.toString());
    }
    
    public static void handleInfo(String info) {
        StringWriter sw = new StringWriter();
        logger.error(sw.toString());
    }
}
