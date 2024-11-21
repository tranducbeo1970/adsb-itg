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
public class TrajectoryIntentStatus {
    private boolean dataAvailable;
    private boolean dataValid;
    
    public static int extract(byte[] bytes, int index, TrajectoryIntentStatus code) {

        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte currentByte = bytes[index++];
        int unit = (currentByte >> 7) & 0x01;
        code.setDataAvailable(unit > 0);
        unit = (currentByte >> 6) & 0x01;
        code.setDataValid(unit > 0);
        
        int count = 1;
        while ((currentByte & 0x01) > 0) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            currentByte = bytes[index++];
            count++;
        }
        return count;
    }

    /**
     * @return the dataAvailable
     */
    public boolean isDataAvailable() {
        return dataAvailable;
    }

    /**
     * @param dataAvailable the dataAvailable to set
     */
    public void setDataAvailable(boolean dataAvailable) {
        this.dataAvailable = dataAvailable;
    }

    /**
     * @return the dataValid
     */
    public boolean isDataValid() {
        return dataValid;
    }

    /**
     * @param dataValid the dataValid to set
     */
    public void setDataValid(boolean dataValid) {
        this.dataValid = dataValid;
    }
    
}
