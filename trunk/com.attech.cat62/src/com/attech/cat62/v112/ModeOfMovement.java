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
public class ModeOfMovement {
    private int transversalAcceleration;
    private int longitudinalAcceleration;
    private int verticalRate;
    private boolean altitudeDiscrepancyFlag;
    
    public static int extract(byte[] bytes, int index, ModeOfMovement code) {

        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setTransversalAcceleration(cbyte >> 6 & 0x03);
        code.setLongitudinalAcceleration(cbyte >> 4 & 0x03);
        code.setVerticalRate(cbyte >> 2 & 0x03);
        code.setAltitudeDiscrepancyFlag((cbyte & 0x02) > 0);
        return 1;
    }

    /**
     * @return the transversalAcceleration <br/>
     * 00 Constant Course <br/>
     * 01 Right Turn <br/>
     * 10 Left Turn <br/>
     * 11 Undetermined
     */
    public int getTransversalAcceleration() {
        return transversalAcceleration;
    }

    /**
     * @param transversalAcceleration <br/>
     * 00 Constant Course <br/>
     * 01 Right Turn <br/>
     * 10 Left Turn <br/>
     * 11 Undetermined
     */
    public void setTransversalAcceleration(int transversalAcceleration) {
        this.transversalAcceleration = transversalAcceleration;
    }

    /**
     * @return the longitudinalAcceleration <br/>
     * 00 Constant Groundspeed <br/>
     * 01 Increasing Groundspeed <br/>
     * 10 Decreasing Groundspeed <br/>
     * 11 Undetermined
     */
    public int getLongitudinalAcceleration() {
        return longitudinalAcceleration;
    }

    /**
     * @param longitudinalAcceleration <br/>
     * 00 Constant Groundspeed <br/>
     * 01 Increasing Groundspeed <br/>
     * 10 Decreasing Groundspeed <br/>
     * 11 Undetermined
     */
    public void setLongitudinalAcceleration(int longitudinalAcceleration) {
        this.longitudinalAcceleration = longitudinalAcceleration;
    }

    /**
     * @return the verticalRate <br/>
     * 00 Level <br/>
     * 01 Climb <br/>
     * 10 Descent <br/>
     * 11 Undetermined
     */
    public int getVerticalRate() {
        return verticalRate;
    }

    /**
     * @param verticalRate <br/>
     * 00 Level <br/>
     * 01 Climb <br/>
     * 10 Descent <br/>
     * 11 Undetermined
     */
    public void setVerticalRate(int verticalRate) {
        this.verticalRate = verticalRate;
    }

    /**
     * @return the altitudeDiscrepancyFlag
     */
    public boolean isAltitudeDiscrepancyFlag() {
        return altitudeDiscrepancyFlag;
    }

    /**
     * @param altitudeDiscrepancyFlag the altitudeDiscrepancyFlag to set
     */
    public void setAltitudeDiscrepancyFlag(boolean altitudeDiscrepancyFlag) {
        this.altitudeDiscrepancyFlag = altitudeDiscrepancyFlag;
    }
}
