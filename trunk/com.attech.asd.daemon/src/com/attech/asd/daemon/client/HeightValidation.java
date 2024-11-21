/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.client;

import com.attech.asd.daemon.client.IValidation;
import com.attech.cat21.v210.Cat21Message;
import com.attech.asd.daemon.config.HeightFilter;

public class HeightValidation implements IValidation{
    
    private final Float min;
    private final Float max;
    
    public HeightValidation(){
        min = null;
        max = null;
    }

    public HeightValidation(Float min, Float max) {
        this.min = min;
        this.max = max;
    }
    
    public HeightValidation(HeightFilter filter) {
        this.min = filter.getMin();
        this.max = filter.getMax();
    }
    
    @Override
    public boolean validate(Cat21Message message) {
        return validate(message.getFlightLevel());
    }
    
    public boolean validate(Float f) {
        if (f == null) return true;
        return (f >= min && f <= max);
    }
    
    @Override
    public String toString() {
        return String.format("Height Validation %s - %s ", min, max);
    }
}
