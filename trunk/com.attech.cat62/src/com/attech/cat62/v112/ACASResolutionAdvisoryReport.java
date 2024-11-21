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
public class ACASResolutionAdvisoryReport {
    private int [] data = new int[7];
    
    public static int extract(byte[] bytes, int index, ACASResolutionAdvisoryReport code) {
        if (!Byter.validateIndex(index, bytes.length, 7)) return -1;
        code.setData(0, bytes[index++] & 0xFF);
        code.setData(1, bytes[index++] & 0xFF);
        code.setData(2, bytes[index++] & 0xFF);
        code.setData(3, bytes[index++] & 0xFF);
        code.setData(4, bytes[index++] & 0xFF);
        code.setData(5, bytes[index++] & 0xFF);
        code.setData(6, bytes[index++] & 0xFF);
        return 7;
    }
    
    

    /**
     * @return the data
     */
    public int[] getData() {
        return data;
    }
    
    public int getDate(int index) {
        if (index < 0 || index >= data.length) return -1;
        return data[index];
    }

    /**
     * @param data the data to set
     */
    public void setData(int[] data) {
        this.data = data;
    }
    
    public void setData(int index, int value) {
        if (index < 0 || index >= data.length) return;
        data[index] = value;
    }
}
