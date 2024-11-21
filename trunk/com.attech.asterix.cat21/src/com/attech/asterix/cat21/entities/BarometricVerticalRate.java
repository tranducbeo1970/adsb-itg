/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class BarometricVerticalRate extends FloatValue{
    @Override
    public String toString() {
        return "Barometric:" + this.value;
    }
}
