/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Point")
@XmlAccessorType(XmlAccessType.NONE)
public class PointX {
       
    @XmlAttribute(name="y")
    public float y;
  
    @XmlAttribute(name="x")
    public float x;
    
    @XmlAttribute(name="name")
    public String name;
    
    
    public PointX() {
    }
    
    public PointX(float x, float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
    
    public PointX(PointX point) {
        this.x = point.x;
        this.y = point.y;
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    
    public void set(PointX p) {
        this.x = p.x;
        this.y = p.y;
    }
    
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }

    public void setX(float longtitude) { this.x = longtitude; }

    public float getY() { return y; }

    public void setY(float latitude) { this.y = latitude; }
        
 
    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    //</editor-fold>
    
    @Override
    public String toString() {
        return "lon "  +this.x + " lat " + this.y;
    }

}
