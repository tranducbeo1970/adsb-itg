/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hong
 */
@XmlRootElement(name = "Line")
@XmlAccessorType(XmlAccessType.NONE)
public class Line {
    
    @XmlAttribute(name = "z-index")
    private Float zIndex;
    
    @XmlAttribute(name = "line-weight")
    private Integer weight;
    
    @XmlAttribute(name = "color")
    private String color;
    
    @XmlElement(name = "Points")
    private List<Point2f> points;
    
    public Color paintColor;
    
    public Line() {
        this.zIndex = 1.0f;
        this.weight = 1;
        this.color = "#000000";
        this.paintColor = new Color(this.color);
        this.points = new ArrayList<>();
    }
    
    public Line(float zindex, int weigtht, String color) {
        this();
        this.zIndex = zindex;
        this.weight = weigtht;
        this.color = color;
        this.paintColor = new Color(color);
    }
    
    public void updateColor() {
        this.paintColor = new Color(color);
    }
    
    public boolean valid() {
        return !this.points.isEmpty();
    }

    public Float getzIndex() { return zIndex; }

    public void setzIndex(Float zIndex) { this.zIndex = zIndex; }

    public Integer getWeight() { return weight; }

    public void setWeight(Integer weight) { this.weight = weight; }

    public String getColor() { return color; }

    public void setColor(String color) {
        this.color = color;
        this.paintColor = new Color(color);
    }

    public List<Point2f> getPoints() { return points; }

    public void setPoints(List<Point2f> points) { this.points = points; }
    
    public void addPoint(Point2f point) { this.points.add(point); }
    
    public void addPoint(float x, float y) { this.points.add(new Point2f(x, y)); }
    
    
    public Color getPaintColor() {
        return this.paintColor;
    }
    
    
}
