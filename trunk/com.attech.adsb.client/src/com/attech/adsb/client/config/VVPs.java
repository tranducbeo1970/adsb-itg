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
@XmlRootElement(name = "VVPs")
@XmlAccessorType(XmlAccessType.NONE)
public class VVPs extends com.attech.adsb.client.common.XmlSerializer {

    @XmlElement(name = "VVP")
    private List<VVP> lstVVP;    

    public VVPs() {
	lstVVP = new ArrayList<>();
    }

    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public static VVPs load(String fileName) {
	return (VVPs) deserialize(fileName, VVPs.class);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the VVP
     */
    public List<VVP> getVVP() {
	return lstVVP;
    }

    public void setVVP(List<VVP> lstVVP) {
	this.lstVVP = lstVVP;
    }

    public void addVVP(VVP VVP) {
	this.lstVVP.add(VVP);
    }
    //</editor-fold>
    
    

}
