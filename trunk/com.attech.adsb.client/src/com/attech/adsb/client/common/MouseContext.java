/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.common.enums.MouseMode;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.gui.customDraw.ICoordinateUsage;
import java.awt.Point;

/**
 *
 * @author Saitama
 */
public class MouseContext {



    /**
     * @return the currentPoint
     */
    public Point2f getCurrentPoint() {
        return currentPoint;
    }

    /**
     * @param currentPoint the currentPoint to set
     */
    public void setCurrentPoint(Point2f currentPoint) {
        this.currentPoint = currentPoint;
    }

    private static MouseContext instance;
    private MouseMode mode;
    private boolean draggingMap;
    private boolean measuring;
    private Point selectedPoint;
    private Point2f currentPoint;
    private ICoordinateUsage coordinateCapptureRequester;

    public MouseContext() {
        measuring = false;
        draggingMap = false;
        this.mode = MouseMode.Normal;
    }
    
    

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the mode
     */
    public MouseMode getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(MouseMode mode) {
        this.mode = mode;
    }

    /**
     * @return the draggingMap
     */
    public boolean isDraggingMap() {
        return draggingMap;
    }

    /**
     * @param draggingMap the draggingMap to set
     */
    public void setDraggingMap(boolean draggingMap) {
        this.draggingMap = draggingMap;
    }

    /**
     * @return the selectedPoint
     */
    public Point getSelectedPoint() {
        return selectedPoint;
    }

    /**
     * @param selectedPoint the selectedPoint to set
     */
    public void setSelectedPoint(Point selectedPoint) {
        this.selectedPoint = selectedPoint;
    }

    /**
     * @return the measuring
     */
    public boolean isMeasuring() {
        return measuring;
    }

    /**
     * @param measuring the measuring to set
     */
    public void setMeasuring(boolean measuring) {
        this.measuring = measuring;
    }
    //</editor-fold>

    public static MouseContext instance() {
        if (instance == null) {
            instance = new MouseContext();
        }
        return instance;
    }
    
    /**
     * @return the coordinateCapptureRequester
     */
    public ICoordinateUsage getCoordinateCapptureRequester() {
        return coordinateCapptureRequester;
    }

    /**
     * @param coordinateCapptureRequester the coordinateCapptureRequester to set
     */
    public void setCoordinateCapptureRequester(ICoordinateUsage coordinateCapptureRequester) {
        this.coordinateCapptureRequester = coordinateCapptureRequester;
        this.mode = MouseMode.Capture;
    }

}
