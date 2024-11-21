/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
/**
 *
 * @author anhth
 */
@XmlRootElement(name = "CircularSector")
@XmlAccessorType(XmlAccessType.NONE)
public class CircularSector {
    @XmlElement(name="PointCenter")
    private Point2f pointCenter;
    @XmlElement(name="Point1")
    private Point2f point1;
    @XmlElement(name="Point2")
    private Point2f point2;
    
    public CircularSector(){
        pointCenter = new Point2f();
        point1 = new Point2f();
        point2 = new Point2f();
    }
    
    public boolean validate(float lng, float lat) {
        Double bearingAngle = Bearing2Point(lng, lat);
        Double bearing1 = Bearing2Point(point1.x, point1.y);
        Double bearing2 = Bearing2Point(point2.x, point2.y);
        if (bearingAngle < Double.min(bearing1, bearing2) || bearingAngle > Double.max(bearing1, bearing2)) 
            return false;
        double d= Distance.GetDistanceBetweenPoints(pointCenter.y, pointCenter.x, lat, lng);
        double d1= Distance.GetDistanceBetweenPoints(pointCenter.y, pointCenter.x, point1.y, point1.x);
        double d2= Distance.GetDistanceBetweenPoints(pointCenter.y, pointCenter.x, point2.y, point2.x);
        if (d > Double.max(d1, d2)) 
            return false;  
        return true;
    }
    
    public Double Bearing2Point(float longtitude, float latitude) {
        return Distance.Bearing((double)longtitude, (double)latitude, (double)this.pointCenter.getX(), (double)this.pointCenter.getY());
    }
    
    public void save(String file) {
        XmlSerializer.serialize(file, this);
    }
    
    public void save(File file) {
        XmlSerializer.serialize(file, this);
    }
    
    public static CircularSector load(File file) {
        return (CircularSector) XmlSerializer.deserialize(file, CircularSector.class);
    }
    
    public static CircularSector load(String location) {
        return (CircularSector) XmlSerializer.deserialize(location, CircularSector.class);
    }

    /**
     * @return the pointCenter
     */
    public Point2f getPointCenter() {
        return pointCenter;
    }

    /**
     * @param pointCenter the pointCenter to set
     */
    public void setPointCenter(Point2f pointCenter) {
        this.pointCenter = pointCenter;
    }

    /**
     * @return the point1
     */
    public Point2f getPoint1() {
        return point1;
    }

    /**
     * @param point1 the point1 to set
     */
    public void setPoint1(Point2f point1) {
        this.point1 = point1;
    }

    /**
     * @return the point2
     */
    public Point2f getPoint2() {
        return point2;
    }

    /**
     * @param point2 the point2 to set
     */
    public void setPoint2(Point2f point2) {
        this.point2 = point2;
    }
}
