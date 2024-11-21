/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "Station")
@XmlAccessorType(XmlAccessType.NONE)
public class GroundStation {

    @XmlAttribute(name = "Name")
    private String name;

    @XmlAttribute(name = "Enabled")
    private Boolean enabled;

    @XmlElement(name = "X")
    private float x;

    @XmlElement(name = "Y")
    private float y;

    @XmlElement(name = "Z1-Index")
    private Float zIndex;

    @XmlElement(name = "Weight")
    private Integer weight;

    @XmlElement(name = "Size")
    private Integer size;

    @XmlElement(name = "Color")
    private String color;

    @XmlElement(name = "Label")
    private Label label;

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    
    
    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @return the zIndex
     */
    public Float getzIndex() {
        return zIndex;
    }

    /**
     * @param zIndex the zIndex to set
     */
    public void setzIndex(Float zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * @return the weight
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
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

    
    //</editor-fold>
}
