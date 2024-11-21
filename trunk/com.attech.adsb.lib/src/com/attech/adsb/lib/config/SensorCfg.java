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
@XmlRootElement(name = "Sensor")
@XmlAccessorType(XmlAccessType.NONE)
public class SensorCfg {
    
    @XmlAttribute(name = "sic")
    private int sic;
    
    @XmlAttribute(name = "build-report")
    private boolean buildReport;
    
     @XmlElement(name = "report")
    private List<String> reports;
    
    public SensorCfg() {
        this.reports = new ArrayList<>();
    }

    /**
     * @return the sic
     */
    public int getSic() {
        return sic;
    }

    /**
     * @param sic the sic to set
     */
    public void setSic(int sic) {
        this.sic = sic;
    }

    /**
     * @return the reports
     */
    public List<String> getReports() {
        return reports;
    }

    /**
     * @param reports the reports to set
     */
    public void setReports(List<String> reports) {
        this.reports = reports;
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
