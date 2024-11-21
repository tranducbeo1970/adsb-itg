/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

/**
 *
 * @author dhan
 */
public class Byter {
    
    /**
     * Convert length to masked byte to extract data
     * @param length (must be less than an octet)
     * @return masked byte
     */
    private static int mask(int length) {
        if (length < 0 || length > 8) return 0;
        return 0xFF >> (8-length);
    }
    
    /**
     * extract integer value from a byte
     * @param b (byte to extract)
     * @param index (start bit of the byte, if take the whole byte then it should be set to 0)
     * @return integer value
     */
    public static int extract(byte b, int index) {
        return (b >> index & 0x01);
    }
    
    /**
     * extract integer value from a byte
     * @param b (byte to extract)
     * @param index (start bit of the byte, if take the whole byte then it should be set to 0)
     * @param length (number of bit need to be extract)
     * @return integer value
     */
    public static int extract(byte b, int index, int length) {
        int len = mask(length);
        return b >> index & len;
    }
    
    /**
     * Validate length and index of extracted byte
     * @param current
     * @param length
     * @param numOfByte
     * @return valid or not
     */
    public static boolean validateIndex(int current, int length, int numOfByte) {
        if (current + numOfByte > length) {
            // System.out.println(String.format("Error while reading message: Index is out of boundary of array size (index: %s)(size: %s)", (current + numOfByte), length));
            // throw new IndexOutOfBoundsException("Index " + current + " size " + length);
            return false;
        }
        return true;
    }
    
    /**
     * Get value of complement number from byte array
     * @param bytes (byte array)
     * @return integer value
     */
    public static int getComplementNumber (byte[] bytes) {
        int lat = bytes[0] & 0xFF;
        boolean positive = (int) (lat & 0x80) <= 0;
        int length = bytes.length;
        
        if (positive) {
            for (int i = 1; i < length; i++) lat = lat << 8 | (bytes[i] & 0xFF);
        } else {
            lat = ~lat & 0xFF;
            for (int i = 1; i < length; i++) lat = lat << 8 | (~bytes[i] & 0xFF);
            lat = lat + 0x01;
            lat = -lat;
        }
        return lat;
    }
}
