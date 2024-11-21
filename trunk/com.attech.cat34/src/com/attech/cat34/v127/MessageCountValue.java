/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat34.v127;

/**
 *
 * @author andh
 */
public class MessageCountValue {
    
    private int numberOfCount;
    private int type;
    private int counter;

    /**
     * @return the numberOfCount
     */
    public int getNumberOfCount() {
        return numberOfCount;
    }

    /**
     * @param numberOfCount the numberOfCount to set
     */
    public void setNumberOfCount(int numberOfCount) {
        this.numberOfCount = numberOfCount;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    public static int extract(byte [] bytes, int index, MessageCountValue messageCountValue) {
        if (!Byter.validateIndex(index, bytes.length, 3)) return -1;
        byte b = bytes[index++];
        messageCountValue.setNumberOfCount(b & 0xFF);
        
        b = bytes[index++];
        messageCountValue.setNumberOfCount(Byter.extract(b, 3, 5));
        messageCountValue.setNumberOfCount((b & 0x07) << 8 | bytes[index++]);
        return 3;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sMessage Count Value", indent));
        System.out.println(String.format("\t%sNumber of counters following: %s", indent, numberOfCount));
        System.out.println(String.format("\t%sType: %s", indent, type));
        System.out.println(String.format("\t%sCounters: %s", indent, counter));
    }
}
