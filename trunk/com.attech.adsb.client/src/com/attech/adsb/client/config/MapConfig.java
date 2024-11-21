/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.XmlSerializer;
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
public class MapConfig extends com.attech.adsb.client.common.XmlSerializer {

    @XmlElement(name = "Line")
    private List<MapLine> lines;
    
    @XmlAttribute(name = "color")
    private String color;

    public MapConfig() {
    }

    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">

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
    
    //</editor-fold>
    
    public static MapConfig load(String fileName) {
	return (MapConfig) deserialize(fileName, MapConfig.class);
    }

    public void save(String filename) {
	serialize(filename, this);
    }

    public static void main(String[] args) {
        MapConfig map = XmlSerializer.load(Common.RES_MAP, MapConfig.class);
//	Map map = Map.load(Common.CFG_MAP);
	System.out.println("Load completed");
	System.out.println("Color: " + map.getColor());
	System.out.println("Line number: " + map.getLines().size());
    }
}
