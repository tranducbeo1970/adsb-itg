/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.config.Point2f;

/**
 *
 * @author NhuongND
 */
public class TargetPosition {
    public Point2f pos = new Point2f();
    private int alt;
    private double reveiveTimeStamp;
    private double localTimeStamp;

    public int getAlt() {
        return alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public double getReveiveTimeStamp() {
        return reveiveTimeStamp;
    }

    public void setReveiveTimeStamp(double reveiveTimeStamp) {
        this.reveiveTimeStamp = reveiveTimeStamp;
    }

    public double getLocalTimeStamp() {
        return localTimeStamp;
    }

    public void setLocalTimeStamp(double localTimeStamp) {
        this.localTimeStamp = localTimeStamp;
    }
    
}
