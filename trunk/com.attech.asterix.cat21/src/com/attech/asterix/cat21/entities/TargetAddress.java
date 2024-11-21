/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class TargetAddress {
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
        return "Target Add: " + this.value;
    }
}
