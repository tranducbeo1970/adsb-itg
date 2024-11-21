/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.tools;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author ANDH
 */
public class ProducerConsumerExample {
    public static void main(String[] args) throws InterruptedException 
    {
        int numProducers = 4;
        int numConsumers = 3;
         
        BlockingQueue<Object> myQueue = new LinkedBlockingQueue<>(200);
         
        for (int i = 0; i < numProducers; i++){
            new Thread(new Producer(myQueue)).start();
        }
             
        for (int i = 0; i < numConsumers; i++){
            new Thread(new Consumer(myQueue)).start();
        }
 
        // Let the simulation run for, say, 10 seconds
        Thread.sleep(10 * 1000);
 
        // End of simulation - shut down gracefully
        System.exit(0);
    }
}
