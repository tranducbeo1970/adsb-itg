/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author root
 */
public class BroadCastUDPSocket {
    private DatagramSocket socket;
    private InetAddress inetAddress;
    private DatagramPacket dp;
    private int port;
    
    public BroadCastUDPSocket(String address, int port) throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket();
        this.inetAddress = InetAddress.getByName(address);
        this.port = port;
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
}
