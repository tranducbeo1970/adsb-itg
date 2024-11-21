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
public class VeloCartesianCoordinate extends Coordinate {

    @Override
    public String toString() {
        return String.format("[CartesianCoordinate || x: %s | y:%s | originX: %s | originY: %s]", x, y, x, y);
    }

    public static int decode(byte[] bytes, int index, Coordinate coordinate) {
        if (!Byter.validateIndex(index, bytes.length, 4)) {
            return -1;
        }

        byte[] latBytes = new byte[]{bytes[index++], bytes[index++]};
        int value = Byter.getComplementNumber(latBytes);
        coordinate.setX(value * 0.25);

        latBytes = new byte[]{bytes[index++], bytes[index++]};
        value = Byter.getComplementNumber(latBytes);
        coordinate.setY(value * 0.25);
        return 4;
    }

    public static byte[] encode(Coordinate coordinate) {
        final byte[] bytes = new byte[6];
        int valX = (int) (coordinate.x / 0.25);
        int valY = (int) (coordinate.y / 0.25);
        byte[] rslt = getComplementNumberInByte(valX);
        System.arraycopy(rslt, 0, bytes, 0, 2);
        rslt = getComplementNumberInByte(valY);
        System.arraycopy(rslt, 0, bytes, 2, 2);
        return bytes;
    }

    public static byte[] getComplementNumberInByte(int val) {
        if (val > 0) {
            return new byte[]{(byte) (val >> 8 & 0xFF), (byte) (val & 0xFF)};
        }
        val = -(val + 0x01);
        return new byte[]{(byte) (~(val >> 8) & 0xFF), (byte) (~val & 0xFF)};
    }
}
