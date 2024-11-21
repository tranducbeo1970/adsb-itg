/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.record.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author root
 */
public class DataRecorderTest {

    public static void main(String[] args) {
        
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date resultdate = new Date(yourmilliseconds);
        System.out.println(sdf.format(resultdate));
        
    }

}
