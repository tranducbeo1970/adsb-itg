/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.graphic.Convertor;
import com.jogamp.opengl.GL2;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "Plot")
@XmlAccessorType(XmlAccessType.NONE)
public class Plot {

    @XmlAttribute(name = "longtitude")
    private double longitude;

    @XmlAttribute(name = "latitude")
    private double latitude;

    @XmlAttribute(name = "flight-level")
    private double flightLevel;

    @XmlAttribute(name = "nic")
    private int nic;

    @XmlAttribute(name = "nac")
    private int nac;

    @XmlAttribute(name = "sil")
    private int sil;

    private float x;
    private float y;
    private float z;

    @XmlAttribute(name = "time-of-day")
    private double timeOfDay;
    
    private long timeStamp;
    
    private boolean warn;

    public Plot() {
    }
    
    public Plot(double longtitude, double latitude) {
        this.x = Convertor.fromWGS842OpenGL(longtitude);
        this.y = Convertor.fromWGS842OpenGL(latitude);
        this.timeStamp = System.currentTimeMillis();
    }
    
    public Plot(float x, float y, float z, boolean warn) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.warn = warn;
         this.timeStamp = System.currentTimeMillis();
    }

    public Plot(double longtitude, double latitude, double flightLevel, double timeOfDay) {
        this.longitude = longtitude;
        this.latitude = latitude;
        this.flightLevel = flightLevel;
        this.timeOfDay = timeOfDay;
        this.x = Convertor.fromWGS842OpenGL(longtitude);
        this.y = Convertor.fromWGS842OpenGL(latitude);
        this.z = (float) flightLevel;
        this.timeStamp = System.currentTimeMillis();
    }
    
    public boolean isValid(long period) {
        return this.timeStamp > period;
    }

    public Plot(double longtitude, double latitude, double flightLevel, double timeOfDay, int nic, int nac, int sil) {
        this(longtitude, latitude, flightLevel, timeOfDay);
        this.nic = nic;
        this.nac = nac;
        this.sil = sil;
    }

    public void draw(GL2 gl) {
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the flightLevel
     */
    public double getFlightLevel() {
        return flightLevel;
    }

    /**
     * @param flightLevel the flightLevel to set
     */
    public void setFlightLevel(double flightLevel) {
        this.flightLevel = flightLevel;
    }

    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public float getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * @return the timeOfDay
     */
    public double getTimeOfDay() {
        return timeOfDay;
    }

    /**
     * @param timeOfDay the timeOfDay to set
     */
    public void setTimeOfDay(double timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    /**
     * @return the nic
     */
    public int getNic() {
        return nic;
    }

    /**
     * @param nic the nic to set
     */
    public void setNic(int nic) {
        this.nic = nic;
    }

    /**
     * @return the nac
     */
    public int getNac() {
        return nac;
    }

    /**
     * @param nac the nac to set
     */
    public void setNac(int nac) {
        this.nac = nac;
    }

    /**
     * @return the sil
     */
    public int getSil() {
        return sil;
    }

    /**
     * @param sil the sil to set
     */
    public void setSil(int sil) {
        this.sil = sil;
    }
    
    
    /**
     * @return the timeStamp
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    
    /**
     * @return the warn
     */
    public boolean isWarn() {
        return warn;
    }

    /**
     * @param warn the warn to set
     */
    public void setWarn(boolean warn) {
        this.warn = warn;
    }

    //</editor-fold>    
}
