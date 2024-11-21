/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class DValue {
    public boolean rangeExceed;
    public double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    
    @Override
    public String toString() {
        return " RangeExceeded: " + this.isRangeExceed() + " Value: " + this.value;
    }

    /**
     * @return the rangeExceed
     */
    public boolean isRangeExceed() {
        return rangeExceed;
    }

    /**
     * @param rangeExceed the rangeExceed to set
     */
    public void setRangeExceed(boolean rangeExceed) {
        this.rangeExceed = rangeExceed;
    }
}
