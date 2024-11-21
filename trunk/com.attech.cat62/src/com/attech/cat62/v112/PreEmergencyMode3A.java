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
public class PreEmergencyMode3A {
    private boolean available;
    private int value;
    
    public static int decode(byte [] bytes, int index, PreEmergencyMode3A code){
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte cbyte = bytes[index++];
        code.setAvailable((cbyte & 0x10) > 0);
        code.setValue((cbyte & 0x0F) << 8 | (bytes[index++] & 0xFF));
        return 2;
    }
    
    public byte[] encode() {
        byte b1 = (byte) ((available ? 0x10 : 0x00) | (value >> 8 & 0x0F));
        byte b2 = (byte) (value & 0xFF);
        return new byte[]{b1, b2};
    }

    /**
     * @return the available
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
}
