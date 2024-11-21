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
@XmlRootElement(name = "Cell")
@XmlAccessorType(XmlAccessType.NONE)
public class AMACell {
    
    @XmlAttribute(name = "name")
    public String name;
    
    @XmlAttribute(name = "lat1")
    public float lat1;
    
    @XmlAttribute(name = "lat2")
    public float lat2;
    
    @XmlAttribute(name = "lon1")
    public float lon1;
    
    @XmlAttribute(name = "lon2")
    public float lon2;
    
    @XmlAttribute(name = "alt")
    public float alt;
    
    @XmlAttribute(name = "real_alt")
    public float real_alt;
    
    // Vong ben ngoai
    public float lat3;
    public float lat4;
    public float lon3;
    public float lon4;
    
    public AMACell() {
       
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">  
    
    public float getLat1() {
        return lat1;
    }

    public void setLat1(float lat1) {
        this.lat1 = lat1;
    }

    public float getLat2() {
        return lat2;
    }

    public void setLat2(float lat2) {
        this.lat2 = lat2;
    }

    public float getLon1() {
        return lon1;
    }

    public void setLon1(float lon1) {
        this.lon1 = lon1;
    }

    public float getLon2() {
        return lon2;
    }

    public void setLon2(float lon2) {
        this.lon2 = lon2;
    }

    public float getAlt() {
        return alt;
    }

    public void setAlt(float alt) {
        this.alt = alt;
    }

    public float getReal_alt() {
        return real_alt;
    }

    public void setReal_alt(float real_alt) {
        this.real_alt = real_alt;
    }

    public float getLat3() {
        return lat3;
    }

    public void setLat3(float lat3) {
        this.lat3 = lat3;
    }

    public float getLat4() {
        return lat4;
    }

    public void setLat4(float lat4) {
        this.lat4 = lat4;
    }

    public float getLon3() {
        return lon3;
    }

    public void setLon3(float lon3) {
        this.lon3 = lon3;
    }

    public float getLon4() {
        return lon4;
    }

    public void setLon4(float lon4) {
        this.lon4 = lon4;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    //</editor-fold>

}
