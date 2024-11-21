/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.util;

/**
 *
 * @author Andh
 */
public class Wgs84Position {
    private double longtitude;
    private double latitude;
    
    public Wgs84Position() {
    }
    
    public Wgs84Position(double longtitude, double latitude) {
        this.longtitude = longtitude;
        this.latitude = latitude;
    }
    
    public double getLongtitude() {
        return this.longtitude;
    }
    
    public double getLatitude() {
        return this.latitude;
    }
    
    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
