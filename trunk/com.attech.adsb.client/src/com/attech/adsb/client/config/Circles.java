/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Circle")
@XmlAccessorType(XmlAccessType.NONE)
public class Circles {
      
    @XmlAttribute(name="type")
    public String type;
    
    @XmlAttribute(name="x")
    public float x;
    
    @XmlAttribute(name="y")
    public float y;
      
    @XmlAttribute(name="radius")
    public float radius;
    
    @XmlAttribute(name="measureUnit")
    public String measureUnit;
    
    @XmlAttribute(name="color")
    public String color;
    
    @XmlAttribute(name="mode")
    public String mode;
    
    @XmlAttribute(name="z-index")
    public int zIndex;
    
    @XmlAttribute(name="weight")
    public int weight;
    
    @XmlAttribute(name="transparent")
    public Boolean transparent;
    
    @XmlAttribute(name="colorTransparent")
    public String colorTransparent;    
    
    public Circles() {
    }
    
    public Circles(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Boolean getTransparent() {
        return transparent;
    }

    public void setTransparent(Boolean transparent) {
        this.transparent = transparent;
    }

    public String getColorTransparent() {
        return colorTransparent;
    }

    public void setColorTransparent(String colorTransparent) {
        this.colorTransparent = colorTransparent;
    }
    
    //</editor-fold>      
   
}
