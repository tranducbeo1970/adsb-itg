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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "VVRs")
@XmlAccessorType(XmlAccessType.NONE)
public class VVRs extends com.attech.adsb.client.common.XmlSerializer {

    @XmlElement(name = "VVR")
    private List<VVR> lstVVR;    

    public VVRs() {
	lstVVR = new ArrayList<>();
    }

    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public static VVRs load(String fileName) {
	return (VVRs) deserialize(fileName, VVRs.class);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the VVR
     */
    public List<VVR> getVVR() {
	return lstVVR;
    }

    public void setVVR(List<VVR> lstVVR) {
	this.lstVVR = lstVVR;
    }

    public void addVVR(VVR VVR) {
	this.lstVVR.add(VVR);
    }
    //</editor-fold>
    
    

}
