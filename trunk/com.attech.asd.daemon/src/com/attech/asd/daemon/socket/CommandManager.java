/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.socket;

import com.attech.asd.daemon.AppContext;
import com.attech.asd.daemon.common.ExceptionHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author AnhTH
 */
public class CommandManager implements Runnable {
    private static final Logger logger = Logger.getLogger(CommandManager.class);
    private static CommandManager instance;
    
    private final Map<String, ServiceProviderThread> mapConnect;
    
    private Thread thread;
    private ServerSocket listener;
    private boolean running;
    private boolean requestingStop;

    public CommandManager() {
        mapConnect = new HashMap<>();
        this.running = false;
    }
    
    /**
     * @return the instance
     */
    public synchronized static CommandManager getInstance() {
        if (instance == null){
            instance = new CommandManager();
            logger.info("CommandManager has been created");
        }
        return instance;
    }

    public void start() {
        this.thread = new Thread(this, "#CommandManager");
        this.requestingStop = false;
        this.thread.start();
    }

    public void stop() {
        this.requestingStop = true;
    }

    @Override
    public void run() {
        this.running = true;
        try {
            listener = new ServerSocket(AppContext.getServerPort());
        } catch (IOException ex) {
            this.requestingStop = true;
            logger.error(ex);
        }
        while (!requestingStop) {
            try {
                logger.info("Server is listenning");
                Socket socket = listener.accept();
                String clientIP = socket.getInetAddress().getHostAddress();
                if (mapConnect.containsKey(clientIP)) {
                    ServiceProviderThread runable = mapConnect.get(clientIP);
                    runable.setIsRequestStop(true);
                    //mapConnect.remove(runable);
                    //logger.info("Stop last socket connection with " + clientIP);

                }
                logger.info("Create socket connection with " + clientIP);
                ServiceProviderThread runable = new ServiceProviderThread(socket);
                /*
                if (runGms != null) {
                    runable.addObserver(getRunGms());
                }
                if (run != null) {
                    runable.addObserver(getRun());
                }
                */
                mapConnect.put(clientIP, runable);
                new Thread(runable).start();

            } catch (IOException ex) {
                ExceptionHandler.handle(ex);
            }
        }
        mapConnect.values().forEach((element) -> {
            element.setIsRequestStop(true);
        });
        this.mapConnect.clear();
        
        try {
            if (!listener.isClosed()) listener.close();
        } catch (IOException ex) {
            ExceptionHandler.handle(ex);
        }
        this.running = false;
        
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @return the requestingStop
     */
    public boolean isRequestingStop() {
        return requestingStop;
    }

    /**
     * @param requestingStop the requestingStop to set
     */
    public synchronized void setRequestingStop(boolean requestingStop) {
        this.requestingStop = requestingStop;
    }



}
