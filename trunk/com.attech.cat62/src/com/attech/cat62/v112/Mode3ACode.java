/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat62.v112;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Mode3A")
@XmlAccessorType(XmlAccessType.NONE)
public class Mode3ACode {
    
    private boolean changed;
    private int value;
    
    public Mode3ACode(){
    }
    
    public Mode3ACode(int value) {
        this.value = value;
    }
    
    /**
     * Extract mode 3A code from byte array
     * @param bytes (byte array data)
     * @param index (start index of the array)
     * @param code (stored object)
     * @return number of read byte
     */
    public static int decode(byte [] bytes, int index, Mode3ACode code){
        
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte currentByte = bytes[index++];
        code.setChanged((currentByte & 0x20) > 0);
        int val = (currentByte & 0x0F << 8) | (bytes[index++] & 0xFF);
        code.setValue(Integer.parseInt(Integer.toOctalString(val)));
        return 2;
    }
    
    public byte [] encode() {
        // byte [] bytes = new byte[2];
        // byte b1 =  changed ? (byte) (0x20) : 0x00;
        int decVal = Byter.convertOct2Dec(value);
        byte b1 = (byte) ((decVal >> 8 & 0x0F) | (changed ? 0x20 : 0x00));
        //bytes[0] = (byte) (bytes[0] & (decVal >> 8 & 0x0F));
        byte b2 = (byte) (decVal & 0xFF);
        return new byte[] {b1, b2};
    }
    
    @Override
    public String toString() {
//        String validatedS = this.notValidated ? "Not-validated" : "Validated";
//        String garbedS = this.garbed ? "Garbed-code" : "Non-garbed-code";
//        String smoothS = this.localTracker ? "transponder": "local";
//        return String.format("[Mode3ACode || %s | %s | %s | Value: %s]", 
//                                validatedS, 
//                                garbedS, 
//                                smoothS, 
//                                this.getValue());
        return "";
    }

    /**
     * @return the changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * @param changed the changed to set
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
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
