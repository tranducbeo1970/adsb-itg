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

public class PosCartesianCoordinate extends Coordinate {
    

    @Override
    public String toString() {
        return String.format("[CartesianCoordinate || x: %s | y:%s | originX: %s | originY: %s]", x, y, x, y);
    }
    
    
    public static int decode(byte [] bytes, int index, Coordinate coordinate) {
        if (!Byter.validateIndex(index, bytes.length, 6)) return -1;
        int value = getComplementNumber(bytes[index++], bytes[index++], bytes[index++]);
        coordinate.setX(value * 0.5);
        value = getComplementNumber(bytes[index++], bytes[index++], bytes[index++]);
        coordinate.setY(value * 0.5);
        return 8;
    }
    
    public static byte [] encode(Coordinate coordinate) {
        final byte [] bytes = new byte[6];
        int valX = (int) (coordinate.x / 0.5);
        int valY = (int) (coordinate.y / 0.5);
        byte [] rslt = getComplementNumberInByte(valX);
        System.arraycopy(rslt, 0, bytes, 0, 3);
        rslt = getComplementNumberInByte(valY);
        System.arraycopy(rslt, 0, bytes, 3, 3);
        return bytes;
    }
    
    public static int getComplementNumber(byte b1, byte b2, byte b3) {
        final boolean positive = (b1 & 0x80) == 0;
        if (positive) return (b1 & 0xFF) << 16 | (b2 & 0xFF) << 8 | (b3 & 0xFF);
        return -((~b1 & 0xFF) << 16 | (~b2 & 0xFF) << 8 | (~b3 & 0xFF) + 0x01);
    }
    
    public static byte[] getComplementNumberInByte(int val) {
        if (val > 0) {
            return new byte[]{
                (byte) (val >> 16 & 0xFF),
                (byte) (val >> 8 & 0xFF),
                (byte) (val & 0xFF)};
        }
        
        val = -val;
        val -= 0x01;
        return new byte[]{
                (byte) (~(val >> 16) & 0xFF),
                (byte) (~(val >> 8) & 0xFF),
                (byte) (~val & 0xFF)};
    }
}
