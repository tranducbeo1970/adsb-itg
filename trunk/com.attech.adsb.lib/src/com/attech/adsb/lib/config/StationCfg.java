/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "GroundStation")
@XmlAccessorType(XmlAccessType.NONE)
public class StationCfg {
    
    @XmlAttribute(name = "name")
    private String name;
   
    @XmlAttribute(name = "build-report")
    private boolean buildReport;

    @XmlElement(name = "report")
    private List<String> includeReport;
    
    @XmlElement(name = "sic")
    private List<Integer> sics;
    
    public StationCfg() {
        sics = new ArrayList<>();
        includeReport = new ArrayList<>();
    }
    
    public StationCfg(String name) {
        this();
        this.name = name;
    }
    
    public StationCfg(String name, int... sicArray) {
        this(name);
        if (sicArray == null) return;
        for (int sic : sicArray) {
            this.sics.add(sic);
        }
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
     * @return the sics
     */
    public List<Integer> getSics() {
        return sics;
    }

    /**
     * @param sics the sics to set
     */
    public void setSics(List<Integer> sics) {
        this.sics = sics;
    }
    
    public void addSic(int sic) {
        this.sics.add(sic);
    }
    
    public boolean contain (int sic) {
        return this.sics.contains(sic);
    }

    /**
     * @return the includeReport
     */
    public List<String> getIncludeReport() {
        return includeReport;
    }

    /**
     * @param includeReport the includeReport to set
     */
    public void setIncludeReport(List<String> includeReport) {
        this.includeReport = includeReport;
    }

    /**
     * @return the buildReport
     */
    public boolean isBuildReport() {
        return buildReport;
    }

    /**
     * @param buildReport the buildReport to set
     */
    public void setBuildReport(boolean buildReport) {
        this.buildReport = buildReport;
    }
    
}
