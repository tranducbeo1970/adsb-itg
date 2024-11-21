/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service.socket;

import com.attech.asd.amhs.service.MLogger;
import com.attech.asd.amhs.service.monitor.Command;
import com.attech.asd.amhs.service.monitor.ThreadStatus;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

/**
 *
 * @author ANDH
 */
public class ClientSocketHandler extends Thread {

    private static MLogger logger = MLogger.getLogger(ClientSocketHandler.class);

    private Socket socket;
    private String ip;
    private SocketEventHandler eventHandle;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String key;

    public ClientSocketHandler(Socket socket) throws IOException {
	this.socket = socket;
	this.ip = this.socket.getInetAddress().getHostAddress();
	
//	this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
	try {

	    logger.info("Start handling");
	    while (!Thread.interrupted()) {
		handle();
	    }

	} catch (IOException ex) {
	    logger.warn("Connection from ip %s fail.", this.ip);
	    logger.error(ex);

	} finally {
	    closeStream();
	}
    }

    public void sendStatus(List<ThreadStatus> status) throws IOException {
	try {
            logger.info("Sending server status to %s", this.ip);
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	    this.objectOutputStream.writeObject(status);
            //System.out.println(status.get(0)); //AnhTH Debug
	} catch (IOException ex) {
	    logger.error(ex);
            throw ex;
	}
    }

    private void handle() throws IOException {
	try {
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
	    Command command = (Command) objectInputStream.readObject();
	    this.notifyCommand(command);

	} catch (IOException ex) {
            notifyClose();
	    throw ex;
	} catch (ClassNotFoundException ex) {
	    logger.warn("Commnad from %s is not corected format", this.ip);
	}
    }

    private void closeStream() {
	try {
	    this.objectInputStream.close();
	    this.objectOutputStream.close();
	} catch (IOException ex) {
	    logger.warn("Connection from ip %s fail.", this.ip);
	    logger.error(ex);
	}
    }

    public void setEventHandler(SocketEventHandler handler) {
	this.eventHandle = handler;
    }

    private void notifyCommand(Command command) {
	if (this.eventHandle == null) {
	    return;
	}
	this.eventHandle.onCommandReceived(command);
    }

    private void notifyClose() {
	if (this.eventHandle == null) {
	    return;
	}
	this.eventHandle.onConnectionClosed(this);
    }

    /**
     * @return the key
     */
    public String getKey() {
	return key;
    }
}
