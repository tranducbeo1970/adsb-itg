/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import java.text.DecimalFormat;

/**
 *
 * @author andh
 */

public class Wgs84Coordinate {

    
    private static DecimalFormat df1 = new DecimalFormat("###");
    private static  DecimalFormat df2 = new DecimalFormat("00");

    private int deg;
    private int min;
    private int sec;
    private String locateChar;

    private float decimal;
    
    public Wgs84Coordinate() {
        
    }
    
    public Wgs84Coordinate(float decimal) {
        this.decimal = decimal;
    }
    
    public Wgs84Coordinate(int deg, int min, int sec) {
        this.deg = deg;
        this.min = min;
        this.sec = sec;
        
    }
    
    public Wgs84Coordinate(int deg, int min, int sec, String locateChar) {
        this.deg = deg;
        this.min = min;
        this.sec = sec;
        this.locateChar = locateChar;
    }
    
    public String getLontitudeCharacter() {
        return deg < 0 ? "W" : "E" ;
    }
    
     public String getLatitdeCharacter() {
        return deg < 0 ?"S" : "N";
    }
    
    @Override
    public String toString() {
        return "";
    }
    
    public String getLongtitude() {
        if (this.locateChar != null && !this.locateChar.isEmpty()) {
            return String.format("%s°%s'%s %s", df1.format(Math.abs(deg)), df2.format(min), df2.format(sec), this.locateChar);
        }
        
        if (deg < 0) {
            return String.format("%s°%s'%s W", df1.format(Math.abs(deg)), df2.format(min), df2.format(sec));
        } 
        return String.format("%s°%s'%s E", df1.format(Math.abs(deg)), df2.format(min), df2.format(sec));
    }
    
    public String getLatitude() {
        if (this.locateChar != null && !this.locateChar.isEmpty()) {
            return String.format("%s°%s'%s %s", df1.format(Math.abs(deg)), df2.format(min), df2.format(sec), this.locateChar);
        }
        
        if (deg < 0) {
            return String.format("%s°%s'%s S", df1.format(Math.abs(deg)), df2.format(min), df2.format(sec));
        } 
        return String.format("%s°%s'%s N", df1.format(Math.abs(deg)), df2.format(min), df2.format(sec));
    }

    /**
     * @return the deg
     */
    public int getDeg() {
        return deg;
    }

    /**
     * @param deg the deg to set
     */
    public void setDeg(int deg) {
        this.deg = deg;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the sec
     */
    public int getSec() {
        return sec;
    }

    /**
     * @param sec the sec to set
     */
    public void setSec(int sec) {
        this.sec = sec;
    }

    /**
     * @return the decimal
     */
    public float getDecimal() {
        return decimal;
    }

    /**
     * @param decimal the decimal to set
     */
    public void setDecimal(float decimal) {
        this.decimal = decimal;
    }
    
    /**
     * @return the locateChar
     */
    public String getLocateChar() {
        return locateChar;
    }

    /**
     * @param locateChar the locateChar to set
     */
    public void setLocateChar(String locateChar) {
        this.locateChar = locateChar;
    }

}
