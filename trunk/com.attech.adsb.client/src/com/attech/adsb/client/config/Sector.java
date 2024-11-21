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
@XmlRootElement(name = "Sector")
@XmlAccessorType(XmlAccessType.NONE)
public class Sector{

    @XmlElement(name = "Line")
    private List<SectorLine> lines;
    
    @XmlAttribute(name = "name")
    private String name;
    
    @XmlAttribute(name = "enabled")
    private boolean enable;
    
    @XmlAttribute(name = "color")
    private String color;
    
    @XmlAttribute(name = "weight")
    private int weight;
    
    @XmlAttribute(name = "localsector")
    private boolean localsector;
    
    @XmlAttribute(name = "displayfixpoint")
    private boolean displayfixpoint;

    public Sector() {
	this.lines = new ArrayList<>();
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the lines
     */
    public List<SectorLine> getLines() {
	return lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(List<SectorLine> lines) {
	this.lines = lines;
    }
    
    public void addSectorLine(SectorLine line) {
	this.lines.add(line);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public boolean isEnable() {
	return enable;
    }

    public void setEnable(boolean enable) {
	this.enable = enable;
    }

    public boolean isLocalsector() {
	return localsector;
    }

    public void setLocalsector(boolean localsector) {

        this.localsector = localsector;
    } 

    public boolean isDisplayfixpoint() {
        return displayfixpoint;
    }

    public void setDisplayfixpoint(boolean displayfixpoint) {
        this.displayfixpoint = displayfixpoint;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }     
    //</editor-fold>
    
}
