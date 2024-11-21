/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author dhan
 */
public class Byter {

    private static final byte[] MASKED = new byte[]{(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02, (byte) 0x01};
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Convert length to masked byte to extract data
     *
     * @param length (must be less than an octet)
     * @return masked byte
     */
    private static int mask(int length) {
        if (length < 0 || length > 8) {
            return 0;
        }
        return 0xFF >> (8 - length);
    }

    /**
     * extract integer value from a byte
     *
     * @param b (byte to extract)
     * @param index (start bit of the byte, if take the whole byte then it
     * should be set to 0)
     * @return integer value
     */
    public static int extract(byte b, int index) {
        return (b >> index & 0x01);
    }

    public static int extract(int b, int index) {
        return (b >> index & 0x01);
    }

    /**
     * extract integer value from a byte
     *
     * @param b (byte to extract)
     * @param index (start bit of the byte, if take the whole byte then it
     * should be set to 0)
     * @param length (number of bit need to be extract)
     * @return integer value
     */
    public static int extract(byte b, int index, int length) {
        int len = mask(length);
        return b >> index & len;
    }

    public static int extract(int b, int index, int length) {
        int len = mask(length);
        return b >> index & len;
    }

    /**
     * Validate length and index of extracted byte
     *
     * @param current
     * @param length
     * @param numOfByte
     * @return valid or not
     */
    public static boolean validateIndex(int current, int length, int numOfByte) {
        if (current + numOfByte > length) {
            System.out.println(String.format("Error while reading message: Index is out of boundary of array size (index: %s)(size: %s)", (current + numOfByte), length));
            return false;
        }
        return true;
    }

    /**
     * Get value of complement number from byte array
     *
     * @param bytes (byte array)
     * @return integer value
     */
    public static int getComplementNumber(byte[] bytes) {
        int lat = bytes[0] & 0xFF;
        boolean positive = (int) (lat & 0x80) <= 0;
        int length = bytes.length;

        if (positive) {
            for (int i = 1; i < length; i++) {
                lat = lat << 8 | (bytes[i] & 0xFF);
            }
        } else {
            lat = ~lat & 0xFF;
            for (int i = 1; i < length; i++) {
                lat = lat << 8 | (~bytes[i] & 0xFF);
            }
            lat = lat + 0x01;
            lat = -lat;
        }
        return lat;
    }
    
    /**
     * Convert to byte array (4 byte)
     * @param val
     * @return 
     */
    public static byte[] getComplementNumberInByte(int val) {
        if (val > 0) {
            return new byte[]{
                (byte) (val >> 24 & 0xFF),
                (byte) (val >> 16 & 0xFF),
                (byte) (val >> 8 & 0xFF),
                (byte) (val & 0xFF)};
        }
        
        val = -val;
        val -= 0x01;
        return new byte[]{
                (byte) (~(val >> 24) & 0xFF),
                (byte) (~(val >> 16) & 0xFF),
                (byte) (~(val >> 8) & 0xFF),
                (byte) (~val & 0xFF)};
    }


    public static String toHex(byte b) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(b & 0xFF));
        if (sb.length() < 2) {
            sb.insert(0, '0'); // pad with leading zero if needed
        }
        return sb.toString().toUpperCase();
    }

    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static int decodeHeader(byte[] bytes, int index, boolean[] header) {
        boolean isExtend = true;
        int bit;
        int headerIndex = 0;
        while (isExtend) {
            byte currentByte = bytes[index];
            for (int i = 1; i < 8; i++) {
                bit = currentByte & MASKED[i - 1];
                if (bit == 0) {
                    continue;
                }
                int idx = headerIndex * 7 + i - 1;
                if (idx >= header.length) {
                    System.out.println(String.format("Error while reading header: index is out of boundary of array size. (index: %s) (size: %s)", idx, header.length));
                    return -1;
                }
                header[idx] = true;
            }

            bit = currentByte & 0x01;
            isExtend = (bit != 0);
            index++;
            headerIndex++; // Increasing readed bytes
        }
        return headerIndex;
    }
    
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }
    
    public static void set(byte b, int index) {
        
    }
    
    public static int convertOct2Dec(int val) {
        return Integer.parseInt(Integer.toString(val), 8);
    }
    
    public static int convertDec2Oct(int val) {
        return Integer.parseInt(Integer.toOctalString(val));
    }
}
