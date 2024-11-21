/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.playbackserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author andh
 */
public class Broadcaster {

    private String ip;
    private Integer port;

    private DatagramSocket serverSocket;
    private DatagramPacket dp;
    private InetAddress inetAddress;

    public Broadcaster(String ip, Integer port) {
        this.ip = ip;
        this.port = port;

    }

    public void initialize() throws SocketException, UnknownHostException {
        serverSocket = new DatagramSocket();
        inetAddress = InetAddress.getByName(this.ip);
    }

    public void send(byte[] packet) throws IOException {
        dp = new DatagramPacket(packet, packet.length, inetAddress, port);
        serverSocket.send(dp);
    }
}
