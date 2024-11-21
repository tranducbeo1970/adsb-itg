/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class TimeOfDay {
    private long value ;

    /**
     * @return the value
     */
    public long getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(long value) {
        this.value = value;
    }
    
    public String toString() {
        return "Time: " + value;
    }
}
