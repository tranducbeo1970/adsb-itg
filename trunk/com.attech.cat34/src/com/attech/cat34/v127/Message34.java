/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat34.v127;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hong
 */
public class Message34 {
    /*
    1 I034/010 Data Source Identifier 
    2 I034/000 Message Type
    3 I034/030 Time-of-Day
    4 I034/020 Sector Number
    5 I034/041 Antenna Rotation Period
    6 I034/050 System Configuration and Status
    7 I034/060 System Processing Mode
    */
    private Integer sic, sac;
    private Integer type;
    private Double timeOfDay;
    private Double sectorNumber;
    private Double antennaRotationSpeed;
    private SystemConfigurationAndStatus sysConfigStatus;
    private SystemProcessMode processingMode;
    
    /*
    8 I034/070 Message Count Values
    9 I034/100 Generic Polar Window
    10 I034/110 Data Filter
    11 I034/120 3D-Position of Data Source
    12 I034/090 Collimation Error
    13 RE-Data Item Reserved Expansion Field 
    14 SP-Data Item Special Purpose Field
    */
    private MessageCountValue messageCountValue;
    private DynamicWindow windows;
    private Integer dataFilter;
    private PositionOfDataSource positionDataSource;
    private CollimationError collimationError;
    
    private byte [] binary;

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
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
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
     * @return the sysConfigStatus
     */
    public SystemConfigurationAndStatus getSysConfigStatus() {
        return sysConfigStatus;
    }

    /**
     * @param sysConfigStatus the sysConfigStatus to set
     */
    public void setSysConfigStatus(SystemConfigurationAndStatus sysConfigStatus) {
        this.sysConfigStatus = sysConfigStatus;
    }

    /**
     * @return the processingMode
     */
    public SystemProcessMode getProcessingMode() {
        return processingMode;
    }

    /**
     * @param processingMode the processingMode to set
     */
    public void setProcessingMode(SystemProcessMode processingMode) {
        this.processingMode = processingMode;
    }

    /**
     * @return the messageCountValue
     */
    public MessageCountValue getMessageCountValue() {
        return messageCountValue;
    }

    /**
     * @param messageCountValue the messageCountValue to set
     */
    public void setMessageCountValue(MessageCountValue messageCountValue) {
        this.messageCountValue = messageCountValue;
    }

    /**
     * @return the windows
     */
    public DynamicWindow getWindows() {
        return windows;
    }

    /**
     * @param windows the windows to set
     */
    public void setWindows(DynamicWindow windows) {
        this.windows = windows;
    }

    /**
     * @return the dataFilter
     */
    public Integer getDataFilter() {
        return dataFilter;
    }

    /**
     * @param dataFilter the dataFilter to set
     */
    public void setDataFilter(Integer dataFilter) {
        this.dataFilter = dataFilter;
    }

    /**
     * @return the positionDataSource
     */
    public PositionOfDataSource getPositionDataSource() {
        return positionDataSource;
    }

    /**
     * @param positionDataSource the positionDataSource to set
     */
    public void setPositionDataSource(PositionOfDataSource positionDataSource) {
        this.positionDataSource = positionDataSource;
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
    
    public void print(int level) {
        System.out.println(String.format("Asterix message, #34, length %s\n", this.binary.length));
        if (sic != null) System.out.println(String.format("\tSIC: %s", sic));
        if (sac != null) System.out.println(String.format("\tSAC: %s", sac));
        if (timeOfDay != null) System.out.println(String.format("\tTime Of Day: %s", timeOfDay));
        if (type != null) System.out.println(String.format("\tMessage type: %s", getTypes(type.intValue())));
        if (sectorNumber != null) System.out.println(String.format("\tSector number: %s", sectorNumber));
        if (antennaRotationSpeed != null) System.out.println(String.format("\tAntenna rotation speed: %s", antennaRotationSpeed));
        if (sysConfigStatus != null) sysConfigStatus.print(1);
        if (processingMode != null) processingMode.print(1);
        if (messageCountValue != null) messageCountValue.print(1);
        if (windows != null) windows.print(1);
        if (dataFilter != null) System.out.println(String.format("\tData filter: %s", getDataFilters(dataFilter)));
        if (positionDataSource != null) positionDataSource.print(1);
        if (collimationError != null) collimationError.print(1);
        printBinary();
        
    }

    private String getTypes(int type) {
        switch(type) {
            case 1: return "1 (North Marker message)";
            case 2: return "2 (Sector crossing message)";
            case 3: return "3 (Geographical filtering message)";
            case 4: return "4 (Jamming Strobe message)";
            default: return String.format("%s (Unknown)", type);
        }
    }

    private String getDataFilters(int filter) {
        switch(filter) {
            case 0 : return "0 (invalid value)";
            case 1 : return "1 (Filter for Weather data)";
            case 2 : return "2 (Filter for Jamming Strobe)";
            case 3 : return "3 (Filter for PSR data)";
            case 4 : return "4 (Filter for SSR/Mode S data)";
            case 5 : return "5 (Filter for SSR/Mode S + PSR data)";
            case 6 : return "6 (Enhanced Surveillance data)";
            case 7 : return "7 (Filter for PSR+Enhanced Surveillance data)";
            case 8 : return "8 (Filter for PSR+Enhanced Surveillance + SSR/Mode S data not in Area of Prime Interest)";
            case 9 : return "9 (Filter for PSR+Enhanced Surveillance + all SSR/Mode S data)";
            default: return String.format("%s (Unknown)", type);
        }
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
