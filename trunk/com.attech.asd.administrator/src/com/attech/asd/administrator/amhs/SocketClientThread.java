/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.amhs;

import com.attech.asd.administrator.common.ConnectionEventType;
import com.attech.asd.amhs.service.BaseThread;
import com.attech.asd.amhs.service.MLogger;
import com.attech.asd.amhs.service.monitor.Command;
import com.attech.asd.amhs.service.monitor.ThreadStatus;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author andh
 */
public class SocketClientThread extends BaseThread {

    private static final MLogger logger = MLogger.getLogger(SocketClientThread.class);
    private Socket socket;
    private String address;
    private Integer port;
    private Integer retryTimeout;
    private ClientEventHandler clientHandler;
    private boolean running;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public SocketClientThread(String address, Integer port) {
	this.address = address;
	this.port = port;
	this.retryTimeout = 30;
	running = false;
    }

    public void sendCommand(Command command) throws IOException {
	this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	this.objectOutputStream.writeObject(command);

    }

    @Override
    public void run() {
	try {

	    running = true;
	    logger.info("Start handling");
	    while (!Thread.interrupted()) {
		handle();
	    }

	} catch (InterruptedException ex) {
	    logger.error(ex);
	    logger.warn("Thread now is stopped");
	} finally {
	    closeStream();
	    running = false;
	    onStatusUpdate(ConnectionEventType.Disconnected, null);
	}
    }

    private void closeStream() {
	try {
            if (objectInputStream != null)
                this.objectInputStream.close();
            if (objectOutputStream != null)
                this.objectOutputStream.close();
	} catch (IOException ex) {
	    logger.warn("Close connection has some error.");
	    logger.error(ex);
	}
    }

    @Override
    public void stopThread() {

	try {
	    this.socket.close();
	    super.stopThread();
	} catch (IOException ex) {
	    logger.error(ex);
	}
    }

    private void handle() throws InterruptedException {

	try {
	    this.socket = new Socket();
	    this.socket.connect(new InetSocketAddress(this.address, port));

	    logger.info("Connected to server %s:%s", this.address, this.port);

	    while (!Thread.interrupted()) {
		try {
		    this.objectInputStream = new ObjectInputStream(socket.getInputStream());
		    List<ThreadStatus> threadStatusList = (List<ThreadStatus>) objectInputStream.readObject();
		    if (threadStatusList == null) {
			continue;
		    }
		    logger.info("Received %s messages.", threadStatusList.size());
		    onStatusUpdate(ConnectionEventType.Update, threadStatusList);
		} catch (ClassNotFoundException ex) {
		    logger.warn("Received wrong message type");
		}
	    }

	} catch (IOException ex) {

	    logger.error("Connection to %s:%s fail", this.address, this.port);
	    logger.error(ex);
	    onStatusUpdate(ConnectionEventType.Disconnected, null);
	    this.lock(this.retryTimeout);
	}
    }

    private void onStatusUpdate(ConnectionEventType eventType, List<ThreadStatus> param) {
	if (this.clientHandler == null) {
	    return;
	}

//	if (param.length > 0) {
//	    this.clientHandler.updateStatus(eventType, param[0]);
//	}
	this.clientHandler.updateStatus(eventType, param);
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public Integer getPort() {
	return port;
    }

    public void setPort(Integer port) {
	this.port = port;
    }

    public void setClientHandler(ClientEventHandler clientHandler) {
	this.clientHandler = clientHandler;
    }

    public boolean isRunning() {
	return running;
    }

    @Override
    protected MLogger getLogger() {
	return logger;
    }

}
