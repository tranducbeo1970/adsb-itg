/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author root
 */
public class SelectedAltitude {
    private boolean sourceInfo;
    private int source;
    private int altitude;

    public static int extract(byte [] bytes, int index, SelectedAltitude code){
        
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte currentByte = bytes[index++];
        int val = (currentByte >> 7) & 0x01;
        code.setSourceInfo(val > 0);
        
        val = (currentByte >> 5) & 0x03;
        code.setSource(val);
        
        val = getComplementNumber(currentByte, bytes[index++]);
        code.setAltitude(val * 25);
        return 2;
    }
    
    private static int getComplementNumber(byte byte1, byte byte2) {
        boolean positive = (int) (byte1 & 0x10) == 0;
        return (positive) ? (byte1 & 0x1F) << 8 | (byte2 & 0xFF) : 
                -(((~byte1 & 0x1F) << 8 | (~byte2 & 0xFF)) + 0x01);
    }
    
    @Override
    public String toString() {
        return "Source: " + this.source + " Altitude: " + this.altitude;
    }
  
    /**
     * @return the source <br/>
     * = 00 Unknown <br/>
     * = 01 Aircraft Altitude <br/>
     * = 10 FCU/MCP Selected Altitude <br/>
     * = 11 FMS Selected Altitude
     */
    public int getSource() {
        return source;
    }

    /**
     * @param source <br/>
     * = 00 Unknown <br/>
     * = 01 Aircraft Altitude <br/>
     * = 10 FCU/MCP Selected Altitude <br/>
     * = 11 FMS Selected Altitude
     */
    public void setSource(int source) {
        this.source = source;
    }

    /**
     * @return the altitude
     */
    public int getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
    
    

    /**
     * @return the sourceInfo<br/>
     * = 0 No source information provided<br/>
     * = 1 Source Information provided
     */
    public boolean isSourceInfo() {
        return sourceInfo;
    }

    /**
     * @param sourceInfo <br/>
     * = 0 No source information provided<br/>
     * = 1 Source Information provided
     */
    public void setSourceInfo(boolean sourceInfo) {
        this.sourceInfo = sourceInfo;
    }
    
}
