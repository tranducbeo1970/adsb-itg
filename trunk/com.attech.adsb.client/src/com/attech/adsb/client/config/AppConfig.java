/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.XmlSerializer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "App")
@XmlAccessorType(XmlAccessType.NONE)
public class AppConfig extends XmlSerializer {

    @XmlElementWrapper(name = "Parameters")
    @XmlElement(name = "Parameter")
    private HashSet<KeyValueItem> parameterList;
    
    @XmlElement(name = "FilterCondition")
    private FilterCondition filterCondition;
    
    public AppConfig() {
        parameterList = new HashSet<>();
    }

    public static AppConfig load(String fileName) {
        return (AppConfig) deserialize(fileName, AppConfig.class);
    }

    public static AppConfig load() {
        return (AppConfig) XmlSerializer.deserialize(Common.CFG_APP, AppConfig.class);
    }

    @Override
    public void save(String filename) {
        serialize(filename, this);
    }

    public void save() {
        serialize(Common.CFG_APP, this);
    }

    public static void main(String[] args) {
        AppConfig graphic = new AppConfig();
        FilterCondition condition = new FilterCondition();
        condition.setActive(true);
        condition.setAltitudeHigh(340);
        condition.setAltitudeLow(0);
        condition.setCallSign("");
        condition.setHideTarget(false);
        graphic.setFilterCondition(condition);
        graphic.save("app.xml");
        System.out.println("Done");
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
//    /**
//     * @return the zoom
//     */
//    public float getZoom() {
//        return zoom;
//    }
//
//    /**
//     * @param zoom the zoom to set
//     */
//    public void setZoom(float zoom) {
//        this.zoom = zoom;
//    }
//
//    /**
//     * @return the homeY
//     */
//    public float getHomeY() {
//        return homeY;
//    }
//
//    /**
//     * @param homeY the homeY to set
//     */
//    public void setHomeY(float homeY) {
//        this.homeY = homeY;
//    }
//
//    /**
//     * @return the home
//     */
//    public float getHomeX() {
//        return homeX;
//    }
//
//    /**
//     * @param home the home to set
//     */
//    public void setHomeX(float home) {
//        this.homeX = home;
//    }
//
//    /**
//     * @return the bgColor
//     */
//    public String getBgColor() {
//        return bgColor;
//    }
//
//    /**
//     * @param bgColor the bgColor to set
//     */
//    public void setBgColor(String bgColor) {
//        this.bgColor = bgColor;
//    }
//
//    public String getBgColorDefault() {
//        return bgColorDefault;
//    }
//
//    public void setBgColorDefault(String bgColorDefault) {
//        this.bgColorDefault = bgColorDefault;
//    }
//
//    /**
//     * @return the waypointDisplay
//     */
//    public boolean isWaypointDisplay() {
//        return waypointDisplay;
//    }
//
//    /**
//     * @param waypointDisplay the waypointDisplay to set
//     */
//    public void setWaypointDisplay(boolean waypointDisplay) {
//        this.waypointDisplay = waypointDisplay;
//    }
//
//    /**
//     * @return the ndbDisplay
//     */
//    public boolean isNdbDisplay() {
//        return ndbDisplay;
//    }
//
//    /**
//     * @param ndbDisplay the ndbDisplay to set
//     */
//    public void setNdbDisplay(boolean ndbDisplay) {
//        this.ndbDisplay = ndbDisplay;
//    }
//
//    /**
//     * @return the vordmeDisplay
//     */
//    public boolean isVordmeDisplay() {
//        return vordmeDisplay;
//    }
//
//    /**
//     * @param vordmeDisplay the vordmeDisplay to set
//     */
//    public void setVordmeDisplay(boolean vordmeDisplay) {
//        this.vordmeDisplay = vordmeDisplay;
//    }
//
//    /**
//     * @return the labelDisplay
//     */
//    public boolean isLabelDisplay() {
//        return labelDisplay;
//    }
//
//    /**
//     * @param labelDisplay the labelDisplay to set
//     */
//    public void setLabelDisplay(boolean labelDisplay) {
//        this.labelDisplay = labelDisplay;
//    }
//
//    /**
//     * @return the corridorDisplay
//     */
//    public boolean isCorridorDisplay() {
//        return corridorDisplay;
//    }
//
//    /**
//     * @param corridorDisplay the corridorDisplay to set
//     */
//    public void setCorridorDisplay(boolean corridorDisplay) {
//        this.corridorDisplay = corridorDisplay;
//    }
//
//    /**
//     * @return the routeDisplay
//     */
//    public boolean isRouteDisplay() {
//        return routeDisplay;
//    }
//
//    /**
//     * @param routeDisplay the routeDisplay to set
//     */
//    public void setRouteDisplay(boolean routeDisplay) {
//        this.routeDisplay = routeDisplay;
//    }
//
//    public boolean isSectorDisplay() {
//        return sectorDisplay;
//    }
//
//    public void setSectorDisplay(boolean sectorDisplay) {
//        this.sectorDisplay = sectorDisplay;
//    }
//
//    public boolean isLocalsectorDisplay() {
//        return localsectorDisplay;
//    }
//
//    public void setLocalsectorDisplay(boolean localsectorDisplay) {
//        this.localsectorDisplay = localsectorDisplay;
//    }
//
//    public boolean isAmaDisplay() {
//        return amaDisplay;
//    }
//
//    public void setAmaDisplay(boolean amaDisplay) {
//        this.amaDisplay = amaDisplay;
//    }
//
//    /**
//     * @return the targetLabel
//     */
//    public TargetLabelDisplay getTargetLabel() {
//        return targetLabel;
//    }
//
//    /**
//     * @param targetLabel the targetLabel to set
//     */
//    public void setTargetLabel(TargetLabelDisplay targetLabel) {
//        this.targetLabel = targetLabel;
//    }
//
//    /**
//     * @return the selectedAirport
//     */
//    public String getSelectedAirport() {
//        return selectedAirport;
//    }
//
//    /**
//     * @param selectedAirport the selectedAirport to set
//     */
//    public void setSelectedAirport(String selectedAirport) {
//        this.selectedAirport = selectedAirport;
//    }

    /**
     * @return the filterCondition
     */
    public FilterCondition getFilterCondition() {
        return filterCondition;
    }

    /**
     * @param filterCondition the filterCondition to set
     */
    public void setFilterCondition(FilterCondition filterCondition) {
        this.filterCondition = filterCondition;
    }

    public HashSet<KeyValueItem> getParameterList() {
        return parameterList;
    }

    public void setParameterList(HashSet<KeyValueItem> bValueList) {
        this.parameterList = bValueList;
    }
    
    public KeyValueItem getParameter(String key) {
        for (KeyValueItem item : this.parameterList) {
            if (item.getKey().equalsIgnoreCase(key)) {
                return item;
            }
        }
        return null;
    }
    
    public void setParameter(KeyValueItem item) {
        if (!this.parameterList.add(item)) {
            this.parameterList.remove(item);
            this.parameterList.add(item);
        }
        
    }
    
    public void setParameter(String key, Boolean value) {
        KeyValueItem parameter = new KeyValueItem();
        parameter.setKey(key);
        parameter.setBoolean(value);
        this.setParameter(parameter);
    }
    
    public void setParameter(String key, String value) {
        KeyValueItem parameter = new KeyValueItem();
        parameter.setKey(key);
        parameter.setValue(value);
        this.setParameter(parameter);
    }
    
    public void setParameter(String key, int value) {
        KeyValueItem parameter = new KeyValueItem();
        parameter.setKey(key);
        parameter.setValue(String.valueOf(value));
        this.setParameter(parameter);
    }
    
    public Boolean getBoolean(String key) {
        for (KeyValueItem item : this.parameterList) {
            if (item.getKey().equalsIgnoreCase(key)) {
                return item.getBoolean();
            }
        }
        return false;
    }
    
    public String getString(String key) {
         for (KeyValueItem item : this.parameterList) {
            if (item.getKey().equalsIgnoreCase(key)) {
                return item.getValue();
            }
        }
        return "";
    }
    
    public int getInt(String key) {
        for (KeyValueItem item : this.parameterList) {
            if (item.getKey().equalsIgnoreCase(key)) {
                return Integer.parseInt(item.getValue());
            }
        }
        return 0;
    }
    
    //</editor-fold>
}
