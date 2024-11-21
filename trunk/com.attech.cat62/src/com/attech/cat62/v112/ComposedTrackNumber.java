package com.attech.cat62.v112;


import com.attech.cat62.v112.Byter;
import com.attech.cat62.v112.SelectedAltitude;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andh
 */
public class ComposedTrackNumber {
    private List<Integer> systemUnitID;
    private List<Integer> systemTrackNo;
    
    public ComposedTrackNumber() {
        systemUnitID = new ArrayList<>();
        systemTrackNo = new ArrayList<>();
    }
    
    public static int extract(byte [] bytes, int index, ComposedTrackNumber code){
        int count = 0;
        while (true) {
            if (!Byter.validateIndex(index, bytes.length, 3)) return -1;
            byte b1 = bytes[index++];
            byte b2 = bytes[index++];
            byte b3 = bytes[index++];
            
            int sysID = b1 & 0xFF;
            int trackNo = ((b2 & 0xFF) << 8 | (b3 & 0xFE)) >> 1;
            code.add(sysID, trackNo);
            count += 3;
            if ((b3 & 0x01) == 0) break;
        }
        return count;
    }
    
    public int length() {
        return this.systemUnitID.size();
    }
    
    public void add(int unitID, int trackNo) {
        this.systemUnitID.add(unitID);
        this.systemTrackNo.add(trackNo);
    }

    /**
     * @return the systemUnitID
     */
    public List<Integer> getSystemUnitID() {
        return systemUnitID;
    }

    /**
     * @param systemUnitID the systemUnitID to set
     */
    public void setSystemUnitID(List<Integer> systemUnitID) {
        this.systemUnitID = systemUnitID;
    }

    /**
     * @return the systemTrackNo
     */
    public List<Integer> getSystemTrackNo() {
        return systemTrackNo;
    }

    /**
     * @param systemTrackNo the systemTrackNo to set
     */
    public void setSystemTrackNo(List<Integer> systemTrackNo) {
        this.systemTrackNo = systemTrackNo;
    }
    
}
