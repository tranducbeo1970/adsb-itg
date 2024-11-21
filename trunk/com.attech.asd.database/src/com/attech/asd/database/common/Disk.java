/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.common;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author NhuongND
 */
public class Disk implements Serializable {

    private String letter;
    private String type;
    private long total;
    private long free;
    private long lastUpdate;

    public Disk() {

    }

    public Disk(File aDrive) {
        this.setLetter(aDrive.toString());
        this.setFree(aDrive.getFreeSpace());
        this.setTotal(aDrive.getTotalSpace());
    }
    
    @Override
    public String toString(){
        return String.format("Drive %s: | Type: %s | Total: %s | Free: %s | LastUpdate: %s", letter, type, total, free, lastUpdate);
    }

    /**
     * @return the letter
     */
    public String getLetter() {
        return letter;
    }

    /**
     * @param letter the letter to set
     */
    public void setLetter(String letter) {
        this.letter = letter;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * @return the free
     */
    public long getFree() {
        return free;
    }

    /**
     * @param free the free to set
     */
    public void setFree(long free) {
        this.free = free;
    }

    /**
     * @return the lastUpdate
     */
    public long getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
