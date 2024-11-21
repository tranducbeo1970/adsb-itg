/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Point")
@XmlAccessorType(XmlAccessType.NONE)
public class Point2f {
    
    @XmlAttribute(name="y")
    public float y;//lat
    
    @XmlAttribute(name="x")
    public float x;//lon
    
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
    
    public void set(Point2f p) {
        this.x = p.x;
        this.y = p.y;
    }
    
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }

    public void setX(float x) { this.x = x; }

    public float getY() { return y; }

    public void setY(float y) { this.y = y; }
    
    @Override
    public String toString() {
        return "x: "  +this.x + " y: " + this.y;
    }
}
