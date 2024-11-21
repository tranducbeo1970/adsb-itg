/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.adsb.client.config;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "Line")
@XmlAccessorType(XmlAccessType.NONE)
public class MapLine {
    
    @XmlElement(name = "Point")
    private List<Point2f> points;
    
    @XmlAttribute(name = "enabled")
    private boolean enable;
    
    @XmlAttribute(name = "color")
    private String color;
    
    @XmlAttribute(name = "weight")
    private int weight;
    
    @XmlAttribute(name = "z-index")
    private int zIndex;
    
    public MapLine(){
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">

    /**
     * @return the points
     */
    public List<Point2f> getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(List<Point2f> points) {
        this.points = points;
    }

    /**
     * @return the enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
  
    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    /**
     * @return the zIndex
     */
    public int getzIndex() {
        return zIndex;
    }

    /**
     * @param zIndex the zIndex to set
     */
    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }
    //</editor-fold>
}
