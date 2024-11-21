/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class EmitterCategory {
    private short value;

    /** VALUE **/
    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "Emit Cat:" + this.value;
    }
}
