/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;

/**
 *
 * @author andh
 */
public class Timer implements Runnable{
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    private final ScheduledExecutorService worker;
    private final JLabel label;
    
    public Timer(JLabel label) {
        this.label = label;
        this.worker = Executors.newScheduledThreadPool(1);
    }
    
    public void start() {
        Date date = new Date();
        long delay = date.getTime() % 1000;
        this.worker.scheduleWithFixedDelay(this, delay, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        this.label.setText(format.format(new Date()));
    }
}
