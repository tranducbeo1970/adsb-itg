/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.socket;

import com.attech.asd.daemon.AppContext;
import com.attech.asd.daemon.client.ClientInformationItem;
import com.attech.asd.daemon.client.ClientInformationManager;
import com.attech.asd.daemon.Data;
import com.attech.asd.daemon.DataFusion;
import com.attech.asd.daemon.receiver.InformationItem;
import com.attech.asd.database.common.Command;
import com.attech.asd.database.common.Disk;
import com.attech.asd.daemon.receiver.InformationManager;
import com.attech.asd.daemon.ServerInfo;
import com.attech.asd.daemon.common.ExceptionHandler;
import com.attech.asd.database.common.ActionRequest;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author anhth
 */
public class UpdatingThread  implements Runnable  {
    private String clientIp;
    private Socket connection;
    private ObjectOutputStream outputStream;
    private final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UpdatingThread.class);
    private Command cmdOut;
    private boolean requestStop;
    
    Thread thread;
    
    private Integer reloadClientId;
    private Integer reloadReceiverId;
    private Integer reloadMsgAccId;
    private boolean reloadConfig;
                
    private final SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");
    private String currentDate = dateFormatGmt.format(new Date());

    public UpdatingThread(Socket socket) throws IOException {
        this.connection = socket;
        clientIp = socket.getInetAddress().getHostAddress();
        outputStream = new ObjectOutputStream(connection.getOutputStream());
        reloadClientId = 0;
        reloadReceiverId = 0;
        reloadMsgAccId = 0;
        logger.info("Data Tranfer provider is inited (clientIP: " + clientIp + ":" + connection.getPort() + ")");
        reloadConfig = false;        
        dateFormatGmt.setTimeZone(AppContext.utc);
    }
    
    public void start(){
        thread = new Thread(this);
        requestStop = false;
        thread.start();
    }
    
    public void stop(){
        requestStop = true;
    }       
    
    @Override
    public void run() {
        while (!requestStop) {
            try {
                if (connection.isClosed()) {
                    return;
                }
                cmdOut = new Command();
                cmdOut.setCmd(ActionRequest.UPDATE_SERVER);
                
                ServerInfo serverInfo = new ServerInfo();
                Data fusionFileName = new Data("FusingFile", "");
                Data fusionTargetCount = new Data("TargetCount", 0);
                Data storageInfo = new Data("Storage", null);
                
                HashMap<Integer, InformationItem> receivers = new HashMap<>();
                InformationManager.instance().getInfo().values().forEach((element) -> {
                    InformationItem item = new InformationItem(element);
                    receivers.put(item.getNo(), item);
                });
                Data activeReceivers = new Data("ActiveReceiver", receivers);
                serverInfo.setActiveReceivers(activeReceivers);
                
                HashMap<Integer, ClientInformationItem> clients = new HashMap<>();
                ClientInformationManager.getInstance().getInfo().values().forEach((element) -> {
                    ClientInformationItem item = new ClientInformationItem(element);
                    clients.put(item.getNo(), item);
                });
                Data activeClients = new Data("ActiveClient", clients);
                serverInfo.setActiveClients(activeClients);
                
                fusionFileName.setData(DataFusion.getInstance().getRecordingFile());
                serverInfo.setFusionFileName(fusionFileName);
                
                fusionTargetCount.setData(DataFusion.getInstance().targetCount());
                serverInfo.setFusionTargetCount(fusionTargetCount);
                serverInfo.setFusedMessageCount(DataFusion.getInstance().getCountFused());
                serverInfo.setMessageCount(DataFusion.getInstance().getCount());
                                                        
                File drive = new File(AppContext.getStorageLocation());                
                Disk disk = new Disk(drive);
                disk.setLastUpdate(System.currentTimeMillis());
                storageInfo.setData(disk);          
                
                serverInfo.setStorageInfo(storageInfo);
                
                serverInfo.setReloadClientId(this.reloadClientId);
                serverInfo.setReloadReceiverId(this.reloadReceiverId);
                serverInfo.setReloadMsgAccId(this.reloadMsgAccId);
                
                serverInfo.setReloadConfig(reloadConfig);

                if (!AppContext.getInstance().getMessage(clientIp).isEmpty()){
                    serverInfo.addMessage(AppContext.getInstance().getMessage(clientIp));
                    
                }
                
                cmdOut.addParam(serverInfo);
                outputStream.writeObject(cmdOut);
                
                // RESET Stream khi bat dau ngay moi
                if(this.isReset()){
                    outputStream.flush();            
                    outputStream.close();  
                    System.runFinalization();
                    System.gc();
                }                     
                
                // RESET
                AppContext.getInstance().putMessage(clientIp, "");
                if (this.reloadClientId > 0)
                    this.reloadClientId = 0;
                if (this.reloadReceiverId > 0)
                    this.reloadReceiverId = 0;
                if (this.reloadMsgAccId > 0)
                    this.reloadMsgAccId = 0;
                this.reloadConfig = false;        
                                              
                Thread.sleep(AppContext.getRefreshTime());   
            } catch (IOException | InterruptedException ex) {
                logger.error(ex);
                requestStop = true;
            }
        }
        close();
    }
    
    public boolean isReset() {
        String date = dateFormatGmt.format(new Date());
        if (date.equalsIgnoreCase(currentDate)) {
            return false;
        }

        this.currentDate = date;
        return true;        
    }
    
    public void setNoticeReloadClient(Integer idClient){
        this.reloadClientId = idClient;
    }
    
    public void setNoticeReloadReceiver(Integer idSensor){
        this.reloadReceiverId = idSensor;
    }
    
    public void setNoticeReloadMsgAccId(Integer value){
        this.reloadMsgAccId = value;
    }
    
    public boolean isClosed(){
        return connection.isClosed();
    }

    public void close(){
        try {
            connection.close();
            System.out.println("Socket closed");
        } catch (IOException ex) {
            ExceptionHandler.handle(ex);
        }
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

    /**
     * @return the reloadConfig
     */
    public boolean isReloadConfig() {
        return reloadConfig;
    }

    /**
     * @param reloadConfig the reloadConfig to set
     */
    public void setReloadConfig(boolean reloadConfig) {
        this.reloadConfig = reloadConfig;
    }

}
