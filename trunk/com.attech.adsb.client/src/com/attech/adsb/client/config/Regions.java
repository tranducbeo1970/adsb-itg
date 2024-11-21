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
@XmlRootElement(name = "Regions")
@XmlAccessorType(XmlAccessType.NONE)
public class Regions {



    @XmlElement(name = "RegionGroup")
    private List<RegionGroup> lstRegionGroup;  
    
    @XmlAttribute(name = "type")
    private String type;
    
    @XmlAttribute(name = "enable")
    private Boolean enable;
        
    public Regions() {
        this.lstRegionGroup = new ArrayList<>();
        this.enable = true;
    }
               
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public List<RegionGroup> getRegionGroup() {
	return lstRegionGroup;
    }

    public void setRegion(List<RegionGroup> lstRegionGroup) {
	this.lstRegionGroup = lstRegionGroup;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return the enable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

     //</editor-fold>
}
