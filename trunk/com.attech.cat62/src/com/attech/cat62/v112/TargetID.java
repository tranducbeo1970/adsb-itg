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
public class TargetID {

    public int mode;
    public String value;
    
    public TargetID() {
    }
    
    public TargetID(int mode, String value) {
        this.mode = mode;
        this.value = value;
    }
    
    public static int decode(byte [] bytes, int index, TargetID code){
        if (!Byter.validateIndex(index, bytes.length, 7)) return -1;
        byte currentByte = bytes[index++];
        code.setMode(currentByte >> 6 & 0x03);
        final StringBuilder builder = new StringBuilder();
        builder.append(getCode(new byte[]{bytes[index++], bytes[index++], bytes[index++]}));
        builder.append(getCode(new byte[]{bytes[index++], bytes[index++], bytes[index++]}));
        code.setValue(builder.toString());
        return 7;
    }
    
    public byte [] encode() {
        byte b1 = (byte) (this.mode << 6);
        int c0 = getCharCode(0);
        int c1 = getCharCode(1);
        int c2 = getCharCode(2);
        int c3 = getCharCode(3);
        int c4 = getCharCode(4);
        int c5 = getCharCode(5);
        int c6 = getCharCode(6);
        int c7 = getCharCode(7);
        byte b2 = (byte) ((c0 << 2 ) | (c1 >> 4 & 0x03));
        byte b3 = (byte) ((c1 << 4 ) | (c2 >> 2 & 0x0F));
        byte b4 = (byte) ((c2 << 6 ) | (c3 & 0x3F));
        byte b5 = (byte) ((c4 << 2 ) | (c5 >> 4 & 0x03));
        byte b6 = (byte) ((c5 << 4 ) | (c6 >> 2 & 0x0F));
        byte b7 = (byte) ((c6 << 6 ) | (c7 & 0x3F));
        return new byte[]{b1, b2, b3, b4, b5, b6, b7};
    }
    
    private static String getCode(byte[] byts) {
        final StringBuilder builder = new StringBuilder();
        
        int code = byts[0] >> 2 & 0x3F;
        if (code != 0) builder.append(getChar(code));

        code = (byts[0] & 0x03) << 4 | (byts[1] >> 4 & 0x0F);
        if (code != 0) builder.append(getChar(code));
        
        code = (byts[1] & 0x0F) << 2 | (byts[2] >> 6 & 0x03);
        if (code != 0) builder.append(getChar(code));

        code = byts[2] & 0x3F;
        if (code != 0) builder.append(getChar(code));
        return builder.toString();
    }
    
    public static char getChar(int i) {
        if (i <=26) return (char) (i+64);
        if (i==32) return ' ';
        return (char) i;
    }
    
    public static int getCharIndex(char c) {
        int val = (int) c;
        if (val >= 64) val -= 64;
        return val;
    }
    
    public int getCharCode(int index) {
        if (value.isEmpty() || value.length() < (index + 1) || index < 0) {
            return 32;
        }
        return getCharIndex(value.charAt(index));
    }

    /**
     * @return the mode <br/>
     * 00 (0) Callsign or registration downlinked from target<br/>
     * 01 (1) Callsign not downlinked from target<br/>
     * 10 (2) Registration not downlinked from target<br/>
     * 11 (3) Invalid
     */
    public int getMode() {
        return mode;
    }

    /**
     * @param mode<br/>
     * 00 (0) Callsign or registration downlinked from target<br/>
     * 01 (1) Callsign not downlinked from target<br/>
     * 10 (2) Registration not downlinked from target<br/>
     * 11 (3) Invalid
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    

  

}
