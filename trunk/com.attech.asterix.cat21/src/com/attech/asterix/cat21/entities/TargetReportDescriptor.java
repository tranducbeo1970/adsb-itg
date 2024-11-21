/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class TargetReportDescriptor {
    private boolean isDifferentialCorrection;
    private boolean isGroundBitSet;
    private boolean isSimulatedTargetReport;
    private boolean isTestTarget;
    private boolean isReportFromFieldMonitor;
    private boolean isEquipementCapable;
    private boolean isSpecialPositionIdentification;
    private short atpValue;
    private short altitudeReportingCapability;

    /**
     * @return the isDifferentialCorrection
     */
    public boolean isIsDifferentialCorrection() {
        return isDifferentialCorrection;
    }

    /**
     * @param isDifferentialCorrection the isDifferentialCorrection to set
     */
    public void setIsDifferentialCorrection(boolean isDifferentialCorrection) {
        this.isDifferentialCorrection = isDifferentialCorrection;
    }

    /**
     * @return the isGroundBitSet
     */
    public boolean isIsGroundBitSet() {
        return isGroundBitSet;
    }

    /**
     * @param isGroundBitSet the isGroundBitSet to set
     */
    public void setIsGroundBitSet(boolean isGroundBitSet) {
        this.isGroundBitSet = isGroundBitSet;
    }

    /**
     * @return the isSimulatedTargetReport
     */
    public boolean isIsSimulatedTargetReport() {
        return isSimulatedTargetReport;
    }

    /**
     * @param isSimulatedTargetReport the isSimulatedTargetReport to set
     */
    public void setIsSimulatedTargetReport(boolean isSimulatedTargetReport) {
        this.isSimulatedTargetReport = isSimulatedTargetReport;
    }

    /**
     * @return the isTestTarget
     */
    public boolean isIsTestTarget() {
        return isTestTarget;
    }

    /**
     * @param isTestTarget the isTestTarget to set
     */
    public void setIsTestTarget(boolean isTestTarget) {
        this.isTestTarget = isTestTarget;
    }

    /**
     * @return the isReportFromFieldMonitor
     */
    public boolean isIsReportFromFieldMonitor() {
        return isReportFromFieldMonitor;
    }

    /**
     * @param isReportFromFieldMonitor the isReportFromFieldMonitor to set
     */
    public void setIsReportFromFieldMonitor(boolean isReportFromFieldMonitor) {
        this.isReportFromFieldMonitor = isReportFromFieldMonitor;
    }

    /**
     * @return the isEquipementCapable
     */
    public boolean isIsEquipementCapable() {
        return isEquipementCapable;
    }

    /**
     * @param isEquipementCapable the isEquipementCapable to set
     */
    public void setIsEquipementCapable(boolean isEquipementCapable) {
        this.isEquipementCapable = isEquipementCapable;
    }

    /**
     * @return the isSpecialPositionIdentification
     */
    public boolean isIsSpecialPositionIdentification() {
        return isSpecialPositionIdentification;
    }

    /**
     * @param isSpecialPositionIdentification the isSpecialPositionIdentification to set
     */
    public void setIsSpecialPositionIdentification(boolean isSpecialPositionIdentification) {
        this.isSpecialPositionIdentification = isSpecialPositionIdentification;
    }

    /**
     * @return the atpValue
     */
    public short getAtpValue() {
        return atpValue;
    }

    /**
     * @param atpValue the atpValue to set
     */
    public void setAtpValue(short atpValue) {
        this.atpValue = atpValue;
    }

    /**
     * @return the altitudeReportingCapability
     */
    public short getAltitudeReportingCapability() {
        return altitudeReportingCapability;
    }

    /**
     * @param altitudeReportingCapability the altitudeReportingCapability to set
     */
    public void setAltitudeReportingCapability(short altitudeReportingCapability) {
        this.altitudeReportingCapability = altitudeReportingCapability;
    }
    
    public String toString() {
        return "TargetReportDes test:" + this.isTestTarget + " difCorrect:" + this.isDifferentialCorrection + " fileMirr:" + this.isReportFromFieldMonitor + " isSpecialPos:" + this.isSpecialPositionIdentification + " aptValue:" + this.atpValue + " AltReport:" + this.altitudeReportingCapability;
    }
}
