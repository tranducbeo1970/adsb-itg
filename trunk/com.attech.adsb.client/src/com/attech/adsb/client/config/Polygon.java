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
@XmlRootElement(name = "Polygon")
@XmlAccessorType(XmlAccessType.NONE)
public class Polygon {
        
    @XmlAttribute(name = "alt")
    private float alt;
    
     @XmlElement(name = "Point")
    private List<Point2f> lstPoint;      
            
    public Polygon() {
        this.lstPoint = new ArrayList<>();
    }
               
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public float getAlt() {
        return alt;
    }

    public void setAlt(float alt) {
        this.alt = alt;
    }

    public List<Point2f> getLstPoint() {
        return lstPoint;
    }

    public void setLstPoint(List<Point2f> lstPoint) {
        this.lstPoint = lstPoint;
    }    
    //</editor-fold>

    
}
