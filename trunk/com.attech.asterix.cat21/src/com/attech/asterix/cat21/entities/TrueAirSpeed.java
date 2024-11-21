/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class TrueAirSpeed {
    private int value;

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    public String toString() {
        return "True Spd: " + this.value;
    }
}
