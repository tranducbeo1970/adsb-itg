/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class ModeSMBData {
    private int repetitionFactor;
    private byte[] message;
    private int bds1;
    private int bds2;
    
    
    public ModeSMBData(int length) {
        message = new byte[length];
    }
    
    public ModeSMBData() {
        this(7);
    }
    
    public static int extract(byte [] bytes, int index, ModeSMBData modeSdata) {
        if (!Byter.validateIndex(index, bytes.length, 9)) return -1;
        
        modeSdata.setRepetitionFactor(bytes[index++] & 0xFF);
        modeSdata.setMessage(0, bytes[index++]);
        modeSdata.setMessage(1, bytes[index++]);
        modeSdata.setMessage(2, bytes[index++]);
        modeSdata.setMessage(3, bytes[index++]);
        modeSdata.setMessage(4, bytes[index++]);
        modeSdata.setMessage(5, bytes[index++]);
        modeSdata.setMessage(6, bytes[index++]);
        
        byte cbyte = bytes[index++];
        modeSdata.setBds1(cbyte >> 4 & 0x0F);
        modeSdata.setBds2(cbyte & 0x0F);
        return 9;
    }

    /**
     * @return the repetitionFactor
     */
    public int getRepetitionFactor() {
        return repetitionFactor;
    }

    /**
     * @param repetitionFactor the repetitionFactor to set
     */
    public void setRepetitionFactor(int repetitionFactor) {
        this.repetitionFactor = repetitionFactor;
    }

    /**
     * @return the message
     */
    public byte[] getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(byte[] message) {
        this.message = message;
    }
    
     public void setMessage(int index, byte bt) {
         if (index < 0 || index >= message.length) return;
        this.message[index] = bt;
    }

  
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
//        builder.append("BDS1: ").append(this.getBDS1()).append(" ");
//        builder.append("BDS2: ").append(this.getBDS2()).append(" ");
//        builder.append("Message: ").append(this.getMessage()).append(" ");
//        builder.append("Repetition Factor: ").append(this.getRepetitionFactor());
        return builder.toString();
    }

    /**
     * @return the bds1
     */
    public int getBds1() {
        return bds1;
    }

    /**
     * @param bds1 the bds1 to set
     */
    public void setBds1(int bds1) {
        this.bds1 = bds1;
    }

    /**
     * @return the bds2
     */
    public int getBds2() {
        return bds2;
    }

    /**
     * @param bds2 the bds2 to set
     */
    public void setBds2(int bds2) {
        this.bds2 = bds2;
    }
}
