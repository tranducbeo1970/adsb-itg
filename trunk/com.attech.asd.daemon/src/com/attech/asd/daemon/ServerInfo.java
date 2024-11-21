/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon;

import com.attech.asd.database.common.Disk;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author anhth
 */
public class ServerInfo implements Serializable {
    private Data reloadClientId;
    private Data reloadReceiverId;
    private Data reloadMsgAccId;
    
    private Data fusionTargetCount;
    private Data fusionFileName;
    private Data fusedMessageCount;
    private Data messageCount;
    private Data storageInfo;
    
    private Data activeClients;
    private Data activeReceivers;
    
    private String message;
    private boolean reloadConfig;
    
    
    public ServerInfo(){
        fusionFileName = new Data("FusingFile", "");
        fusionTargetCount = new Data("TargetCount", 0);
        storageInfo = new Data("Storage", new Disk());
        
        activeClients = new Data("ActiveClient", new HashMap<>());
        activeReceivers = new Data("ActiveReceiver", new HashMap<>());
        
        reloadClientId = new Data("ReloadClientId", 0);
        reloadReceiverId = new Data("ReloadReciverId", 0);
        reloadMsgAccId = new Data("ReloadMsgAccId", 0);
        
        fusedMessageCount = new Data("FusedMsgCount", 0);
        messageCount = new Data("MsgCount", 0);
        
        message = "";
        reloadConfig = false;
    }
    
    /**
     * @return the reloadAllMsg
     */
    public Integer getReloadMsgAccIdValue() {
        return reloadMsgAccId.getDataInt();
    }

    /**
     * @param value
     */
    public void setReloadMsgAccId(Integer value) {
        this.reloadMsgAccId.setData(value);
    }
    
    /**
     * @return the reloadMsgAccId
     */
    public Data getReloadMsgAccId() {
        return reloadMsgAccId;
    }
    
    /**
     * @return the reloadClientId
     */
    public Integer getReloadClientIdValue() {
        return (Integer) reloadClientId.getData();
    }

    /**
     * @param reloadClientId the reloadClientId to set
     */
    public void setReloadClientId(Integer reloadClientId) {
        this.reloadClientId.setData(reloadClientId);
    }

    /**
     * @return the reloadClientId
     */
    public Data getReloadClientId() {
        return reloadClientId;
    }

    /**
     * @param reloadClientId the reloadClientId to set
     */
    public void setReloadClientId(Data reloadClientId) {
        this.reloadClientId = reloadClientId;
    }
    
    /**
     * @return the reloadReceiverId
     */
    public Integer getReloadReceiverIdValue() {
        return (Integer) reloadReceiverId.getData();
    }

    /**
     * @param reloadReceiverId the reloadReceiverId to set
     */
    public void setReloadReceiverId(Integer reloadReceiverId) {
        this.reloadReceiverId.setData(reloadReceiverId);
    }
    

    /**
     * @return the reloadReceiverId
     */
    public Data getReloadReceiverId() {
        return reloadReceiverId;
    }

    /**
     * @param reloadReceiverId the reloadReceiverId to set
     */
    public void setReloadReceiverId(Data reloadReceiverId) {
        this.reloadReceiverId = reloadReceiverId;
    }

    /**
     * @return the fusionTargetCount
     */
    public Integer getFusionTargetCountValue() {
        return (Integer) fusionTargetCount.getData();
    }

    /**
     * @param fusionTargetCount the fusionTargetCount to set
     */
    public void setFusionTargetCount(Integer fusionTargetCount) {
        this.fusionTargetCount.setData(fusionTargetCount);
    }
    
    /**
     * @return the fusionTargetCount
     */
    public Data getFusionTargetCount() {
        return fusionTargetCount;
    }

    /**
     * @param fusionTargetCount the fusionTargetCount to set
     */
    public void setFusionTargetCount(Data fusionTargetCount) {
        this.fusionTargetCount = fusionTargetCount;
    }
    
    /**
     * @return the fusionFileName
     */
    public String getFusionFileNameValue() {
        return (String) fusionFileName.getData();
    }

    /**
     * @param fusionFileName the fusionFileName to set
     */
    public void setFusionFileName(String fusionFileName) {
        this.fusionFileName.setData(fusionFileName);
    }

    /**
     * @return the fusionFileName
     */
    public Data getFusionFileName() {
        return fusionFileName;
    }

    /**
     * @param fusionFileName the fusionFileName to set
     */
    public void setFusionFileName(Data fusionFileName) {
        this.fusionFileName = fusionFileName;
    }
    
    /**
     * @return the storageInfo
     */
    public Disk getStorageInfoValue() {
        return (Disk) storageInfo.getData();
    }

    /**
     * @param storageInfo the storageInfo to set
     */
    public void setStorageInfo(Disk disk) {
        this.storageInfo.setData(disk);
    }

    /**
     * @return the storageInfo
     */
    public Data getStorageInfo() {
        return storageInfo;
    }

    /**
     * @param storageInfo the storageInfo to set
     */
    public void setStorageInfo(Data storageInfo) {
        this.storageInfo = storageInfo;
    }

    /**
     * @return the activeClients
     */
    public Data getActiveClients() {
        return activeClients;
    }

    /**
     * @param activeClients the activeClients to set
     */
    public void setActiveClients(Data activeClients) {
        this.activeClients = activeClients;
    }

    /**
     * @return the activeReceivers
     */
    public Data getActiveReceivers() {
        return activeReceivers;
    }

    /**
     * @param activeReceivers the activeReceivers to set
     */
    public void setActiveReceivers(Data activeReceivers) {
        this.activeReceivers = activeReceivers;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void addMessage(String message) {
        this.message += message;
    }

    /**
     * @return the fusionPackageCount
     */
    public Data getFusedMessageCount() {
        return fusedMessageCount;
    }
    
    /**
     * @return the fusionPackageCount
     */
    public int getFusedMessageCountValue() {
        return fusedMessageCount.getDataInt();
    }

    /**
     * @param fusionPackageCount the fusionPackageCount to set
     */
    public void setFusedMessageCount(Data fusionPackageCount) {
        this.fusedMessageCount = fusionPackageCount;
    }
    
    /**
     * @param value the fusionPackageCount to set
     */
    public void setFusedMessageCount(int value) {
        this.fusedMessageCount.setData(value);
    }

    /**
     * @return the messageCount
     */
    public Data getMessageCount() {
        return messageCount;
    }

    /**
     * @param messageCount the messageCount to set
     */
    public void setMessageCount(Data messageCount) {
        this.messageCount = messageCount;
    }
    
    /**
     * @return the messageCount
     */
    public int getMessageCountValue() {
        return messageCount.getDataInt();
    }

    /**
     * @param messageCount the messageCount to set
     */
    public void setMessageCount(int messageCount) {
        this.messageCount.setData(messageCount);
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
