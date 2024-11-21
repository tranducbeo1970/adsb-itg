/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AnhTH
 */
@XmlRootElement(name = "Height")
@XmlAccessorType(XmlAccessType.NONE)
public class HeightFilter {
    
    @XmlAttribute(name = "min")
    private Float min;
    
    @XmlAttribute(name = "max")
    private Float max;
    
    public HeightFilter() {
    }
    
    public HeightFilter(Float min, Float max) {
        this.min = min;
        this.max = max;
    }

    /**
     * @return the min
     */
    public Float getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(Float min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public Float getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(Float max) {
        this.max = max;
    }
}
