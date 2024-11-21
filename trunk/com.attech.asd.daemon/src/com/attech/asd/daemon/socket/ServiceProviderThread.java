/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.socket;

import com.attech.asd.daemon.AppContext;
import com.attech.asd.database.FileRecordDao;
import com.attech.asd.database.common.Command;
import com.attech.asd.database.entities.FileRecord;
import com.attech.asd.database.entities.FusedFileRecord;
import com.attech.asd.daemon.RecordItem;
import com.attech.asd.daemon.client.ClientManager;
import com.attech.asd.daemon.receiver.ReceiverManager;
import com.attech.asd.database.FusedFileRecordDao;
import com.attech.asd.database.common.ActionRequest;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author AnhTH
 */
public class ServiceProviderThread extends Observable implements Runnable {
    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ServiceProviderThread.class);
    
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private Socket connection;
    private boolean isRequestStop;
    

    public ServiceProviderThread(Socket socket) throws IOException {
        this.connection = socket;
        this.isRequestStop = false;
        outputStream = new ObjectOutputStream(connection.getOutputStream());
        inputStream = new ObjectInputStream(connection.getInputStream());
        logger.info("Service provider is inited (clientIP: " + connection.getInetAddress().getHostAddress() + ":" + connection.getPort() + ")");
    }

    @Override
    public void run() {
        Command command;
        Command cmdComplete;
        while (!isRequestStop) {
            try {
                logger.info("Waiting command...");
                if (inputStream != null) {
                    while ((command = (Command) inputStream.readObject()) != null) {
                        logger.info("Command : " + command.getCmd());
                        final List<Object> objs = command.getParams();
                        final int id;
                        switch (command.getCmd()) {
                            case ACTIVE_RECEIVER:
                                id = (Integer) objs.get(0);
                                ReceiverManager.getInstance().startReceiver(id);
                                //UpdatingManager.getInstance().setNotifyReloadReceiver(id);
                                break;
                            case DEACTIVE_RECEIVER:
                                id = (Integer) objs.get(0);
                                ReceiverManager.getInstance().stopReceiver(id);
                                //UpdatingManager.getInstance().setNotifyReloadReceiver(id);
                                break;
                            case RELOAD_RECEIVER:
                                id = (Integer) objs.get(0);
                                ReceiverManager.getInstance().reloadReceiver(id);
                                //UpdatingManager.getInstance().setNotifyReloadReceiver(id); 
                                break;
                            case DELETE_RECEIVER:
                                id = (Integer) objs.get(0);
                                ReceiverManager.getInstance().deleteReceiver(id);
                                //UpdatingManager.getInstance().setNotifyReloadReceiver(id);
                                break;
                            case ACTIVE_CLIENT:
                                id = (Integer) objs.get(0);
                                ClientManager.getInstance().startClient(id);
                                //UpdatingManager.getInstance().setNotifyReloadClient(id);
                                break;
                            case DEACTIVE_CLIENT:
                                id = (Integer) objs.get(0);
                                ClientManager.getInstance().stopClient(id);
                                //UpdatingManager.getInstance().setNotifyReloadClient(id);
                                break;
                            case RELOAD_CLIENT:
                                id = (Integer) objs.get(0);
                                ClientManager.getInstance().reloadClient(id);
                                //UpdatingManager.getInstance().setNotifyReloadClient(id);
                                break;
                            case DELETE_CLIENT:
                                id = (Integer) objs.get(0);
                                ClientManager.getInstance().deleteClient(id);
                                //UpdatingManager.getInstance().setNotifyReloadClient(id);
                                break;
                            case RELOAD_MSG_ACC:
                                id = (Integer) objs.get(0);
                                UpdatingManager.getInstance().setNotifyReloadMsgAcc(id);
                                break;
                            
                            case READ_FILE:
                                sendList((String) command.getParam(0));
                                break;

                            case DELETE_FILE:
                                final FileRecordDao dao = new FileRecordDao();
                                objs.forEach((obj) -> {
                                    Integer fileId = (Integer) obj;
                                    FileRecord record = dao.get(fileId);
                                    File file = new File(record.getAbsolutePath());
                                    if (file.delete()) {
                                        record.setStatus(3);
                                        dao.save(record);
                                    }
                                    
                                });
                                // NOTIFY
                                cmdComplete = new Command();
                                cmdComplete.setCmd(ActionRequest.ACTION_COMPLETED);
                                outputStream.writeObject(cmdComplete);
                                break;
                                
                            case DELETE_FUSED_FILE:
                                final FusedFileRecordDao fdao = new FusedFileRecordDao();
                                objs.forEach((obj) -> {
                                    Integer fileId = (Integer) obj;
                                    FusedFileRecord record = fdao.get(fileId);
                                    File file = new File(record.getAbsolutePath());
                                    if (file.delete()) {
                                        record.setStatus(3);
                                        fdao.save(record);
                                    }
                                    
                                });
                                
                                // NOTIFY
                                cmdComplete = new Command();
                                cmdComplete.setCmd(ActionRequest.ACTION_COMPLETED);
                                outputStream.writeObject(cmdComplete);
                                break;
                                
                            case DOWNLOAD_FILE:
                                sendList((String) command.getParam(0));
                                break;
                            case RELOAD_CONFIGURATION:
                                AppContext.getInstance().reloadConfig();
                                logger.info("Configuration has been reloaded");
                                UpdatingManager.getInstance().setNotifyReloadConfig(true);
                                break;
                        }
                    }
                }

            } catch (Exception ex) {
                try {
                    connection.close();
                    logger.info("Service provider is closed (clientIP: " + connection.getInetAddress().getHostAddress() + ":" + connection.getPort() + ")");
                    //model.removeRow(iRow);
                    break;
                } catch (IOException ex1) {
                    logger.error(ex1.getMessage());
                }
            }
        }
    }

    private void sendList(String pathFileRecord) {
        final FileInputStream fis;
        final List<RecordItem> items = new ArrayList<>();
        try {
            fis = new FileInputStream(pathFileRecord);
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
                while (fis.available() > 0) {
                    items.add((RecordItem) ois.readObject());
                }
            } catch (EOFException ex) {
                //logger.info(String.format("End file!"));
            }
            outputStream.writeObject(items);
            System.out.println(items.size());
            logger.info(String.format("Sent all!"));
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * @return the inputSteam
     */
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param isRequestStop the isRequestStop to set
     */
    public synchronized void setIsRequestStop(boolean isRequestStop) {
        //System.out.println("set isRequetStop");
        this.isRequestStop = isRequestStop;
    }

    public Socket getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Socket connection) {
        this.connection = connection;
    }

}
