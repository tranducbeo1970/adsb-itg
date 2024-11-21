/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.client;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author AnhTH
 */
public class ClientInformationItem  implements Serializable {
    private int no;
    private long count;
    private Date lastUpdate;
    private boolean active;
    
    public ClientInformationItem(){
        
    }
    
    public ClientInformationItem(ClientInformationItem element){
        this.no = element.getNo();
        this.count = element.getCount();
        this.lastUpdate = element.getLastUpdate();
        this.active = element.isActive();
    }
    
    @Override
    public String toString() {
        return String.format("No: %d | Count: %s | Last Update: %s | Active: %s ", no, count, lastUpdate, active);
    }

    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(long count) {
        this.count += count;
        this.lastUpdate = new Date();
    }

    /**
     * @return the lastUpdate
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public synchronized void reset() {
        this.count = 0;
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
}
