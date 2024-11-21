/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.receiver;

import com.attech.asd.daemon.AppContext;
import java.io.Serializable;

/**
 *
 * @author Tang Hai Anh
 */
public class InformationItem implements Serializable {
    private int no;
    private boolean status;
    private boolean data;
    private long received;
    private long transfer;
    private long lastReceived;
    private String des;
    private boolean receivingStatus;
    
    public InformationItem() {
        receivingStatus = false;
    }
    
    public InformationItem(int no) {
        this();
        this.no = no;
    }
    
    public InformationItem(InformationItem element) {
        this();
        this.no = element.getNo();
        this.received = element.getReceived();
        this.transfer = element.getTransfer();
        this.lastReceived = element.getLastReceived();
        this.status = element.isStatus();
        this.des = element.getDes();
        //this.receivingStatus = element.getReceivingStatus();
        this.data = element.isData();
        
        if (!this.isStatus()) {
            receivingStatus = false;
        }
        this.receivingStatus = System.currentTimeMillis() - this.lastReceived <= AppContext.getInstance().getErrorTimeout();
    }
    
    @Override
    public String toString() {
        return String.format("No: %d | Status: %s | Data: %s | Received: %s | Tranfer: %s | LastReceived: %s | Description: %s | ReceiveStatus: %s", no, isStatus(), data, received, transfer, lastReceived, des, receivingStatus);
    }

    /**
     * @return the no
     */
    public int getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(int no) {
        this.no = no;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the data
     */
    public boolean isData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(boolean data) {
        this.data = data;
    }

    /**
     * @return the lastReceived
     */
    public Long getLastReceived() {
        return lastReceived;
    }

    /**
     * @param lastReceived the lastReceived to set
     */
    public void setLastReceived(Long lastReceived) {
        this.lastReceived = lastReceived;
    }

    /**
     * @return the des
     */
    public String getDes() {
        return des;
    }

    /**
     * @param des the des to set
     */
    public void setDes(String des) {
        this.des = des;
    }
    
    public void updateReceivingStatus() {
        if (!this.isStatus()) {
            receivingStatus = false;
        }
        this.receivingStatus = System.currentTimeMillis() - this.lastReceived <= AppContext.getInstance().getErrorTimeout();
    }

    /**
     * @return the lastReceivingStatus
     */
    public boolean getReceivingStatus() {
        return receivingStatus;
    }

    /**
     * @param receivingStatus the lastReceivingStatus to set
     */
    public void setReceivingStatus(boolean receivingStatus) {
        this.receivingStatus = receivingStatus;
    }

    /**
     * @return the received
     */
    public long getReceived() {
        return received;
    }

    /**
     * @param received the received to set
     */
    public void setReceived(long received) {
        this.received += received;
    }

    /**
     * @return the transfer
     */
    public long getTransfer() {
        return transfer;
    }

    /**
     * @param transfer the transfer to set
     */
    public void setTransfer(long transfer) {
        this.transfer += transfer;
    }
  
    public synchronized void reset() {
        this.received = 0;
        this.transfer = 0;
    }
}
