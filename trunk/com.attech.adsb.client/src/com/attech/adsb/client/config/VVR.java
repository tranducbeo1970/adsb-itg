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
 * @author NhuongND
 */
@XmlRootElement(name = "VVR")
@XmlAccessorType(XmlAccessType.NONE)
public class VVR {

    
    @XmlAttribute(name = "name")
    private String name;
        
    @XmlAttribute(name = "color")
    private String color;
    
    @XmlAttribute(name = "type")
    private String type;
    
    @XmlAttribute(name = "levelmin")
    private Integer levelmin;
    
    @XmlAttribute(name = "radius")
    public float radius;
    
    @XmlAttribute(name = "enabled")
    private boolean enabled;
    
    @XmlAttribute(name = "weight")
    private int weight;
    
    @XmlAttribute(name = "z-index")
    private int zIndex;
    
    @XmlAttribute(name = "includes")
    private String includes;
    
    @XmlAttribute(name = "angles")
    private String angles;
    
    @XmlElement(name = "Point")
    private List<Point2f> points;

    public VVR() {
       
    }

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

    /**
     * @return the colors
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColors(String color) {
        this.color = color;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
     /**
     * @param radius the radius to set
     */
    public void setRadius(Float radius) {
        this.radius = radius;
    }
    
    /**
     * @return the range
     */
    public Float getRadius() {
        return radius;
    }

    /**
     * @return the level min
     */
    public Integer getLevelmin() {
        return levelmin;
    }

    /**
     * @param levelmin the level min to set
     */
    public void setLevelmin(Integer levelmin) {
        this.levelmin = levelmin;
    }
    
    /**
     * @return the enabled
     */
    public boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public String getIncludes() {
        return includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    public String getAngles() {
        return angles;
    }

    public void setAngles(String angles) {
        this.angles = angles;
    }      
    
    /**
     * @return the point
     */
    public List<Point2f> getPoints() {
        return points;
    }

    /**
     * @param point the point to set
     */
    public void setPoints(List<Point2f> points) {
        this.points = points;
    }
    
    /**
     * @param point the point to set
     */
    public void setPoints(Point2f points) {
        this.points.add(points);
    }

}
