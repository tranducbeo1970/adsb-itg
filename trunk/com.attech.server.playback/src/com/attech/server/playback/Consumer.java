/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import java.util.concurrent.DelayQueue;

/**
 *
 * @author root
 */
public class Consumer extends Thread {
    
    private DelayQueue dq;
    private BroadCastDelayItem item;
    private BroadCastUDPSocket socket;
    
    public Consumer(DelayQueue dq, BroadCastUDPSocket socket) {
        this.dq = dq;
        this.socket  = socket;
    }
    
    @Override
    public void run() {
        
        while (true) {
            try {
                item = (BroadCastDelayItem) dq.take();
                this.socket.sent(item.getByte());
                System.out.println("Size " + this.dq.size());
                
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    public void stopThread() {
        this.dq.clear();
        this.stop();
    }
}
