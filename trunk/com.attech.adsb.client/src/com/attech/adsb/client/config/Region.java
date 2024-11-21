/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.graphic.Convertor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NhuongND
 */
@XmlRootElement(name = "Region")
@XmlAccessorType(XmlAccessType.NONE)
public class Region {

    
    @XmlAttribute(name = "name")
    private String name;
            
    @XmlAttribute(name = "type")
    private String type;
    
    @XmlAttribute(name = "alt")
    private Integer alt;    
    
    @XmlAttribute(name = "enabled")
    private Boolean enabled;
    
    @XmlAttribute(name = "displayName")
    private Boolean displayName;
    
    @XmlAttribute(name = "selected")
    private Boolean selected;
    
    @XmlElement(name = "RegionLine")
    private List<RegionLine> lstRegionline; 
    
    @XmlElement(name = "Ref")
    private List<String> ref;
    
    private final java.util.Map<String, Point2f> points;
        
    public Region() {       
        this.ref = new ArrayList<>();
        this.lstRegionline = new ArrayList<>();
        this.points = new HashMap<>();
    }
    
    public void loadFixPoint() {
        List<Point2f> lstPointRWY = new ArrayList<>();
        if(this.ref != null){
           for (String refFile : this.ref) {
                ListPoints lstPoints = ListPoints.load(refFile, ListPoints.class);
                for (PointX fixPoint : lstPoints.getFixPoint()) {
                    if(this.type.equals("RWY")){
                        lstPointRWY.add(Convertor.fromWGS842OpenGL(new Point2f(fixPoint.getX(), fixPoint.getY())));
                    }                    
                    if (points.containsKey(fixPoint.getName())) continue;
                    points.put(fixPoint.getName(), new Point2f(fixPoint.getX(), fixPoint.getY()));                
                }
            }
           
           if(this.type.equals("RWY")){
               for (RegionLine regionLine : this.lstRegionline) {                       
                    regionLine.setPoint2fs(lstPointRWY);
                } 
           } else if(this.type.equals("PROC")){
               for (RegionLine regionLine : this.lstRegionline) {
                    List<Point2f> point2fs = new ArrayList<>();
                    for (String key : regionLine.getPoints()) {
                        Point2f point2f = this.points.get(key);
                        if (point2f == null) continue;
                        point2fs.add(Convertor.fromWGS842OpenGL(point2f));
                    }
                    regionLine.setPoint2fs(point2fs);
                }
           } else {
               for (RegionLine regionLine : this.lstRegionline) {
                    List<Point2f> point2fs = new ArrayList<>();
                    for (String key : regionLine.getPoints()) {
                        Point2f point2f = this.points.get(key);
                        if (point2f == null) continue;
                        point2fs.add(Convertor.fromWGS842OpenGL(point2f));
                    }
                    regionLine.setPoint2fs(point2fs);
                }
           }
           
           
        }
    }
          
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">   
    public Point2f getPoint2f(String key) {
        return this.points.get(key);
    }

    public void addLine(RegionLine regionLine) {
        this.lstRegionline.add(regionLine);
    }
    
    public void addRef(String file) {
        this.ref.add(file);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAlt() {
        return alt;
    }

    public void setAlt(Integer alt) {
        this.alt = alt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getDisplayName() {
        return displayName;
    }

    public void setDisplayName(Boolean displayName) {
        this.displayName = displayName;
    }   

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
    public List<RegionLine> getLstRegionline() {
        return lstRegionline;
    }

    public void setLstRegionline(List<RegionLine> lstRegionline) {
        this.lstRegionline = lstRegionline;
    }
    
    public List<String> getRef() {
        return ref;
    }

    public void setRef(List<String> ref) {
        this.ref = ref;
    }
    
    public Map<String, Point2f> getPoints() {
        return points;
    }
        
    //</editor-fold>
}
