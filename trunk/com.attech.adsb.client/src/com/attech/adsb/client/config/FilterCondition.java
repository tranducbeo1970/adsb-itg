/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.Target;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "FilterCondition")
@XmlAccessorType(XmlAccessType.NONE)
public class FilterCondition {

    @XmlAttribute(name = "active")
    private boolean active;

    @XmlAttribute(name = "hide-target")
    private boolean hideTarget;

    @XmlElement(name = "AltitudeLow")
    private Integer altitudeLow;
    
    @XmlElement(name = "AltitudeHigh")
    private Integer altitudeHigh;

    @XmlElement(name = "Callsign")
    private String callSign;
    
    public FilterCondition (){
    }
    
    public FilterCondition createClone() {
       FilterCondition condition = new FilterCondition();
       condition.setActive(active);
       condition.setHideTarget(hideTarget);
       condition.setAltitudeHigh(altitudeHigh);
       condition.setAltitudeLow(altitudeLow);
       condition.setCallSign(callSign);
       return condition;
    }
    
    public void validate(Target target) {
        
    }
     
    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the hideTarget
     */
    public boolean isHideTarget() {
        return hideTarget;
    }

    /**
     * @param hideTarget the hideTarget to set
     */
    public void setHideTarget(boolean hideTarget) {
        this.hideTarget = hideTarget;
    }

    /**
     * @return the altitudeLow
     */
    public Integer getAltitudeLow() {
        return altitudeLow;
    }

    /**
     * @param altitudeLow the altitudeLow to set
     */
    public void setAltitudeLow(Integer altitudeLow) {
        this.altitudeLow = altitudeLow;
    }

    /**
     * @return the altitudeHigh
     */
    public Integer getAltitudeHigh() {
        return altitudeHigh;
    }

    /**
     * @param altitudeHigh the altitudeHigh to set
     */
    public void setAltitudeHigh(Integer altitudeHigh) {
        this.altitudeHigh = altitudeHigh;
    }

    /**
     * @return the callSign
     */
    public String getCallSign() {
        return callSign;
    }

    /**
     * @param callSign the callSign to set
     */
    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }
}
