/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import org.apache.log4j.Logger;

/**
 *
 * @author anbk
 */
public class ShutdownHook extends Thread {

    private static final Logger logger = Logger.getLogger(ShutdownHook.class);

    @Override
    public void run() {
        logger.warn("PROGRAM END");
    }
}
