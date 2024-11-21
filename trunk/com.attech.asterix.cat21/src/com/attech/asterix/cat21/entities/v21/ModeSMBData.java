/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities.v21;

/**
 *
 * @author andh
 */
public class ModeSMBData {
    private short repetitionFactor;
    private byte[] message;
    private short bDS1;
    private short bDS2;
    
    public ModeSMBData() {
    }

    /**
     * @return the repetitionFactor
     */
    public short getRepetitionFactor() {
        return repetitionFactor;
    }

    /**
     * @param repetitionFactor the repetitionFactor to set
     */
    public void setRepetitionFactor(short repetitionFactor) {
        this.repetitionFactor = repetitionFactor;
    }

    /**
     * @return the message
     */
    public byte[] getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(byte[] message) {
        this.message = message;
    }

    /**
     * @return the bDS1
     */
    public short getBDS1() {
        return bDS1;
    }

    /**
     * @param bDS1 the bDS1 to set
     */
    public void setBDS1(short bDS1) {
        this.bDS1 = bDS1;
    }

    /**
     * @return the bDS2
     */
    public short getBDS2() {
        return bDS2;
    }

    /**
     * @param bDS2 the bDS2 to set
     */
    public void setBDS2(short bDS2) {
        this.bDS2 = bDS2;
    }
}
