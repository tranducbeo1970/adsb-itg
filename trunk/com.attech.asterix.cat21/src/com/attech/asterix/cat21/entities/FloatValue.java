/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

import exception.InvalidFormatException;

/**
 *
 * @author andh
 */
public class FloatValue {
    protected float value;

    /** VALUE **/
    public float getValue() {
        return value;
    }

    public void setValue(float value) throws InvalidFormatException {
        this.value = value;
    }
}
