/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat48.v121;

/**
 *
 * @author andh
 */
public class DValue {
    private boolean isRangeExceeded;
    private double value;

    public double getValue() {
        return value;
    }


    public void setValue(double value) {
        this.value = value;
    }

    public boolean isIsRangeExceeded() {
        return isRangeExceeded;
    }

    public void setIsRangeExceeded(boolean isRangeExceeded) {
        this.isRangeExceeded = isRangeExceeded;
    }
    
    @Override
    public String toString() {
        return " RangeExceeded: " + this.isRangeExceeded + " Value: " + this.value;
    }
}
