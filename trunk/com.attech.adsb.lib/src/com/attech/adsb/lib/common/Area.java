/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Area")
@XmlAccessorType(XmlAccessType.NONE)
public class Area {
    
    @XmlElement(name="Point")
    private List<Point2f> points;
    
    public Area() {
        this.points = new ArrayList<>();
    }
    
    public void save(String file) {
        XmlSerializer.serialize(file, this);
    }
    
    public void save(File file) {
        XmlSerializer.serialize(file, this);
    }
    
    public static Area load(File file) {
        return (Area) XmlSerializer.deserialize(file, Area.class);
    }
    
    public static Area load(String location) {
        return (Area) XmlSerializer.deserialize(location, Area.class);
    }

    /**
     * @return the points
     */
    public List<Point2f> getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(List<Point2f> points) {
        this.points = points;
    }
    
    public void add(float x, float y) {
        Point2f p = new Point2f(x, y);
        this.points.add(p);
    }
    
    public boolean validate(float lng, float lat) {
        int j = points.size() - 1;
        boolean oddNodes = false;
        for (int i = 0; i < points.size(); i++) {

            Point2f vi = this.points.get(i);
            Point2f vj = this.points.get(j);
            if ((vi.y < lat && vj.y >= lat
                    || vj.y < lat && vi.y >= lat)
                    && (vi.x <= lng || vj.x <= lng)) {
                oddNodes ^= (vi.x + (lat - vi.y) / (vj.y - vi.y) * (vj.x - vi.x) < lng);
            }
            j = i;
        }
        return oddNodes;
    }
    
    public boolean validate(double lng, double lat) {
        int j = points.size() - 1;
        boolean oddNodes = false;
        for (int i = 0; i < points.size(); i++) {

            Point2f vi = this.points.get(i);
            Point2f vj = this.points.get(j);
            if ((vi.y < lat && vj.y >= lat
                    || vj.y < lat && vi.y >= lat)
                    && (vi.x <= lng || vj.x <= lng)) {
                oddNodes ^= (vi.x + (lat - vi.y) / (vj.y - vi.y) * (vj.x - vi.x) < lng);
            }
            j = i;
        }
        return oddNodes;
    }
}
