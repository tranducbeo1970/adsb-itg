/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hong
 */
@XmlRootElement(name = "Report")
@XmlAccessorType(XmlAccessType.NONE)
public class ReportCfg {
    
    @XmlAttribute(name = "name")
    private String name;
    
    @XmlAttribute(name = "min-fl")
    private Float minFL;
    
    @XmlAttribute(name = "max-fl")
    private Float maxFL;
    
    @XmlAttribute(name = "min-distance")
    private Double minDistance;
    
    @XmlAttribute(name = "max-distance")
    private Double maxDistance;
    
    @XmlAttribute(name = "export-detail")
    private boolean exportDetail;
    
    @XmlAttribute(name = "export-flight-trace")
    private boolean exportFlightTrace;
    
    public ReportCfg() {
    }
    
    public ReportCfg(String name, Double min, Double max) {
        this.name = name;
        this.minDistance = min;
        this.maxDistance = max;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the minDistance
     */
    public Double getMinDistance() {
        return minDistance;
    }

    /**
     * @param minDistance the minDistance to set
     */
    public void setMinDistance(Double minDistance) {
        this.minDistance = minDistance;
    }

    /**
     * @return the maxDistance
     */
    public Double getMaxDistance() {
        return maxDistance;
    }

    /**
     * @param maxDistance the maxDistance to set
     */
    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    /**
     * @return the exportDetail
     */
    public boolean isExportDetail() {
        return exportDetail;
    }

    /**
     * @param exportDetail the exportDetail to set
     */
    public void setExportDetail(boolean exportDetail) {
        this.exportDetail = exportDetail;
    }

    /**
     * @return the minFL
     */
    public Float getMinFL() {
        return minFL;
    }

    /**
     * @param minFL the minFL to set
     */
    public void setMinFL(Float minFL) {
        this.minFL = minFL;
    }

    /**
     * @return the maxFL
     */
    public Float getMaxFL() {
        return maxFL;
    }

    /**
     * @param maxFL the maxFL to set
     */
    public void setMaxFL(Float maxFL) {
        this.maxFL = maxFL;
    }

    /**
     * @return the exportFlightTrace
     */
    public boolean isExportFlightTrace() {
        return exportFlightTrace;
    }

    /**
     * @param exportFlightTrace the exportFlightTrace to set
     */
    public void setExportFlightTrace(boolean exportFlightTrace) {
        this.exportFlightTrace = exportFlightTrace;
    }


    
}
