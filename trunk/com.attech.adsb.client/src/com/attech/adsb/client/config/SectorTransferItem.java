/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "Sector")
@XmlAccessorType(XmlAccessType.NONE)
public class SectorTransferItem {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "RelateSector")
    private List<String> sectors;

    public SectorTransferItem() {
        sectors = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSectors() {
        return sectors;
    }

    public void setSectors(List<String> sectors) {
        this.sectors = sectors;
    }

    //</editor-fold>
}
