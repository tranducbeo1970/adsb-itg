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
@XmlRootElement(name = "Airport")
@XmlAccessorType(XmlAccessType.NONE)
public class Airport extends com.attech.adsb.client.common.XmlSerializer  {


    @XmlAttribute(name = "name")
    private String name;
    
    @XmlAttribute(name = "path")
    private String path;
    
    @XmlAttribute(name = "type")
    private String type;
    
    @XmlAttribute(name = "code")
    private String code;
    
    @XmlAttribute(name = "iata")
    private String iata;
    
    @XmlAttribute(name = "icao")
    private String icao;
    
    @XmlAttribute(name = "zoomLevel")
    private Integer zoomLevel;
    
    @XmlAttribute(name = "displayCircle")
    private Boolean displayCircle;
    
    @XmlAttribute(name = "displayArc")
    private Boolean displayArc;
    
    @XmlAttribute(name = "enabled")
    private Boolean enabled;
    
    @XmlAttribute(name = "selected")
    private Boolean selected;
        
    @XmlElement(name = "Point")
    private Point2f point;
    
    @XmlElement(name = "Circle")
    private List<Circles> circle;
    
    @XmlElement(name = "Regions")
    private List<Regions> regions;

    public Airport() {
       
    }
    
    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public static Airport load(String fileName) {
	return (Airport) deserialize(fileName, Airport.class);
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public Boolean getDisplayCircle() {
        return displayCircle;
    }

    public void setDisplayCircle(Boolean displayCircle) {
        this.displayCircle = displayCircle;
    }

    public Boolean getDisplayArc() {
        return displayArc;
    }

    public void setDisplayArc(Boolean displayArc) {
        this.displayArc = displayArc;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Point2f getPoint() {
        return point;
    }

    public void setPoint(Point2f point) {
        this.point = point;
    }   

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
    public List<Regions> getRegions() {
        return regions;
    }

    public void setRegions(List<Regions> regions) {
        this.regions = regions;
    }

    public void setRegions(Regions regions) {
        this.regions.add(regions);
    }

    public List<Circles> getCircle() {
        return circle;
    }

    public void setCircle(List<Circles> circle) {
        this.circle = circle;
    }
    
    

    /**
     * @return the zoomLevel
     */
    public Integer getZoomLevel() {
        return zoomLevel;
    }

    /**
     * @param zoomLevel the zoomLevel to set
     */
    public void setZoomLevel(Integer zoomLevel) {
        this.zoomLevel = zoomLevel;
    }
    //</editor-fold>     
}
