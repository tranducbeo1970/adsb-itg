/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

import com.attech.asd.amhs.database.dao.ConfigDao;
import com.attech.asd.amhs.database.dao.DBException;
import com.attech.asd.amhs.database.dao.HibernateUtil;
import com.attech.asd.amhs.service.monitor.Command;
import com.attech.asd.amhs.service.monitor.ThreadStatus;
import com.attech.asd.amhs.service.socket.SocketEventHandler;
import com.attech.asd.amhs.service.socket.ClientSocketHandler;
import com.attech.asd.amhs.service.socket.SocketServer;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author ANDH
 */
public class Executor implements Runnable {

    private static final MLogger logger = MLogger.getLogger(Executor.class);
    private final ConfigDao configDao = new ConfigDao();
    private ThreadManager threadManager = new ThreadManager();
    private final SocketServer socketManager;
    private final SocketEventHandler socketEventHandler;
    private final ThreadEventHandler threadEventHandler;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    public Executor() throws DBException {

	logger.info("Initializing");
	socketEventHandler = new SocketEventHandler() {
	    @Override
	    public void onCommandReceived(Command command) {
		handleReceivingCommand(command);
	    }

	    @Override
	    public void onConnectionClosed(ClientSocketHandler socketHandler) {
		handleClosingConnection(socketHandler);
	    }
	};

	threadEventHandler = this::handleNotify;
	String monitorBindingInterface = configDao.getConfig("AmhsMonitorBindIP1");
	String monitorBindingPort = configDao.getConfig("AmhsMonitorPort1");
	int bindingPort = Integer.parseInt(monitorBindingPort);
	this.socketManager = new SocketServer(monitorBindingInterface, bindingPort);
        this.socketManager.setSocketEventHandler(socketEventHandler);
        
	this.threadManager = new ThreadManager();
	this.threadManager.setThreadEventHandler(threadEventHandler);
        
    }

    public void handleReceivingCommand(Command command) {
	try {
	    if (command == null) {
		return;
	    }

	    int commandId = 0;
	    switch (command.getType()) {
		case START:
                    System.out.println("REQUEST START");
		    commandId = (int) command.getArgument();
		    this.threadManager.start(commandId);
		    break;
		case STOP:
                    System.out.println("REQUEST STOP");
		    commandId = (int) command.getArgument();
		    this.threadManager.stop(commandId);
		    break;
		case REFRESH:
		    break;
		case UPDATE:
		    break;
	    }

	} catch (Exception ex) {
	    logger.error(ex);
	}
    }

    public void handleClosingConnection(ClientSocketHandler socketHandler) {
	this.socketManager.removeHandler(socketHandler);
    }

    public void handleNotify(List<ThreadStatus> status) {
	this.socketManager.notifyThreadStatus(status);
    }

    public void execute() throws DBException {
	this.socketManager.start();
	this.threadManager.start();
	logger.info("Start client notify timer");
	scheduledExecutorService.scheduleAtFixedRate(this, 10, 10, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
	try {
	    logger.info("Notify status to all client");
	    List<ThreadStatus> threadSatuses = this.threadManager.getThreadStatus();
	    this.socketManager.notifyThreadStatus(threadSatuses);
	} catch (Exception ex) {
	    logger.error(ex);
	}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	DOMConfigurator.configure("config/log.xml");
	HibernateUtil.buildSessionFactory("config/database.xml");
	MessageUtils.initialize();
	try {
	    logger.info("Start programming");
	    Executor executor = new Executor();
	    executor.execute();
	} catch (DBException ex) {
	    logger.error(ex);
	}

    }

}
