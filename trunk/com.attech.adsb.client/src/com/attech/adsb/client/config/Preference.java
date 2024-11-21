package com.attech.adsb.client.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "Preference")
@XmlAccessorType(XmlAccessType.NONE)
public class Preference extends com.attech.adsb.client.common.XmlSerializer {


    @XmlElement(name = "Sector")
    private String sector;

    @XmlElement(name = "TargetTimeoutThreshold")
    private long targetTimeout;

    @XmlElement(name = "Record")
    private Record record;

    @XmlElement(name = "SignalQuality")
    private SignalQuality signalQuality;

    @XmlElement(name = "FullScreen")
    private boolean fullscreen;

    @XmlElement(name = "Theme")
    private String theme;
    
    @XmlElement(name = "LockFile")
    private String lockFilePath;
    
    @XmlElement(name = "TargetUpdateInterval")
    private int targetUpdateInterval;
    
     @XmlElement(name = "TargetInfoUpdateInterval")
    private int targetInfoUpdateInterval;
    
    @XmlElement(name = "DebugMode")
    private boolean debugMode;
    
    @XmlElement(name = "TmaWarningThreshold")
    private Integer tmaWarningThreshold;
    
    /** Fucking shit **/
    
//    @XmlElement(name = "Parameters")
//    private Parameters parameters;

    public Preference() {
    }
    
    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public static void main(String[] args) {
        Preference preference = new Preference();
        preference.setRecord(new Record(true, "record", 30));
        preference.setSignalQuality(new SignalQuality(4, 5, 2));
        preference.save("preference.xml");
        
        System.out.println("Done");
    }
       
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">

    
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
    
//    public Parameters getParameters() {
//        return parameters;
//    }
//    
//    public void setParameter(Parameters parameters) {
//        this.parameters = parameters;
//    }
//    
//    public void fromFileParameter() {
//        Parameter.SPV                       = parameters.SPV;
//        Parameter.WarningHitFuture          = parameters.WarningHitFuture;
//        Parameter.WarningHitLevel           = parameters.WarningHitLevel;
//        Parameter.WarningHitYellowDistance  = parameters.WarningHitYellowDistance;
//        Parameter.WarningHitDistance        = parameters.WarningHitDistance;
//        Parameter.vectorAhead               = parameters.vectorAhead;
//        Parameter.heading                   = parameters.heading;
//        Parameter.assume                    = parameters.assume;
//        Parameter.AcviteFilter              = parameters.AcviteFilter;
//        Parameter.CheckVVPR                 = parameters.CheckVVPR;
//        Parameter.CheckConflict             = parameters.CheckConflict;
//        Parameter.CheckDRAW                 = parameters.CheckDRAW;
//        Parameter.CheckAMA                  = parameters.CheckAMA;
//        Parameter.CheckAMA                  = parameters.CheckAMA;
//        Parameter.CheckTMA                  = parameters.CheckTMA;
//        Parameter.CheckMSA                  = parameters.CheckMSA;
//        Parameter.CheckOutScreen            = parameters.CheckOutScreen;
//        Parameter.CheckClimdDesc            = parameters.CheckClimdDesc;
//        Parameter.CheckTransfer             = parameters.CheckTransfer;
//        Parameter.Client                    = parameters.Client;
//        Parameter.IsRecording               = parameters.IsRecording;
//        Parameter.RecordingStorageLocation  = parameters.RecordingStorageLocation;
//        Parameter.amaMargin                 = parameters.amaMargin;
//        Parameter.to_feet                   = parameters.to_feet;
//    }
//    
//    public void toFileParameter() {
//        parameters.SPV                      = Parameter.SPV;
//        parameters.WarningHitFuture         = Parameter.WarningHitFuture;
//        parameters.WarningHitLevel          = Parameter.WarningHitLevel;
//        parameters.WarningHitYellowDistance = Parameter.WarningHitYellowDistance;
//        parameters.WarningHitDistance       = Parameter.WarningHitDistance;
//        parameters.vectorAhead              = Parameter.vectorAhead;
//        parameters.heading                  = Parameter.heading;
//        parameters.assume                   = Parameter.assume;
//        parameters.AcviteFilter             = Parameter.AcviteFilter;
//        parameters.CheckVVPR                = Parameter.CheckVVPR;
//        parameters.CheckConflict            = Parameter.CheckConflict;
//        parameters.CheckDRAW                = Parameter.CheckDRAW;
//        parameters.CheckAMA                 = Parameter.CheckAMA;
//        parameters.CheckTMA                 = Parameter.CheckTMA;
//        parameters.CheckMSA                 = Parameter.CheckMSA;
//        parameters.CheckOutScreen           = Parameter.CheckOutScreen;
//        parameters.CheckClimdDesc           = Parameter.CheckClimdDesc;
//        parameters.CheckTransfer            = Parameter.CheckTransfer;
//        parameters.Client                   = Parameter.Client;
//        parameters.IsRecording              = Parameter.IsRecording;
//        parameters.RecordingStorageLocation = Parameter.RecordingStorageLocation;
//        parameters.amaMargin                = Parameter.amaMargin;
//        parameters.to_feet                  = Parameter.to_feet;
//        parameters.SPV                      = Parameter.SPV;
//        parameters.SPV                      = Parameter.SPV;
//        parameters.SPV                      = Parameter.SPV;
//        parameters.SPV                      = Parameter.SPV;
//        parameters.SPV                      = Parameter.SPV;
//        parameters.SPV                      = Parameter.SPV;
//    }

    /**
     * @return the record
     */
    public Record getRecord() {
        return record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(Record record) {
        this.record = record;
    }

    /**
     * @return the signalQuality
     */
    public SignalQuality getSignalQuality() {
        return signalQuality;
    }

    /**
     * @param signalQuality the signalQuality to set
     */
    public void setSignalQuality(SignalQuality signalQuality) {
        this.signalQuality = signalQuality;
    }
    
    /**
     * @return the targetTimeout
     */
    public long getTargetTimeout() {
        return targetTimeout;
    }

    /**
     * @param targetTimeout the targetTimeout to set
     */
    public void setTargetTimeout(long targetTimeout) {
        this.targetTimeout = targetTimeout;
    }

    /**
     * @return the fullscreen
     */
    public boolean isFullscreen() {
        return fullscreen;
    }

    /**
     * @param fullscreen the fullscreen to set
     */
    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }
    
    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }
    
    
    /**
     * @return the lockFilePath
     */
    public String getLockFilePath() {
        return lockFilePath;
    }

    /**
     * @param lockFilePath the lockFilePath to set
     */
    public void setLockFilePath(String lockFilePath) {
        this.lockFilePath = lockFilePath;
    }
    
    /**
     * @return the targetUpdateInterval
     */
    public int getTargetUpdateInterval() {
        return targetUpdateInterval;
    }

    /**
     * @param targetUpdateInterval the targetUpdateInterval to set
     */
    public void setTargetUpdateInterval(int targetUpdateInterval) {
        this.targetUpdateInterval = targetUpdateInterval;
    }
    
    
    /**
     * @return the debugMode
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * @param debugMode the debugMode to set
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    /**
     * @return the tmaWarningThreshold
     */
    public Integer getTmaWarningThreshold() {
        return tmaWarningThreshold;
    }

    /**
     * @param tmaWarningThreshold the tmaWarningThreshold to set
     */
    public void setTmaWarningThreshold(Integer tmaWarningThreshold) {
        this.tmaWarningThreshold = tmaWarningThreshold;
    }
    
    /**
     * @return the targetInfoUpdateInterval
     */
    public int getTargetInfoUpdateInterval() {
        return targetInfoUpdateInterval;
    }

    /**
     * @param targetInfoUpdateInterval the targetInfoUpdateInterval to set
     */
    public void setTargetInfoUpdateInterval(int targetInfoUpdateInterval) {
        this.targetInfoUpdateInterval = targetInfoUpdateInterval;
    }
    //</editor-fold>
}
