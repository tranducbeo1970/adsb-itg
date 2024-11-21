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
 * @author NhuongND
 */
@XmlRootElement(name = "Site")
@XmlAccessorType(XmlAccessType.NONE)
public class Site {
    @XmlAttribute(name = "address")
    private String address;
    
    public Site() {
        
    }
    
    public Site(String address) {
	this.address = address;
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    //</editor-fold>   
    
}
