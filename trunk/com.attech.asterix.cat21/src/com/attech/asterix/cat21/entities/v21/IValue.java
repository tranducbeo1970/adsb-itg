/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities.v21;

/**
 *
 * @author andh
 */
public class IValue {
    private boolean isRangeExceeded;
    private int value;

    public int getValue() {
        return value;
    }


    public void setValue(int value) {
        this.value = value;
    }

    public boolean isIsRangeExceeded() {
        return isRangeExceeded;
    }

    public void setIsRangeExceeded(boolean isRangeExceeded) {
        this.isRangeExceeded = isRangeExceeded;
    }
    
    public String toString() {
        return "Value: " + this.value;
    }
}
