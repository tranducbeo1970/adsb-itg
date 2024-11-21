/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "GroundStations")
@XmlAccessorType(XmlAccessType.NONE)
public class GroundStationConfig extends com.attech.adsb.client.common.XmlSerializer {


    @XmlElement(name = "Station")
    private List<GroundStation> groundStations;
    
    @XmlElement(name = "Icon")
    private String icon;

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the groundStations
     */
    public List<GroundStation> getGroundStations() {
        return groundStations;
    }

    /**
     * @param groundStations the groundStations to set
     */
    public void setGroundStations(List<GroundStation> groundStations) {
        this.groundStations = groundStations;
    }
    
    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    //</editor-fold>
    
    public static void main(String[] args) {
        GroundStationConfig config = GroundStationConfig.load("res/ndb.xml", GroundStationConfig.class);
        List<GroundStation> stations = config.getGroundStations();
        for (GroundStation station : stations) {
            System.out.println("Name: " + station.getName());
        }
    }
}