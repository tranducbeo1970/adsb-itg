/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.adsb.client.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "Zoom")
@XmlAccessorType(XmlAccessType.NONE)
public class Zoom {


    @XmlAttribute(name = "min")
    private int min;
    
    @XmlAttribute(name = "max")
    private int max;
    
    @XmlAttribute(name = "step")
    private int step;
    
   
    
    public Zoom() {
    }
    
    public Zoom(int min, int max, int step) {
        this.min = min;
        this.max = max;
        this.step = step;
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    
    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the step
     */
    public int getStep() {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(int step) {
        this.step = step;
    }

    //</editor-fold>

}
