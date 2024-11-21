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
@XmlRootElement(name = "Map")
@XmlAccessorType(XmlAccessType.NONE)
public class Map extends com.attech.adsb.client.common.XmlSerializer {

    @XmlElement(name = "Line")
    private List<MapLine> lines;
    
    @XmlAttribute(name = "color")
    private String color;

    public Map() {
    }

    /**
     * @return the lines
     */
    public List<MapLine> getLines() {
	return lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(List<MapLine> lines) {
	this.lines = lines;
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
    
    public static Map load(String fileName) {
	return (Map) deserialize(fileName, Map.class);
    }

    public void save(String filename) {
	serialize(filename, this);
    }
    
}
