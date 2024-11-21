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
public class Producer implements Runnable {

    protected BlockingQueue<Object> queue;
 
    Producer(BlockingQueue<Object> theQueue) {
        this.queue = theQueue;
    }
 
    @Override
    public void run() 
    {
        try
        {
            while (true) 
            {
                Object justProduced = getResource();
                queue.put(justProduced);
                System.out.println("Produced resource - Queue size now = "  + queue.size());
            }
        } 
        catch (InterruptedException ex) 
        {
            System.out.println("Producer INTERRUPTED");
        }
    }
 
    Object getResource() 
    { 
        try
        {
            Thread.sleep(200); // simulate time passing during read
        } 
        catch (InterruptedException ex) 
        {
            System.out.println("Producer Read INTERRUPTED");
        }
        return new Object();
    }
    
}
