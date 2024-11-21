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
public class TrackAngleRate {
    private int turnIndicator;
    private double rateOfTurn;
    
    public static int extract(byte[] bytes, int index, TrackAngleRate code) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        code.setTurnIndicator(bytes[index++] >> 6 & 0x03);
        code.setRateOfTurn(getComplementNumber(bytes[index++]));
        return 2;
    }
    
    private static int getComplementNumber(byte byte1) {
        boolean positive = (int) (byte1 & 0x80) == 0;
        return (positive) ? (byte1 & 0xFE) >> 1: -(((~byte1 & 0xFE) >> 1) + 0x01);
    }

    /**
     * @return the turnIndicator <br/>
     * 00 = Not available <br/>
     * 01 = Left <br/>
     * 10 = Right <br/>
     * 11 = Straight
     */
    public int getTurnIndicator() {
        return turnIndicator;
    }

    /**
     * @param turnIndicator <br/>
     * 00 = Not available <br/>
     * 01 = Left <br/>
     * 10 = Right <br/>
     * 11 = Straight
     */
    public void setTurnIndicator(int turnIndicator) {
        this.turnIndicator = turnIndicator;
    }

    /**
     * @return the rateOfTurn <br/>
     * -15 °/s <= Rate of Turn <= 15 °/s
     */
    public double getRateOfTurn() {
        return rateOfTurn;
    }

    /**
     * @param rateOfTurn  <br/>
     * -15 °/s <= Rate of Turn <= 15 °/s
     */
    public void setRateOfTurn(double rateOfTurn) {
        this.rateOfTurn = rateOfTurn;
    }
    
}
