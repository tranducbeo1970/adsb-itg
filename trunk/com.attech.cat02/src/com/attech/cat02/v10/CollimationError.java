/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat02.v10;

import java.nio.ByteBuffer;

/**
 *
 * @author hong
 */
public class CollimationError {
    private double rangeError;
    private double azimuthError;

    /**
     * @return the rangeError
     */
    public double getRangeError() {
        return rangeError;
    }

    /**
     * @param rangeError the rangeError to set
     */
    public void setRangeError(double rangeError) {
        this.rangeError = rangeError;
    }

    /**
     * @return the azimuthError
     */
    public double getAzimuthError() {
        return azimuthError;
    }

    /**
     * @param azimuthError the azimuthError to set
     */
    public void setAzimuthError(double azimuthError) {
        this.azimuthError = azimuthError;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sCollimation Error", indent));
        System.out.println(String.format("\t%sRange Error: %s", indent, rangeError));
        System.out.println(String.format("\t%sAzimuth Error: %s", indent, azimuthError));
    }
    
    public static int extract(byte[] bytes, int index, CollimationError collimationError) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, 0x00, 0x00, bytes[index++]}).getInt();
        double dvalue = (double) value / 128;
        collimationError.setRangeError(dvalue);
        
        value = ByteBuffer.wrap(new byte[]{0x00, 0x00,  0x00, bytes[index++]}).getInt();
        dvalue = (double) value * 360 / Math.pow(2, 14);
        collimationError.setAzimuthError(dvalue);
        
        return 2;
    }
    
}
