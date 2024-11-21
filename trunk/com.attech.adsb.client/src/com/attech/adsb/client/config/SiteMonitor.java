/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NhuongND
 */
@XmlRootElement(name = "SiteMonitor")
@XmlAccessorType(XmlAccessType.NONE)
public class SiteMonitor extends com.attech.adsb.client.common.XmlSerializer {
    
    @XmlElement(name = "Address")
    private List<String> lstSite; 
    
    @XmlAttribute(name = "display")
    private boolean display;
    
    public SiteMonitor() {
        this.lstSite = new ArrayList<>();
    }
    
    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public boolean contain(String address) {
        return this.lstSite.contains(address);
    }
    
    public static SiteMonitor load(String fileName) {
	return (SiteMonitor) deserialize(fileName, SiteMonitor.class);
    }
    
    public static void main(String [] args) {
        SiteMonitor siteMonitor = new SiteMonitor();
        siteMonitor.addSite("8880FA");
        siteMonitor.addSite("8880FB");
        siteMonitor.addSite("8880FC");
        siteMonitor.addSite("8880FD");
        siteMonitor.addSite("8880FE");
        siteMonitor.save("site-monitor.xml");
        System.out.println("OK");
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
  
    public List<String> getSite() {
	return lstSite;
    }

    public void setSite(List<String> lstSite) {
	this.lstSite = lstSite;
    }

    public void addSite(String site) {
	this.lstSite.add(site);
    }
    
    public boolean getDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    
    //</editor-fold>
    
}
