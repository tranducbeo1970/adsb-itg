/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator;

import com.attech.asd.administrator.common.ConnectionEventType;
import com.attech.asd.daemon.ServerInfo;
import com.attech.asd.database.common.Command;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author AnhTH
 */
public class EventUpdaterThread extends Thread {
    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EventUpdaterThread.class);
    
    private IEventUpdater eventUpdater;
    private SoundAlert alert;
    
    private boolean requestStop;
    private boolean running;
    
    
    public EventUpdaterThread() {
        this.alert = new SoundAlert();
    }
    
    @Override
    public synchronized void run() {
        running = true;
        requestStop = false;
        while (AppContext.getInstance().getUpdaterSocket().getOIS() == null) {
            AppContext.getInstance().connectToServer = false;
            if (AppContext.getInstance().EnableSound) 
                alert.PlayAlert();
            
            System.out.printf("Still waiting updater socket connect... ");
            if (eventUpdater == null) {
                return;
            }
            eventUpdater.updateInfo(ConnectionEventType.Disconnected, null);
            try {
                AppContext.connectUpdaterSocket();
                AppContext.connectCommandSocket();
                System.out.printf("OK\n");
                
                AppContext.getInstance().connectToServer = true;
            } catch (IOException ex1) {
                System.out.printf("FAIL\n");
            }
            
            try {
                this.wait(AppContext.getRefreshTime());
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        Command command;
        while (!requestStop) {
            try {
                AppContext.getInstance().connectToServer = true;
                if ((command = (Command) AppContext.getInstance().getUpdaterSocket().getOIS().readObject()) != null) {
                    final List<Object> objs = command.getParams();
                    switch (command.getCmd()) {
                        case UPDATE_SERVER:
                            if (objs != null && objs.size() > 0) {
                                ServerInfo data = (ServerInfo) objs.get(0);
                                if (data.isReloadConfig()) {
                                    AppContext.getInstance().reloadConfig();
                                    logger.info("Configuration has been reloaded");
                                }
                                if (eventUpdater == null) {
                                    return;
                                }
                                eventUpdater.updateInfo(ConnectionEventType.Connected, data);
                            }
                            break;
                    }
                }
                // SOUND WARNING
                if (AppContext.getInstance().isWarning() && AppContext.getInstance().EnableSound) {
                    alert.PlayAlert();
                }
            } catch (IOException ex) {
                AppContext.getInstance().getCommandSocket().close();
                AppContext.getInstance().getUpdaterSocket().close();
                AppContext.getInstance().connectToServer = false;
                while (AppContext.getInstance().getUpdaterSocket().isClosed()) {
                    // SOUND WARNING
                    if (AppContext.getInstance().EnableSound) 
                        alert.PlayAlert();
                    eventUpdater.updateInfo(ConnectionEventType.Disconnected, null);
                    // RECONNECT
                    System.out.printf("Still waiting updater socket connect... ");
                    try {
                        AppContext.connectUpdaterSocket();
                        AppContext.connectCommandSocket();
                        System.out.printf("OK\n");
                        AppContext.getInstance().connectToServer = true;
                    } catch (IOException ex1) {
                        System.out.printf("FAIL\n");
                    } 
                    
                    try {
                        this.wait(AppContext.getRefreshTime());
                    } catch (InterruptedException iex) {
                        System.out.println(iex.getMessage());
                    }
                }
            } catch (ClassNotFoundException ex) {
                logger.error(ex);
            }
        }

        logger.info("Updating Thread has been closed.");
        running = false;
    }
    
    
    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return the eventUpdater
     */
    public IEventUpdater getEventUpdater() {
        return eventUpdater;
    }

    /**
     * @param eventUpdater the eventUpdater to set
     */
    public void setEventUpdater(IEventUpdater eventUpdater) {
        this.eventUpdater = eventUpdater;
    }
}
