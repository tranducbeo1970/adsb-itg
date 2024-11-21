/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat48.v121;

/**
 *
 * @author andh
 */
public class ModeSMBItem {
    private byte[] data;
    private int bds1;
    private int bds2;
    
    public ModeSMBItem() {
    }
    
    public ModeSMBItem(byte [] data, int bds1, int bds2) {
        this.data = data;
        this.bds1 = bds1;
        this.bds2 = bds2;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
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
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        String content = Byter.toHex(data);
        System.out.printf("%sData: %s%n", indent, content);
        System.out.printf("%sBDS1: %d BDS2:%d%n", indent, bds1, bds2);
    }
}
