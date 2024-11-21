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
 * @author Saitama
 */
@XmlRootElement(name = "Line")
@XmlAccessorType(XmlAccessType.NONE)
public class SectorLine {
    
    @XmlElement(name = "Point")
    private List<PointX> points;
    
    @XmlAttribute(name = "enabled")
    private boolean enable;
    
    @XmlAttribute(name = "color")
    private String color;
    
    @XmlAttribute(name = "weight")
    private int weight;
    
    @XmlAttribute(name = "z-index")
    private int zIndex;
    
    public SectorLine(){
	this.points = new ArrayList<>();
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">

    /**
     * @return the points
     */
    public List<PointX> getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(List<PointX> points) {
        this.points = points;
    }
    
    public void addPoint(PointX pointX) {
	this.points.add(pointX);
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
