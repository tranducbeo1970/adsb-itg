/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.XmlSerializer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "STCA")
@XmlAccessorType(XmlAccessType.NONE)
public class STCAConfig extends XmlSerializer {

    @XmlElement(name = "WarnVertialThreshold")
    private int verticalWarnThreshold;

    @XmlElement(name = "WarnHorizonThreshold")
    private int horizonWarnThreshold;

    @XmlElement(name = "AlarmVertialThreshold")
    private int verticalAlarmThreshold;

    @XmlElement(name = "AlarmHorizonThreshold")
    private int horizonAlarmThreshold;

    @XmlElement(name = "WarnTimeInAdvance")
    private int warningTimeInAdvance;

    public STCAConfig() {
    }
    
    public void update(STCAConfig config) {
        this.verticalAlarmThreshold = config.getVerticalAlarmThreshold();
        this.verticalWarnThreshold = config.getVerticalWarnThreshold();
        this.horizonAlarmThreshold = config.getHorizonAlarmThreshold();
        this.horizonWarnThreshold = config.getHorizonWarnThreshold();
        this.warningTimeInAdvance = config.getWarningTimeInAdvance();
    }

    public static void main(String[] args) {
        STCAConfig stcaConfig = new STCAConfig();
        stcaConfig.setHorizonAlarmThreshold(5);
        stcaConfig.setHorizonWarnThreshold(3);
        stcaConfig.setVerticalAlarmThreshold(1000);
        stcaConfig.setVerticalWarnThreshold(2000);
        stcaConfig.setWarningTimeInAdvance(160);
        stcaConfig.save("stca.xml");
        System.out.println("Done");

    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">  
    /**
     * @return the verticalWarnThreshold the unit is Feet
     */
    public int getVerticalWarnThreshold() {
        return verticalWarnThreshold;
    }

    /**
     * @param verticalWarnThreshold the unit is Feet
     */
    public void setVerticalWarnThreshold(int verticalWarnThreshold) {
        this.verticalWarnThreshold = verticalWarnThreshold;
    }

    /**
     * @return the horizonWarnThreshold the unit is Nautical Mile
     */
    public int getHorizonWarnThreshold() {
        return horizonWarnThreshold;
    }

    /**
     * @param horizonWarnThreshold the unit is Nautical Mile
     */
    public void setHorizonWarnThreshold(int horizonWarnThreshold) {
        this.horizonWarnThreshold = horizonWarnThreshold;
    }

    /**
     * @return the verticalAlarmThreshold the unit is Nautical Mile
     */
    public int getVerticalAlarmThreshold() {
        return verticalAlarmThreshold;
    }

    /**
     * @param verticalAlarmThreshold the unit is Nautical Mile
     */
    public void setVerticalAlarmThreshold(int verticalAlarmThreshold) {
        this.verticalAlarmThreshold = verticalAlarmThreshold;
    }

    /**
     * @return the horizonAlarmThreshold the unit is Nautical Mile
     */
    public int getHorizonAlarmThreshold() {
        return horizonAlarmThreshold;
    }

    /**
     * @param horizonAlarmThreshold the unit is Nautical Mile
     */
    public void setHorizonAlarmThreshold(int horizonAlarmThreshold) {
        this.horizonAlarmThreshold = horizonAlarmThreshold;
    }

    /**
     * @return the warningTimeInAdvance the unit is Second
     */
    public int getWarningTimeInAdvance() {
        return warningTimeInAdvance;
    }

    /**
     * @param warningTimeInAdvance  the unit is Second
     */
    public void setWarningTimeInAdvance(int warningTimeInAdvance) {
        this.warningTimeInAdvance = warningTimeInAdvance;
    }
    //</editor-fold>

}
