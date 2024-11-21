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
public class IFPSFlightID {
    private int ifpsType;
    private int nbr;
    
    public static int decode(byte [] bytes, int index, IFPSFlightID code){
        
        if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
        byte currentByte = bytes[index++];
        code.setIfpsType(currentByte >> 6 & 0x03);
        int val = (currentByte & 0x07) << 24 | (bytes[index++] & 0xFF) << 16 | (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
        code.setNbr(val);
        return 4;
    }
    
    public byte[] encode() {
        return new byte[]{
            (byte) ((ifpsType & 0x03 << 6) | (nbr >> 24 & 0x07)),
            (byte) (nbr >> 16 & 0xFF),
            (byte) (nbr >> 8 & 0xFF),
            (byte) (nbr & 0xFF)
        };
    }

    /**
     * @return the ifpsType <br/>
     * 00 Plan Number <br/>
     * 01 Unit 1 internal flight number <br/>
     * 10 Unit 2 internal flight number <br/>
     * 11 Unit 3 internal flight number
     */
    public int getIfpsType() {
        return ifpsType;
    }

    /**
     * @param ifpsType  <br/>
     * 00 Plan Number <br/>
     * 01 Unit 1 internal flight number <br/>
     * 10 Unit 2 internal flight number <br/>
     * 11 Unit 3 internal flight number
     */
    public void setIfpsType(int ifpsType) {
        this.ifpsType = ifpsType;
    }

    /**
     * @return the nbr
     */
    public int getNbr() {
        return nbr;
    }

    /**
     * @param nbr the nbr to set
     */
    public void setNbr(int nbr) {
        this.nbr = nbr;
    }
    
}
