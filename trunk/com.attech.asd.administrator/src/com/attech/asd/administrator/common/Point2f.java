/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Point")
@XmlAccessorType(XmlAccessType.NONE)
public class Point2f {
    
    /***
     * Y index (Decaster) Or  Latitude (WGS84)
     */
    @XmlAttribute(name="y")
    public float y;
    
     /***
     * X index (Decaster) Or  Longtitude (WGS84)
     */
    @XmlAttribute(name="x")
    public float x;
    
    @XmlAttribute(name="z")
    public Float z;
    
    @XmlAttribute(name="color")
    private String color;
    
    @XmlAttribute(name="size")
    private int size;
    
    public Point2f() {
    }
    
    public Point2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Point2f(Point2f point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point2f(double longtitude, double latitude) {
        this.x = (float) longtitude;
        this.y = (float) latitude;
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    
    public void set(Point2f p) {
        this.x = p.x;
        this.y = p.y;
    }
    
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }

    public void setX(float longtitude) { this.x = longtitude; }

    public float getY() { return y; }

    public void setY(float latitude) { this.y = latitude; }
    
    public void setLongtitude(float longtitude) {
	this.x = longtitude;
    }
    
    public float getLongtitude() {
	return this.x;
    }
    
    public void setLatitude(float latitude) {
	this.y = latitude;
    }
    
    public float getLatitude() {
	return this.y;
    }
    
    /**
     * @return the z
     */
    public Float getZ() {
	return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(Float z) {
	this.z = z;
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
     * @return the size
     */
    public int getSize() {
	return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
	this.size = size;
    }
    
    public Point2f makeCopy() {
        return new Point2f(x, y);
    }
    
    public Point2f makeCopy(float paddingX, float paddingY) {
        return new Point2f(x + paddingX, y + paddingY);
    }
    //</editor-fold>
    
    @Override
    public String toString() {
        return "lon "  +this.x + " lat " + this.y;
    }

}
