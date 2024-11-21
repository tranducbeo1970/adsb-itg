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
//public class CommandServer extends Thread implements Observer{

public class UpdatingManager implements Runnable { 
    private static final Logger logger = Logger.getLogger(UpdatingManager.class);
    private static UpdatingManager instance;
    private final Map <String, UpdatingThread> mapConnect;
    
    private Thread thread;
    private ServerSocket listener;
    private boolean running;
    private boolean requestingStop;
    
    
    public UpdatingManager(){
        mapConnect = new HashMap<>();
        this.running = false;
    }
    
    /**
     * @return the instance
     */
    public synchronized static UpdatingManager getInstance() {
        if (instance == null) {
            instance = new UpdatingManager();
            logger.info("UpdatingManager has been created");
        }
        return instance;
    }

    public void start() {
        this.thread = new Thread(this, "#UpdatingManager");
        this.requestingStop = false;
        this.thread.start();
    }
    
    public void stop() {
        this.requestingStop = true;
    }
    
    public void setNotifyReloadClient(int idClient){
        mapConnect.values().forEach((element) -> {
            element.setNoticeReloadClient(idClient);
        });
    }
    
    public void setNotifyReloadReceiver(int idSensor){
        mapConnect.values().forEach((element) -> {
            element.setNoticeReloadReceiver(idSensor);
        });
    }
    
    public void setNotifyReloadMsgAcc(int id){
        mapConnect.values().forEach((element) -> {
            element.setNoticeReloadMsgAccId(id);
        });
    }
    
    public void setNotifyReloadConfig(boolean value){
        mapConnect.values().forEach((element) -> {
            element.setReloadConfig(value);
        });
    }

    @Override
    public void run() {
        this.running = true;
        try {
            listener = new ServerSocket(AppContext.getServerPort() + 1);
        } catch (IOException ex) {
            this.requestingStop = true;
            logger.error(ex);
        }

        while (!requestingStop) {
            try {
                logger.info("UpdatingThread is listenning");
                Socket socket = listener.accept();
                String clientIP = socket.getInetAddress().getHostAddress();
                if (mapConnect.containsKey(clientIP)) {
                    UpdatingThread obj = mapConnect.get(clientIP);
                    obj.stop();
                    AppContext.getInstance().removeScreen(clientIP);
                    //mapConnect.remove(obj);
                    logger.info("Stop last UpdatingThread socket connection with " + clientIP);
                }
                logger.info("Create UpdatingThread socket connection with " + clientIP);
                UpdatingThread obj = new UpdatingThread(socket);
                mapConnect.put(clientIP, obj);
                AppContext.getInstance().addScreen(clientIP);
                obj.start();

            } catch (IOException ex) {
                //mapConnect.clear();
                ExceptionHandler.handle(ex);
            }
        }
        mapConnect.values().forEach((e) -> {
            e.stop();
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
