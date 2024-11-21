/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon;

import com.attech.cat21.v210.Cat21Message;
import org.apache.log4j.Logger;

/**
 *
 * @author andh
 */
public class FusedItem {
    private long updatedTime;
    private double time;
    private static final Logger logger = Logger.getLogger(FusedItem.class);

    public FusedItem() {
    }
    
    public FusedItem(double time) {
        this.updatedTime = System.currentTimeMillis();
        this.time = time;
    }
    
    public synchronized boolean validate(Cat21Message msg) {
        // System.out.print("Time before: " + this.time);
        this.updatedTime = System.currentTimeMillis();
        if (msg.getPosition() == null && msg.getHighResolutionPosition() == null) {
            return true;
        }
        
        if (msg.getTimeOfMessageReceptionOfPosition() == null) {
            final String content = bytesToHex(msg.getBytes());
            logger.warn(String.format("Message has position field but does's has Time Of Message Reception Of Position Field.\n[%s]", content));
            return true;
        }
        double t = msg.getTimeOfMessageReceptionOfPosition();
        if (t > this.time || this.time - t > 72000) {
            this.time = t;
            //System.out.println("Time after: " + this.time);
            return true;
        }
        // System.out.println("Time after: " + this.time);
        return false;
    }
    
    public synchronized boolean validate(double t) {
        this.updatedTime = System.currentTimeMillis();
         if (t > this.time || this.time - t > 72000) {
            this.time = t;
            return true;
        }
        return false;
    }
    
    public synchronized void update() {
        this.updatedTime = System.currentTimeMillis();
    }
    
    public synchronized boolean isObsoleted(long timeout) {
        return System.currentTimeMillis() - updatedTime >= timeout;
    }
    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
            
        }
        return new String(hexChars);
    }
    
    @Override
    public String toString() {
        return String.format("Time: %s LastUpdate: %s", time, updatedTime );
    }
}
