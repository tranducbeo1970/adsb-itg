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
public class ModeCCode {
    private boolean notValidated;
    private boolean garbled;
    private double value;

    public static int extract(byte [] bytes, int index, ModeCCode code){
        
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte cbyte = bytes[index++];
        code.setNotValidated((cbyte & 0x80) > 0);
        code.setGarbled((cbyte & 0x40) > 0);
        int val = getComplementNumber(cbyte, bytes[index++]);
        code.setValue(val * 0.25);
        return 2;
    }
    
    private static int getComplementNumber(byte byte1, byte byte2) {
        boolean positive = (int) (byte1 & 0x3F) == 0;
        return (positive) ? (byte1 & 0x3F) << 8 | (byte2 & 0xFF) : 
                -(((~byte1 & 0x3F) << 8 | (~byte2 & 0xFF)) + 0x01);
    }
    
    /**
     * @return the notValidated
     */
    public boolean isNotValidated() {
        return notValidated;
    }

    /**
     * @param notValidated the notValidated to set
     */
    public void setNotValidated(boolean notValidated) {
        this.notValidated = notValidated;
    }

    /**
     * @return the garbled
     */
    public boolean isGarbled() {
        return garbled;
    }

    /**
     * @param garbled the garbled to set
     */
    public void setGarbled(boolean garbled) {
        this.garbled = garbled;
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
