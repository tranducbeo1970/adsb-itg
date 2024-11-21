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
public class FlightCategory {
    private int airTraffic;
    private int flightRule;
    private int reduceVerticalSeperationMinimum;
    private int hpr;

    public static int decode(byte [] bytes, int index, FlightCategory code){
        
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte currentByte = bytes[index++];
        code.setAirTraffic(currentByte >> 6 & 0x03);
        code.setFlightRule(currentByte >> 4 & 0x03);
        code.setReduceVerticalSeperationMinimum(currentByte >> 2 & 0x03);
        code.setHpr(currentByte >> 1 & 0x01);
        return 1;
    }
    
    public byte[] encode() {
        return new byte[]{
            (byte) ((airTraffic & 0x03 << 6)
            | (flightRule & 0x03 << 4)
            | (reduceVerticalSeperationMinimum & 0x03 << 2)
            | (hpr & 0x01 << 1))};
    }
        
    /**
     * @return the airTraffic <br/>
     * 00 Unknown<br/>
     * 01 General Air Traffic<br/>
     * 10 Operational Air Traffic<br/>
     * 11 Not applicable
     */
    public int getAirTraffic() {
        return airTraffic;
    }

    /**
     * @param airTraffic <br/>
     * 00 Unknown<br/>
     * 01 General Air Traffic<br/>
     * 10 Operational Air Traffic<br/>
     * 11 Not applicable
     */
    public void setAirTraffic(int airTraffic) {
        this.airTraffic = airTraffic;
    }

    /**
     * @return the flightRule <br/>
     * 00 Instrument Flight Rules <br/>
     * 01 Visual Flight rules <br/>
     * 10 Not applicable <br/>
     * 11 Controlled Visual Flight Rules
     */
    public int getFlightRule() {
        return flightRule;
    }

    /**
     * @param flightRule <br/>
     * 00 Instrument Flight Rules <br/>
     * 01 Visual Flight rules <br/>
     * 10 Not applicable <br/>
     * 11 Controlled Visual Flight Rules
     */
    public void setFlightRule(int flightRule) {
        this.flightRule = flightRule;
    }

    /**
     * @return the reduceVerticalSeperationMinimum <br/>
     * 00 Unknown <br/>
     * 01 Approved <br/>
     * 10 Exempt <br/>
     * 11 Not Approved
     */
    public int getReduceVerticalSeperationMinimum() {
        return reduceVerticalSeperationMinimum;
    }

    /**
     * @param reduceVerticalSeperationMinimum <br/>
     * 00 Unknown <br/>
     * 01 Approved <br/>
     * 10 Exempt <br/>
     * 11 Not Approved
     */
    public void setReduceVerticalSeperationMinimum(int reduceVerticalSeperationMinimum) {
        this.reduceVerticalSeperationMinimum = reduceVerticalSeperationMinimum;
    }

    /**
     * @return the hpr <br/>
     * 0 Normal Priority Flight <br/>
     * 1 High Priority Flight
     */
    public int getHpr() {
        return hpr;
    }

    /**
     * @param hpr <br/>
     * 0 Normal Priority Flight <br/>
     * 1 High Priority Flight
     */
    public void setHpr(int hpr) {
        this.hpr = hpr;
    }
    
}
