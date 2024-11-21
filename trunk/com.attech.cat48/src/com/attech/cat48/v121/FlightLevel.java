/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "ModeC")
@XmlAccessorType(XmlAccessType.NONE)
public class FlightLevel extends Mode2Code{
    
    public static int extract(byte [] bytes, int index, FlightLevel code) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte currentByte = bytes[index++];
        
        boolean bVal = Byter.extract(currentByte, 7, 1) > 0;
        code.setNotValidated(bVal);
        
        bVal = Byter.extract(currentByte, 6, 1) > 0;
        code.setGarbedCode(bVal);
        
        int iVal = getModeCHeight(currentByte, bytes[index++]);
        code.setValue(iVal);
        return 2;
    }
    
    private static int getModeCHeight(byte byte1, byte byte2) {
        int lat = byte1 & 0x3F;
        boolean positive = (int) (lat & 0x20) == 0;
        if (positive) {
            lat = lat << 8 | (byte2 & 0xFF);
        } else {
            lat = ~lat & 0xFF;
            lat = lat << 8 | (~byte2 & 0xFF);
            lat = lat + 0x01;
            lat = -lat;
        }
        return lat * 25;
    }
    
    @Override
    public String toString() {
        String validatedS = this.notValidated ? "Not-validated" : "Validated";
        String garbedS = this.garbedCode ? "Garbed-code" : "Non-garbed-code";
        String smoothS = this.localTracker ? "Smoothed-local-track": "Not-smooth-local-track";
        return String.format("[ModeC || %s | %s | %s | Value: %s]", 
                                validatedS, 
                                garbedS, 
                                smoothS, 
                                this.getValue());
    }
    
    @Override
    public void print(int level) {
        String validated = this.notValidated ? "Not validated(1)" : "Validated(0)";
        String garbed = this.garbedCode ? "Garbed(1)" : "Not garbed(0)";
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sFlightLevel", indent));
        System.out.println(String.format("\t%s%s", indent, validated));
        System.out.println(String.format("\t%s%s", indent, garbed));
        System.out.println(String.format("\t%sCode: %s", indent, this.getValue()));
    }
}
