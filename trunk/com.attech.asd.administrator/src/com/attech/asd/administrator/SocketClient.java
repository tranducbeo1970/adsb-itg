/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import com.attech.asd.administrator.common.ExceptionHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.attech.asd.database.common.Command;

/**
 *
 * @author NhuongND
 */
public class SocketClient {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    public boolean connect(String IP, int port) throws IOException {
            socket = new Socket(IP, port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());  
            return true;
    }

    public void setCommand(Command command) {
        try {         
            oos.writeObject(command);
        } catch (IOException ex) {
            ExceptionHandler.handle(ex);
        }
    }
    
    public boolean isConnected(){
        return (socket == null) ? false : socket.isConnected();
    }
    
    public boolean isClosed(){
        return (socket == null) ? true : socket.isClosed();
    }
    
    public void close() {
        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException ioException) {
            ExceptionHandler.handle(ioException);
        }
    }
    
    public void setOIS(ObjectInputStream ois){
        this.ois = ois;
    }
    
    public ObjectInputStream getOIS(){
        return ois;
    }
    
    public void setOOS(ObjectOutputStream oos){
        this.oos = oos;
    }
    
    public ObjectOutputStream getOOS(){
        return oos;
    }
    
    public String getCurrentIp(){
        return socket.getLocalAddress().getHostAddress();
    }
}
