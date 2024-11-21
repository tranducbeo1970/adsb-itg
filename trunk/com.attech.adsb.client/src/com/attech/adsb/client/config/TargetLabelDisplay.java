/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

/**
 *
 * @author andh
 */
public class TargetLabelDisplay {

    private boolean targetCallsignVisible;
    private boolean targetSSRVisible;
    private boolean targetSPDVisible;
    private boolean targetALTVisible;
    private boolean targetCALTVisible;
    private boolean targetCoALTVisible;
    private boolean targetCTRLVisible;
    private boolean targetHDGNoteVisible;
    private boolean targetInfoVisible;
    private boolean targetHeadingVisible;
    private boolean targetTrackingVisible;
    private boolean targetCldsWarning;
    private long historyLength;
    
    public TargetLabelDisplay() {
    }
    
    public void setAllDisplay(boolean isDisplay) {
        this.targetCallsignVisible = isDisplay;
        this.targetSSRVisible = isDisplay;
        this.targetSPDVisible = isDisplay;
        this.targetALTVisible = isDisplay;
        this.targetCALTVisible = isDisplay;
        this.targetCoALTVisible = isDisplay;
        this.targetCTRLVisible = isDisplay;
        this.targetHDGNoteVisible = isDisplay;
        this.targetInfoVisible = isDisplay;
        this.targetHeadingVisible = isDisplay;
        this.targetTrackingVisible = isDisplay;
    }
    
    public Boolean isDisplayNothing() {
        return !targetCallsignVisible
                && !targetSSRVisible
                && !targetSPDVisible
                && !targetALTVisible
                && !targetCALTVisible
                && !targetCoALTVisible
                && !targetCTRLVisible
                && !targetHDGNoteVisible
                && !targetInfoVisible
                && !targetHeadingVisible
                && !targetTrackingVisible;
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the targetCallsignVisible
     */
    public boolean isTargetCallsignVisible() {
        return targetCallsignVisible;
    }

    /**
     * @param targetCallsignVisible the targetCallsignVisible to set
     */
    public void setTargetCallsignVisible(boolean targetCallsignVisible) {
        this.targetCallsignVisible = targetCallsignVisible;
    }

    /**
     * @return the targetSSRVisible
     */
    public boolean isTargetSSRVisible() {
        return targetSSRVisible;
    }

    /**
     * @param targetSSRVisible the targetSSRVisible to set
     */
    public void setTargetSSRVisible(boolean targetSSRVisible) {
        this.targetSSRVisible = targetSSRVisible;
    }

    /**
     * @return the targetSPDVisible
     */
    public boolean isTargetSPDVisible() {
        return targetSPDVisible;
    }

    /**
     * @param targetSPDVisible the targetSPDVisible to set
     */
    public void setTargetSPDVisible(boolean targetSPDVisible) {
        this.targetSPDVisible = targetSPDVisible;
    }

    /**
     * @return the targetALTVisible
     */
    public boolean isTargetALTVisible() {
        return targetALTVisible;
    }

    /**
     * @param targetALTVisible the targetALTVisible to set
     */
    public void setTargetALTVisible(boolean targetALTVisible) {
        this.targetALTVisible = targetALTVisible;
    }

    /**
     * @return the targetCALTVisible
     */
    public boolean isTargetCALTVisible() {
        return targetCALTVisible;
    }

    /**
     * @param targetCALTVisible the targetCALTVisible to set
     */
    public void setTargetCALTVisible(boolean targetCALTVisible) {
        this.targetCALTVisible = targetCALTVisible;
    }

    /**
     * @return the targetCoALTVisible
     */
    public boolean isTargetCoALTVisible() {
        return targetCoALTVisible;
    }

    /**
     * @param targetCoALTVisible the targetCoALTVisible to set
     */
    public void setTargetCoALTVisible(boolean targetCoALTVisible) {
        this.targetCoALTVisible = targetCoALTVisible;
    }

    /**
     * @return the targetCTRLVisible
     */
    public boolean isTargetCTRLVisible() {
        return targetCTRLVisible;
    }

    /**
     * @param targetCTRLVisible the targetCTRLVisible to set
     */
    public void setTargetCTRLVisible(boolean targetCTRLVisible) {
        this.targetCTRLVisible = targetCTRLVisible;
    }

    /**
     * @return the targetHDGNoteVisible
     */
    public boolean isTargetHDGNoteVisible() {
        return targetHDGNoteVisible;
    }

    /**
     * @param targetHDGNoteVisible the targetHDGNoteVisible to set
     */
    public void setTargetHDGNoteVisible(boolean targetHDGNoteVisible) {
        this.targetHDGNoteVisible = targetHDGNoteVisible;
    }

    /**
     * @return the targetInfoVisible
     */
    public boolean isTargetInfoVisible() {
        return targetInfoVisible;
    }

    /**
     * @param targetInfoVisible the targetInfoVisible to set
     */
    public void setTargetInfoVisible(boolean targetInfoVisible) {
        this.targetInfoVisible = targetInfoVisible;
    }

    /**
     * @return the targetHeadingVisible
     */
    public boolean isTargetHeadingVisible() {
        return targetHeadingVisible;
    }

    /**
     * @param targetHeadingVisible the targetHeadingVisible to set
     */
    public void setTargetHeadingVisible(boolean targetHeadingVisible) {
        this.targetHeadingVisible = targetHeadingVisible;
    }

    /**
     * @return the targetTrackingVisible
     */
    public boolean isTargetTrackingVisible() {
        return targetTrackingVisible;
    }

    /**
     * @param targetTrackingVisible the targetTrackingVisible to set
     */
    public void setTargetTrackingVisible(boolean targetTrackingVisible) {
        this.targetTrackingVisible = targetTrackingVisible;
    }
    
    /**
     * @return the targetCldsWarning
     */
    public boolean isTargetCldsWarning() {
        return targetCldsWarning;
    }

    /**
     * @param targetCldsWarning the targetCldsWarning to set
     */
    public void setTargetCldsWarning(boolean targetCldsWarning) {
        this.targetCldsWarning = targetCldsWarning;
    }

    /**
     * @return the historyLength
     */
    public long getHistoryLength() {
        return historyLength;
    }

    /**
     * @param historyLength the historyLength to set
     */
    public void setHistoryLength(float historyLength) {
        this.historyLength = (long) (historyLength * 60 * 1000);
    }
    
    public void setAll(boolean visible) {
        this.targetCallsignVisible = visible;
        this.targetSSRVisible = visible;
        this.targetSPDVisible = visible;
        this.targetALTVisible = visible;
        this.targetCALTVisible = visible;
        this.targetCoALTVisible = visible;
        this.targetCTRLVisible = visible;
        this.targetHDGNoteVisible = visible;
        this.targetInfoVisible = visible;
        this.targetHeadingVisible = visible;
        this.targetTrackingVisible = visible;
    }

    //</editor-fold>
}
