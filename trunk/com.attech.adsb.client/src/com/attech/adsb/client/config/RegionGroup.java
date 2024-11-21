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
 * @author nhuongnd
 */
@XmlRootElement(name = "RegionGroup")
@XmlAccessorType(XmlAccessType.NONE)
public class RegionGroup {

    @XmlElement(name = "Region")
    private List<Region> lstRegion;  
        
    @XmlAttribute(name = "type")
    private String type;
    
    @XmlAttribute(name = "name")
    private String name;
            
    public RegionGroup() {
        this.lstRegion = new ArrayList<>();
    }
               
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public List<Region> getRegion() {
	return lstRegion;
    }

    public void addRegion(Region Region) {
	this.lstRegion.add(Region);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Region> getLstRegion() {
        return lstRegion;
    }

    public void setLstRegion(List<Region> lstRegion) {
        this.lstRegion = lstRegion;
    }   
     //</editor-fold>
}
