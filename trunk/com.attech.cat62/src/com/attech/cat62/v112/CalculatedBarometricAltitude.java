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
public class CalculatedBarometricAltitude {
    private boolean applyQNH;
    private double value;
    
    public static int extract(byte [] bytes, int index, CalculatedBarometricAltitude altitude) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte cbyte = bytes[index++];
        altitude.setApplyQNH((cbyte & 0x80) > 0);
        
        int ival = getComplementNumber(cbyte, bytes[index++]);
        altitude.setValue(ival * 25);
        return 2;
    }
    
    private static int getComplementNumber(byte byte1, byte byte2) {
        boolean positive = (int) (byte1 & 0x40) == 0;
        return (positive) ? (byte1 & 0x7F) << 8 | (byte2 & 0xFF)
                : -(((~byte1 & 0x7F) << 8 | (~byte2 & 0xFF)) + 0x01);
    }

    /**
     * @return the applyQNH
     */
    public boolean isApplyQNH() {
        return applyQNH;
    }

    /**
     * @param applyQNH the applyQNH to set
     */
    public void setApplyQNH(boolean applyQNH) {
        this.applyQNH = applyQNH;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
    
}
