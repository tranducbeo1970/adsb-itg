/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class CapacityStatusReportByModeS {
    private int communicationCapability;
    private int flightStatus;
    private boolean specificServiceCapability;
    private int altitudeReportingCapability;
    private boolean aircraftIdentificationCapability;
    private int b1a;
    private int b1b;
    
    public static int extract(byte[] bytes, int index, CapacityStatusReportByModeS code) {

        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte currentByte = bytes[index++];
        int val = (currentByte >> 5) & 0x07;
        code.setCommunicationCapability(val);
        val = (currentByte >> 2) & 0x07;
        code.setFlightStatus(val);
        
        currentByte = bytes[index++];
        code.setSpecificServiceCapability((currentByte & 0x80)> 0);
        code.setAltitudeReportingCapability(currentByte >> 6 & 0x01);
        code.setAircraftIdentificationCapability((currentByte & 0x20) > 0);
        code.setB1a(currentByte >> 4 & 0x01);
        code.setB1b(currentByte & 0x0F);
        return 2;
    }

    /**
     * @return the communicationCapability <br/>
     * 0 No communications capability (surveillance only) <br/>
     * 1 Comm. A and Comm. B capability <br/>
     * 2 Comm. A, Comm. B and Uplink ELM <br/>
     * 3 Comm. A, Comm. B, Uplink ELM and Downlink ELM <br/>
     * 4 Level 5 Transponder capability 5 to 7 Not assigned
     */
    public int getCommunicationCapability() {
        return communicationCapability;
    }

    /**
     * @param communicationCapability <br/>
     * 0 No communications capability (surveillance only) <br/>
     * 1 Comm. A and Comm. B capability <br/>
     * 2 Comm. A, Comm. B and Uplink ELM <br/>
     * 3 Comm. A, Comm. B, Uplink ELM and Downlink ELM <br/>
     * 4 Level 5 Transponder capability 5 to 7 Not assigned
     */
    public void setCommunicationCapability(int communicationCapability) {
        this.communicationCapability = communicationCapability;
    }

    /**
     * @return the flightStatus <br/>
     * 0 No alert, no SPI, aircraft airborne<br/>
     * 1 No alert, no SPI, aircraft on ground<br/>
     * 2 Alert, no SPI, aircraft airborne<br/>
     * 3 Alert, no SPI, aircraft on ground<br/>
     * 4 Alert, SPI, aircraft airborne or on ground<br/>
     * 5 No alert, SPI, aircraft airborne or on ground
     */
    public int getFlightStatus() {
        return flightStatus;
    }

    /**
     * @param flightStatus <br/>
     * 0 No alert, no SPI, aircraft airborne<br/>
     * 1 No alert, no SPI, aircraft on ground<br/>
     * 2 Alert, no SPI, aircraft airborne<br/>
     * 3 Alert, no SPI, aircraft on ground<br/>
     * 4 Alert, SPI, aircraft airborne or on ground<br/>
     * 5 No alert, SPI, aircraft airborne or on ground
     */
    public void setFlightStatus(int flightStatus) {
        this.flightStatus = flightStatus;
    }

    /**
     * @return the specificServiceCapability
     */
    public boolean isSpecificServiceCapability() {
        return specificServiceCapability;
    }

    /**
     * @param specificServiceCapability the specificServiceCapability to set
     */
    public void setSpecificServiceCapability(boolean specificServiceCapability) {
        this.specificServiceCapability = specificServiceCapability;
    }

    /**
     * @return the altitudeReportingCapability <br/>
     * 0 100 ft resolution<br/>
     * 1 25 ft resolution
     */
    public int getAltitudeReportingCapability() {
        return altitudeReportingCapability;
    }

    /**
     * @param altitudeReportingCapability <br/>
     * 0 100 ft resolution<br/>
     * 1 25 ft resolution
     */
    public void setAltitudeReportingCapability(int altitudeReportingCapability) {
        this.altitudeReportingCapability = altitudeReportingCapability;
    }

    /**
     * @return the aircraftIdentificationCapability
     */
    public boolean isAircraftIdentificationCapability() {
        return aircraftIdentificationCapability;
    }

    /**
     * @param aircraftIdentificationCapability the aircraftIdentificationCapability to set
     */
    public void setAircraftIdentificationCapability(boolean aircraftIdentificationCapability) {
        this.aircraftIdentificationCapability = aircraftIdentificationCapability;
    }

    /**
     * @return the b1a
     */
    public int getB1a() {
        return b1a;
    }

    /**
     * @param b1a the b1a to set
     */
    public void setB1a(int b1a) {
        this.b1a = b1a;
    }

    /**
     * @return the b1b
     */
    public int getB1b() {
        return b1b;
    }

    /**
     * @param b1b the b1b to set
     */
    public void setB1b(int b1b) {
        this.b1b = b1b;
    }
    
    
    
    
    
    
}
