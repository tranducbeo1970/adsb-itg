/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

/**
 *
 * @author andh
 */
public class AppContext {

    private static AppContext instance;

    private boolean cldsWarning;
    private boolean playbackMode;
    private int speedVector;
    private int aheadVector;

    public AppContext() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
 
    /**
     * @return the cldsWarning
     */
    public boolean isCldsWarning() {
	return cldsWarning;
    }

    /**
     * @param cldsWarning the cldsWarning to set
     */
    public void setCldsWarning(boolean cldsWarning) {
	this.cldsWarning = cldsWarning;
    }
    
    
    /**
     * @return the playbackMode
     */
    public boolean isPlaybackMode() {
        return playbackMode;
    }

    /**
     * @param playbackMode the playbackMode to set
     */
    public void setPlaybackMode(boolean playbackMode) {
        this.playbackMode = playbackMode;
    }
    
        /**
     * @return the speedVector
     */
    public int getSpeedVector() {
        return speedVector;
    }

    /**
     * @param speedVector the speedVector to set
     */
    public void setSpeedVector(int speedVector) {
        this.speedVector = speedVector;
    }

    /**
     * @return the aheadVector
     */
    public int getAheadVector() {
        return aheadVector;
    }

    /**
     * @param aheadVector the aheadVector to set
     */
    public void setAheadVector(int aheadVector) {
        this.aheadVector = aheadVector;
    }
    //</editor-fold> 

    public static AppContext instance() {
	if (instance == null) {
	    instance = new AppContext();
	}
	return instance;
    }


}
