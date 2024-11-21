/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat34.v127;

/**
 *
 * @author andh
 */
public class SValue {
    private String value;

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    
    public String val() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}
