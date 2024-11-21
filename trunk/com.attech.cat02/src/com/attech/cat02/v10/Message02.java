/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat02.v10;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;

/**
 *
 * @author hong
 */
public class Message02 {
    /*
    1 I002/010 Data Source Identifier
    2 I002/000 Message Type
    3 I002/020 Sector Number
    4 I002/030 Time of Day
    5 I002/041 Antenna Rotation Period
    6 I002/050 Station Configuration Status
    7 I002/060 Station Processing Mode
    FX - Field Extension Indicator
    */
    private Integer sic;
    private Integer sac;

    /*
    - 001, North marker message;
    - 002, Sector crossing message;
    - 003, South marker message;
    - 008, Activation of blind zone filtering;
    - 009, Stop of blind zone filtering.
    */
    private Integer mesageType;
    private Double sectorNumber;
    private Double timeOfDay;
    private Double antennaRotationSpeed;
    private Integer stationConfigurationStatus;
    private Integer stationProcessingMode;
    
    
    
    /*
    8 I002/070 Plot Count Values
    9 I002/100 Dynamic Window - Type 1
    10 I002/090 Collimation Error
    11 I002/080 Warning/Error Conditions
    12 - Spare
    13 - Reserved for SP Indicator
    14 - Reserved for RFS Indicator (RS-bit)
    FX - Field Extension Indicator (set to 0)
    */
    private PlotCountValues plotcountValue;
    private DynamicWindow dynamicWindow;
    private CollimationError collimationError;
    private WarningErrorCondition warningConditionError;
    
    private byte[] binary;

    /**
     * @return the sic
     */
    public Integer getSic() {
        return sic;
    }

    /**
     * @param sic the sic to set
     */
    public void setSic(Integer sic) {
        this.sic = sic;
    }

    /**
     * @return the sac
     */
    public Integer getSac() {
        return sac;
    }

    /**
     * @param sac the sac to set
     */
    public void setSac(Integer sac) {
        this.sac = sac;
    }

    /**
     * @return the mesageType
     */
    public Integer getMesageType() {
        return mesageType;
    }

    /**
     * @param mesageType the mesageType to set
     */
    public void setMesageType(Integer mesageType) {
        this.mesageType = mesageType;
    }

    /**
     * @return the sectorNumber
     */
    public Double getSectorNumber() {
        return sectorNumber;
    }

    /**
     * @param sectorNumber the sectorNumber to set
     */
    public void setSectorNumber(Double sectorNumber) {
        this.sectorNumber = sectorNumber;
    }

    /**
     * @return the timeOfDay
     */
    public Double getTimeOfDay() {
        return timeOfDay;
    }

    /**
     * @param timeOfDay the timeOfDay to set
     */
    public void setTimeOfDay(Double timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    /**
     * @return the antennaRotationSpeed
     */
    public Double getAntennaRotationSpeed() {
        return antennaRotationSpeed;
    }

    /**
     * @param antennaRotationSpeed the antennaRotationSpeed to set
     */
    public void setAntennaRotationSpeed(Double antennaRotationSpeed) {
        this.antennaRotationSpeed = antennaRotationSpeed;
    }

    /**
     * @return the stationConfigurationStatus
     */
    public Integer getStationConfigurationStatus() {
        return stationConfigurationStatus;
    }

    /**
     * @param stationConfigurationStatus the stationConfigurationStatus to set
     */
    public void setStationConfigurationStatus(Integer stationConfigurationStatus) {
        this.stationConfigurationStatus = stationConfigurationStatus;
    }

    /**
     * @return the stationProcessingMode
     */
    public Integer getStationProcessingMode() {
        return stationProcessingMode;
    }

    /**
     * @param stationProcessingMode the stationProcessingMode to set
     */
    public void setStationProcessingMode(Integer stationProcessingMode) {
        this.stationProcessingMode = stationProcessingMode;
    }

    /**
     * @return the plotcountValue
     */
    public PlotCountValues getPlotcountValue() {
        return plotcountValue;
    }

    /**
     * @param plotcountValue the plotcountValue to set
     */
    public void setPlotcountValue(PlotCountValues plotcountValue) {
        this.plotcountValue = plotcountValue;
    }

    /**
     * @return the dynamicWindow
     */
    public DynamicWindow getDynamicWindow() {
        return dynamicWindow;
    }

    /**
     * @param dynamicWindow the dynamicWindow to set
     */
    public void setDynamicWindow(DynamicWindow dynamicWindow) {
        this.dynamicWindow = dynamicWindow;
    }

    /**
     * @return the collimationError
     */
    public CollimationError getCollimationError() {
        return collimationError;
    }

    /**
     * @param collimationError the collimationError to set
     */
    public void setCollimationError(CollimationError collimationError) {
        this.collimationError = collimationError;
    }

    /**
     * @return the warningConditionError
     */
    public WarningErrorCondition getWarningConditionError() {
        return warningConditionError;
    }

    /**
     * @param warningConditionError the warningConditionError to set
     */
    public void setWarningConditionError(WarningErrorCondition warningConditionError) {
        this.warningConditionError = warningConditionError;
    }

    /**
     * @return the binary
     */
    public byte[] getBinary() {
        return binary;
    }

    /**
     * @param binary the binary to set
     */
    public void setBinary(byte[] binary) {
        this.binary = binary;
    }
    
    public void print() {
        
        System.out.println(String.format("Asterix message, #2, length %s\n", this.binary.length));
        if (sic != null) System.out.println(String.format("\tSIC: %s", sic));
        if (sac != null) System.out.println(String.format("\tSAC: %s", sac));
        if (mesageType != null) System.out.println(String.format("\tMessage Type: %s", mesageType));
        if (sectorNumber != null) System.out.println(String.format("\tSector No: %s", sectorNumber));
        if (timeOfDay != null) System.out.println(String.format("\tTime of day: %s", timeOfDay));
        if (antennaRotationSpeed != null) System.out.println(String.format("\tAntenna Rotaion Speed: %s", antennaRotationSpeed));
        if (stationConfigurationStatus != null) System.out.println(String.format("\tStation configuration Status: %s", stationConfigurationStatus));
        if (stationProcessingMode != null) System.out.println(String.format("\tStation configuration Mode: %s", stationProcessingMode));
        if (plotcountValue != null) plotcountValue.print(1);
        if (dynamicWindow != null) dynamicWindow.print(1);
        if (collimationError != null) collimationError.print(1);
        if (warningConditionError != null) warningConditionError.print(1);
        printBinary() ;
    }
    
    public void printBinary() {
        System.out.println("Binary: " + this.binary.length);
        for (int i=0; i<this.binary.length; i++) {
            System.out.print(" " + Integer.toHexString(this.binary[i] & 0xFF));
            if (i%8 == 0 && i != 0) {
                System.out.println("\t");
            }
        }
        System.out.println("\t");
        
    }
    
    public Object[] getValueArray() throws IllegalArgumentException, IllegalAccessException{
        List<Object> list = new ArrayList<>();
        for (Field field: this.getClass().getDeclaredFields()){
            if (field.getName().equals("binary")){
                list.add("");
                continue;
            }
            list.add(field.get(this));
        }
        return list.toArray();
    }
}
