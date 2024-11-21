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
public class TargetSizeOrientation {
    private int length;
    private double orientation;
    private int width;
    
    public static int extract(byte [] bytes, int index, TargetSizeOrientation altitude) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        altitude.setLength((cbyte & 0x7F) >> 1);
        if ((cbyte & 0x01) == 0) return 1;
        
        cbyte = bytes[index++];
        int ival = (cbyte & 0x7F) >> 1;
        altitude.setOrientation(ival * 360 / 128);
        if ((cbyte & 0x01) == 0) return 2;
        
        
        cbyte = bytes[index++];
        altitude.setOrientation((cbyte & 0x7F) >> 1);
        if ((cbyte & 0x01) == 0) return 3;
        
        int count = 3;
        while ((cbyte & 0x01) > 0) {
            count ++;
            cbyte = bytes[index++];
        }
        return count;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the orientation
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }
}
