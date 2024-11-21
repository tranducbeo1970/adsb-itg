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

public class AccCartesianCoordinate extends Coordinate {
    

    @Override
    public String toString() {
        return String.format("[CartesianCoordinate || x: %s | y:%s | originX: %s | originY: %s]", x, y, x, y);
    }
    
    
    public static int extract(byte [] bytes, int index, Coordinate coordinate) {
       if (!Byter.validateIndex(index, bytes.length, 4)) return -1;

        byte[] latBytes = new byte[]{bytes[index++], bytes[index++]};
        int value = Byter.getComplementNumber(latBytes);
        coordinate.setX(value * 0.25);

        latBytes = new byte[]{bytes[index++], bytes[index++]};
        value = Byter.getComplementNumber(latBytes);
        coordinate.setY(value * 0.25);
        return 8;
    }
}
