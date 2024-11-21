/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.client;

import com.attech.asd.daemon.AppContext;
import com.attech.asd.daemon.common.ExceptionHandler;
import com.sun.javafx.image.impl.IntArgb;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

/**
 *
 * @author anhth
 */
public class SimpleTransferPackage {
    private static final Logger logger = Logger.getLogger(SimpleTransferPackage.class);
    
    private int no;
    private String address;
    private Integer port;
    private String bindingAddress;
    
    private DatagramSocket socket;
    private InetAddress inetaddress;
    
    private boolean running;
    private boolean socketOpen;
    
    public int sensorId;
    
    public int packets;
    
    
    public SimpleTransferPackage() {
        this.running = false;
        this.socketOpen = false;
        packets = 0;
    }
    
    public SimpleTransferPackage(int no, String address, Integer port, String bindingAddress) {
        this();
        this.no = no;
        this.address = address;
        this.port = port;
        this.bindingAddress = bindingAddress;
    }
    
    public void initSocket() {
        try {
            if (this.getPort() == null || this.getAddress() == null || this.getAddress().isEmpty()) return;
            this.socket = new DatagramSocket(null);
            if (this.getBindingAddress() != null && !this.bindingAddress.isEmpty()) {
                this.socket.bind(new InetSocketAddress(this.bindingAddress, 0));
            }
            this.inetaddress = InetAddress.getByName(this.address);
            socketOpen = true;
            logger.info(String.format("DUC 1 Transfer socket #%d is openned : %s", this.no, this));
            AppContext.getInstance().setMessage(String.format("Transfer socket #%d is openned : %s", this.no, this));
            
        } catch (SocketException | UnknownHostException ex) {
            ExceptionHandler.handle(ex);
            socketOpen = false;
        }
    }
    
    private void stop() {
        try {
            this.running = false;
            this.socket.close();
        } catch (Exception ex) {
            ExceptionHandler.handle(ex);
        }
        socketOpen = false;
    }
    
    public int transfer(byte[] bytes) {
        if (!socketOpen) return 0;
        try {
            final DatagramPacket msgPacket = new DatagramPacket(bytes, bytes.length, this.inetaddress, this.port);
            logger.info("Send socket packet " + this.inetaddress + ":" + Integer.toString(this.port) + " " + Integer.toString(packets) + " " + Integer.toString(bytes.length) + " bytes");
            packets++;
            this.socket.send(msgPacket);
            return 1;
        } catch (IOException e) {
            ExceptionHandler.handle(e);
            return 0;
        }
    }
    
    @Override
    public String toString(){
        return String.format("SimpleTransferPackage: No:#%d Address: %s Port %d Binding Address: %s", this.no, this.getAddress(), this.getPort(), this.getBindingAddress());
    }
    
    /**
     * @return the no
     */
    public int getNo() {
        return no;
    }

    /**
     * @return the enable
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param enable the enable to set
     */
    public void setRunning(boolean enable) {
        if (enable) {
            initSocket();
        }
        else {
            stop();
        }
        this.running = enable;
        ClientInformationManager.getInstance().update(no, 0, this.running);
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @return the bindingAddress
     */
    public String getBindingAddress() {
        return bindingAddress;
    }
}
