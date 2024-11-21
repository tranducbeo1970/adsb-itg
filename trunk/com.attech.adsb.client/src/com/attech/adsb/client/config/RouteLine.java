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
@XmlRootElement(name = "Route")
@XmlAccessorType(XmlAccessType.NONE)
public class RouteLine {

    @XmlAttribute(name = "name")
    private String name;
    
    @XmlAttribute(name = "enabled")
    private Boolean enabled;
    
    @XmlAttribute(name = "color")
    private String color;
    
    @XmlAttribute(name = "z-index")
    private int zIndex;
    
    @XmlAttribute(name = "weight")
    private int weight;

    @XmlElement(name = "Label")
    private Label label;
    
    @XmlElement(name = "Point")
    private List<String> points;
    
    private List<Point2f> point2fs;

    public RouteLine() {
        this.points = new ArrayList<>();
        this.point2fs = new ArrayList<>();
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">


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
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
     * @return the points
     */
    public List<String> getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(List<String> points) {
        this.points = points;
    }
    
    /**
     * @return the label
     */
    public Label getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(Label label) {
        this.label = label;
    }

    /**
     * @return the point2fs
     */
    public List<Point2f> getPoint2fs() {
        return point2fs;
    }

    /**
     * @param point2fs the point2fs to set
     */
    public void setPoint2fs(List<Point2f> point2fs) {
        this.point2fs = point2fs;
    }
    
    //</editor-fold>
    
    
    public void addPoint(String point) {
        this.points.add(point);
    }

}
