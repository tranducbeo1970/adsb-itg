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
public class StatusReportedByADSB {
    private int acasOperational;
    private int multiNavigational;
    private int differenceCorrection;
    private boolean transponderGroundBitSet;
    private int flightStatus;
    
    public static int extract(byte[] bytes, int index, StatusReportedByADSB code) {

        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte currentByte = bytes[index++];
        code.setAcasOperational(currentByte >> 6 & 0x03);
        code.setMultiNavigational(currentByte >> 4 & 0x03);
        code.setDifferenceCorrection(currentByte >> 2 & 0x03);
        code.setTransponderGroundBitSet((currentByte & 0x02) > 0);
        
        currentByte = bytes[index++];
        code.setFlightStatus(currentByte & 0x07);
        return 2;
    }

    /**
     * @return the acasOperational <br/>
     * 00 (0) = unknown <br/>
     * 01 (1) = ACAS not operational <br/>
     * 10 (2) = ACAS operational <br/>
     * 11 (3) = invalid <br/>
     */
    public int getAcasOperational() {
        return acasOperational;
    }

    /**
     * @param acasOperational <br/>
     * 00 (0) = unknown <br/>
     * 01 (1) = ACAS not operational <br/>
     * 10 (2) = ACAS operational <br/>
     * 11 (3) = invalid <br/>
     */
    public void setAcasOperational(int acasOperational) {
        this.acasOperational = acasOperational;
    }

    /**
     * @return the multiNavigational <br/>
     * 00 = unknown<br/>
     * 01 = Multiple navigational aids not operating <br/>
     * 10 = Multiple navigational aids operating <br/>
     * 11 = invalid
     */
    public int getMultiNavigational() {
        return multiNavigational;
    }

    /**
     * @param multiNavigational <br/>
     * 00 = unknown<br/>
     * 01 = Multiple navigational aids not operating <br/>
     * 10 = Multiple navigational aids operating <br/>
     * 11 = invalid
     */
    public void setMultiNavigational(int multiNavigational) {
        this.multiNavigational = multiNavigational;
    }

    /**
     * @return the differenceCorrection <br/>
     * 00 = unknown <br/>
     * 01 = Differential correction <br/>
     * 10 = No differential correction <br/>
     * 11 = invalid
     */
    public int getDifferenceCorrection() {
        return differenceCorrection;
    }

    /**
     * @param differenceCorrection  <br/>
     * 00 = unknown <br/>
     * 01 = Differential correction <br/>
     * 10 = No differential correction <br/>
     * 11 = invalid
     */
    public void setDifferenceCorrection(int differenceCorrection) {
        this.differenceCorrection = differenceCorrection;
    }

    /**
     * @return the transponderGroundBitSet
     */
    public boolean isTransponderGroundBitSet() {
        return transponderGroundBitSet;
    }

    /**
     * @param transponderGroundBitSet the transponderGroundBitSet to set
     */
    public void setTransponderGroundBitSet(boolean transponderGroundBitSet) {
        this.transponderGroundBitSet = transponderGroundBitSet;
    }

    /**
     * @return the flightStatus <br/>
     * = 0 No emergency <br/>
     * = 1 General emergency <br/>
     * = 2 Lifeguard / medical <br/>
     * = 3 Minimum fuel <br/>
     * = 4 No communications <br/>
     * = 5 Unlawful interference <br/>
     * = 6 “Downed” Aircraft <br/>
     * = 7 Unknown
     */
    public int getFlightStatus() {
        return flightStatus;
    }

    /**
     * @param flightStatus  <br/>
     * = 0 No emergency <br/>
     * = 1 General emergency <br/>
     * = 2 Lifeguard / medical <br/>
     * = 3 Minimum fuel <br/>
     * = 4 No communications <br/>
     * = 5 Unlawful interference <br/>
     * = 6 “Downed” Aircraft <br/>
     * = 7 Unknown
     */
    public void setFlightStatus(int flightStatus) {
        this.flightStatus = flightStatus;
    }
}
