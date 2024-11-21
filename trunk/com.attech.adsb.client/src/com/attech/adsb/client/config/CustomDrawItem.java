/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.GraphicNotify;
import com.attech.adsb.client.common.enums.ShapeType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "DrawItem")
@XmlAccessorType(XmlAccessType.NONE)
public class CustomDrawItem {

    @XmlAttribute(name = "type")
    private ShapeType type;

    @XmlAttribute(name = "color")
    private String color;

    @XmlAttribute(name = "weight")
    private int weight;

    @XmlAttribute(name = "z-index")
    private int zIndex;

    @XmlAttribute(name = "enabled")
    private boolean enabled;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElementWrapper(name = "Points")
    @XmlElement(name = "Point")
    private List<Point2f> points;

    @XmlElement(name = "CenterPoint")
    private Point2f centerPoint;

    @XmlElement(name = "Label")
    private Label label;

    @XmlElement(name = "Radius")
    private float radius;

    @XmlElement(name = "angle")
    private String angle;

    @XmlElement(name = "AngleStart")
    private Integer angleStart;

    @XmlElement(name = "AngleEnd")
    private Integer angleEnd;

    @XmlElement(name = "Level")
    private int level;
    
    private boolean obsoleted;

    public CustomDrawItem() {
        this.points = new ArrayList<>();
        obsoleted = false;
    }
    
    public void createLabel() {
        this.label = new Label();
        label.setColor(this.color);
        label.setContent(this.name);
        label.setEnabled(true);
    }
    
    @Override
    public String toString() {
        return String.format("name: %s type: %s", this.name, this.type);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the points
     */
    public List<Point2f> getPoints() {
        return points;
    }
    
    public Point2f getPoints(int index) {
        return points.get(index);
    }

    /**
     * @param points the points to set
     */
    public void setPoints(List<Point2f> points) {
        this.points = points;
    }

    public void addPoint(Point2f point2f) {
        this.points.add(point2f);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public ShapeType getType() {
        return type;
    }

    public void setType(ShapeType type) {
        this.type = type;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the angleStart
     */
    public Integer getAngleStart() {
        return angleStart;
    }

    /**
     * @param angleStart the angleStart to set
     */
    public void setAngleStart(Integer angleStart) {
        this.angleStart = angleStart;
    }

    /**
     * @return the angleEnd
     */
    public Integer getAngleEnd() {
        return angleEnd;
    }

    /**
     * @param angleEnd the angleEnd to set
     */
    public void setAngleEnd(Integer angleEnd) {
        this.angleEnd = angleEnd;
    }

    /**
     * @return the centerPoint
     */
    public Point2f getCenterPoint() {
        return centerPoint;
    }

    /**
     * @param centerPoint the centerPoint to set
     */
    public void setCenterPoint(Point2f centerPoint) {
        this.centerPoint = centerPoint;
    }

    /**
     * @return the obsoleted
     */
    public boolean isObsoleted() {
        return obsoleted;
    }

    /**
     * @param obsoleted the obsoleted to set
     */
    public void setObsoleted(boolean obsoleted) {
        this.obsoleted = obsoleted;
    }

    //</editor-fold>
}
