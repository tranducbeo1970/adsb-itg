/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import com.attech.asd.administrator.XmlSerializer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.jfree.chart.annotations.XYLineAnnotation;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "Map")
@XmlAccessorType(XmlAccessType.NONE)
public class MapConfig extends XmlSerializer {

    @XmlElement(name = "Line")
    private List<MapLine> lines;
    
    @XmlAttribute(name = "color")
    private String color;

    public MapConfig() {
    }
    
    public List<XYLineAnnotation> getXYLines(){
        List<XYLineAnnotation> tmp = new ArrayList<>();
        lines.forEach((mapline) -> {
            for (int i = 0; i< mapline.getPoints().size() - 1; i ++){
                tmp.add(new XYLineAnnotation(
                        mapline.getPoints().get(i).x, 
                        mapline.getPoints().get(i).y,
                        mapline.getPoints().get(i + 1).x, 
                        mapline.getPoints().get(i + 1).y,
                        new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f), 
                        Color.decode(mapline.getColor())));
            }
        });
        return tmp;
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
}
