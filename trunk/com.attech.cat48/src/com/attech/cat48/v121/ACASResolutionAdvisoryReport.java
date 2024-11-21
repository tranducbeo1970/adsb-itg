/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat48.v121;

/**
 *
 * @author hong
 */
public class ACASResolutionAdvisoryReport {
    private byte [] data;

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
    
    
    public static int extract(byte[] bytes, int index, ACASResolutionAdvisoryReport report) {
        if (!Byter.validateIndex(index, bytes.length, 7)) return -1;
        report.setData(new byte[]{
            bytes[index++], bytes[index++], bytes[index++], 
            bytes[index++], bytes[index++], bytes[index++], bytes[index++]});
        return 7;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sACAS Resolution Advisory Report", indent));
        System.out.print(String.format("\t%sData: ", indent));
        for (int i = 0; i < data.length; i++) {
            System.out.println(String.format(" %s", Byter.toHex(data[i])));
        }
        System.out.println();
    }
}
