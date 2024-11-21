/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.tools;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author ANDH
 */
public class Consumer implements Runnable {
     protected BlockingQueue<Object> queue;
 
    Consumer(BlockingQueue<Object> theQueue) {
        this.queue = theQueue;
    }
 
    public void run() {
        try
        {
            while (true) 
            {
                Object obj = queue.take();
                System.out.println("Consumed resource - Queue size now = "  + queue.size());
                take(obj);
            }
        } 
        catch (InterruptedException ex) 
        {
            System.out.println("CONSUMER INTERRUPTED");
        }
    }
 
    void take(Object obj) 
    {
        try
        {
            Thread.sleep(100); // simulate time passing
        } 
        catch (InterruptedException ex) 
        {
            System.out.println("Consumer Read INTERRUPTED");
        }
        System.out.println("Consuming object " + obj);
    }
}
