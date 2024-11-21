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
 * @author NhuongND
 */
@XmlRootElement(name = "RegionLine")
@XmlAccessorType(XmlAccessType.NONE)
public class RegionLine {
            
    @XmlAttribute(name = "color")
    private String color;
    
    @XmlAttribute(name = "type")
    private String type;
        
    @XmlAttribute(name = "radius")
    public float radius;
    
    @XmlAttribute(name = "angles")
    private String angles;
        
    @XmlAttribute(name = "weight")
    private int weight;
    
    @XmlAttribute(name = "z-index")
    private int zIndex;
            
//    @XmlElement(name = "Point")
//    private List<Point2f> points;
    
    @XmlElement(name = "Point")
    private List<String> points;
    
    private List<Point2f> point2fs;
    
    private String regionType;

    public RegionLine() {
       points = new ArrayList<>();
       point2fs = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">   
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getAngles() {
        return angles;
    }

    public void setAngles(String angles) {
        this.angles = angles;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }
   
    public List<String> getPoints() {
        return points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }

    public List<Point2f> getPoint2fs() {
        return point2fs;
    }

    public void setPoint2fs(List<Point2f> point2fs) {
        this.point2fs = point2fs;
    }
    
    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }
    
    //</editor-fold>
    
}
