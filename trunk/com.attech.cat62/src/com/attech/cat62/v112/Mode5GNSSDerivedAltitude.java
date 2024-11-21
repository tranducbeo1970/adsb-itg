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
public class Mode5GNSSDerivedAltitude {
    private int resolution;
    private int gnssAltitude;
    
    public static int extract(byte[] bytes, int index, Mode5GNSSDerivedAltitude code) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte cbyte = bytes[index++];
        code.setResolution(cbyte >> 6 & 0x01);
        int val = getComplementNumber(cbyte, bytes[index++]);
        code.setGnssAltitude(val * 25);
        return 2;
    }
    
    private static int getComplementNumber(byte byte1, byte byte2) {
        boolean positive = (int) (byte1 & 0x20) == 0;
        return (positive) ? (byte1 & 0x3F) << 8 | (byte2 & 0xFF) : 
                -(((~byte1 & 0x3F) << 8 | (~byte2 & 0xFF)) + 0x01);
    }

    /**
     * @return the resolution <br/>
     * 0 GA reported in 100 ft increments, <br/>
     * 1 GA reported in 25 ft increments.
     */
    public int getResolution() {
        return resolution;
    }

    /**
     * @param resolution  <br/>
     * 0 GA reported in 100 ft increments, <br/>
     * 1 GA reported in 25 ft increments.
     */
    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    /**
     * @return the gnssAltitude
     */
    public int getGnssAltitude() {
        return gnssAltitude;
    }

    /**
     * @param gnssAltitude the gnssAltitude to set
     */
    public void setGnssAltitude(int gnssAltitude) {
        this.gnssAltitude = gnssAltitude;
    }
}
