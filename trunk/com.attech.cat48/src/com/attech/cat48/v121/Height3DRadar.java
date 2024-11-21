/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "ModeC")
@XmlAccessorType(XmlAccessType.NONE)
public class Height3DRadar {
    
    @XmlAttribute(name = "value")
    private int value;
    
    public static int extract(byte [] bytes, int index, Height3DRadar code) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte currentByte = bytes[index++];
        code.setValue(getModeCHeight(currentByte, bytes[index++]));
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
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sFlightLevel: %s", indent, getValue()));
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
