/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import com.attech.adsb.record.RecordItem;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class BroadCastUDPSocketThread extends Thread {
    private DatagramSocket socket;
    private InetAddress inetAddress;
    private DatagramPacket dp;
    private int port;
    private BlockingQueue<RecordItem> blockingQueue;
    
    public BroadCastUDPSocketThread(String address, int port, BlockingQueue<RecordItem> blockingQueue) throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket();
        this.inetAddress = InetAddress.getByName(address);
        this.port = port;
        this.blockingQueue = blockingQueue;
    }
    
    public void sent(byte[] bytes) {
        try {
            dp = new DatagramPacket(bytes, bytes.length, this.inetAddress, this.port);
            this.socket.send(dp);
            System.out.println("Sent " + bytes.length + " (bytes)");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        while(true) {
            RecordItem item;
            try {
                item = this.blockingQueue.take();
                this.sent(item.getBytes());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
        }
    }
}
