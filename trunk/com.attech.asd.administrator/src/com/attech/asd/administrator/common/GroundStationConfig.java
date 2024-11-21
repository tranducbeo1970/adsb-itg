/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import com.attech.asd.administrator.XmlSerializer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "GroundStations")
@XmlAccessorType(XmlAccessType.NONE)
public class GroundStationConfig extends XmlSerializer {


    @XmlElement(name = "Station")
    private List<GroundStation> groundStations;
    
    @XmlElement(name = "Icon")
    private String icon;
    
    //XYPointerAnnotation
    public List<XYPointerAnnotation> getXYPointer(){
        List<XYPointerAnnotation> tmp = new ArrayList<>();
        groundStations.forEach((point) -> {
            XYPointerAnnotation p = new XYPointerAnnotation(point.getName(),point.getX(),point.getY(),60);
            p.setArrowLength(0.1);
            p.setArrowWidth(0.1);
            tmp.add(p);    
        });
        return tmp;
    }
    
    public List<XYTextAnnotation> getXYTextPoint(){
        List<XYTextAnnotation> tmp = new ArrayList<>();
        groundStations.forEach((point) -> {
            XYTextAnnotation p = new XYTextAnnotation("△", point.getX(), point.getY()); // ▵  △
            p.setPaint(Color.decode(point.getColor()));
            tmp.add(p);    
        });
        return tmp;
    }
    
    public List<XYTextAnnotation> getXYTextName(){
        List<XYTextAnnotation> tmp = new ArrayList<>();
        groundStations.forEach((point) -> {
            XYTextAnnotation p = new XYTextAnnotation(point.getName(), point.getX(), point.getY() + 0.08); // + 0.08 to higher
            p.setPaint(Color.decode(point.getColor()));
            tmp.add(p);    
        });
        return tmp;
    }
    

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the groundStations
     */
    public List<GroundStation> getGroundStations() {
        return groundStations;
    }

    /**
     * @param groundStations the groundStations to set
     */
    public void setGroundStations(List<GroundStation> groundStations) {
        this.groundStations = groundStations;
    }
    
    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    //</editor-fold>
    
    public static void main(String[] args) {
        GroundStationConfig config = GroundStationConfig.load("res/ndb.xml", GroundStationConfig.class);
        List<GroundStation> stations = config.getGroundStations();
        for (GroundStation station : stations) {
            System.out.println("Name: " + station.getName());
        }
    }
}