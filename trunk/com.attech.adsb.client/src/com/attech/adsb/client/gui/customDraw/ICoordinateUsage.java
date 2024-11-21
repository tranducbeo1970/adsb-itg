/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.customDraw;

import com.attech.adsb.client.config.Point2f;

/**
 *
 * @author hong
 */
public interface ICoordinateUsage {
    
    void updateCoordinate(Point2f point);
    
    void captureCoordinate(Point2f point);
    
    boolean isAvailable();
}
