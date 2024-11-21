/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author an
 */
public class ModeSMBData {
    private int repetition;
    private List<ModeSMBItem> mbdata = new ArrayList<>();
    
    public ModeSMBData() {
    }

    /**
     * @return the repetition
     */
    public int getRepetition() {
        return repetition;
    }

    /**
     * @param repetition the repetition to set
     */
    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }
    
    /**
     * @return the mbdata
     */
    public List<ModeSMBItem> getMbdata() {
        return mbdata;
    }

    /**
     * @param mbdata the mbdata to set
     */
    public void setMbdata(List<ModeSMBItem> mbdata) {
        this.mbdata = mbdata;
    }
    
    /**
     * addMbData
     * @param item 
     */
    public void addMbData(ModeSMBItem item) {
        this.mbdata.add(item);
    }

    public static int extract(byte [] bytes, int index, ModeSMBData modes ) {
        if (!Byter.validateIndex(index, bytes.length, 9)) return -1;
        byte cbyte = bytes[index++];
        int count = 1;
        int rep = cbyte & 0xFF;
        modes.setRepetition(rep);

        for (int i = 0; i < rep; i++) {
            byte[] mbdata = new byte[]{
                bytes[index++], bytes[index++], bytes[index++],
                bytes[index++], bytes[index++], bytes[index++], 
                bytes[index++]};
            cbyte = bytes[index++];
            int bds1 = Byter.extract(cbyte, 4, 4);
            int bds2 = Byter.extract(cbyte, 0, 4);
            ModeSMBItem item = new ModeSMBItem(mbdata, bds1, bds2);
            modes.addMbData(item);
            count+= 8;
        }
        return count;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.printf("%sModeS MB Data %n", indent);
        System.out.printf("\t%sRepetition: %s%n", indent, repetition);
        for (ModeSMBItem mbdata1 : mbdata) {
            mbdata1.print(level + 1);
        }
    }

    
    
}
