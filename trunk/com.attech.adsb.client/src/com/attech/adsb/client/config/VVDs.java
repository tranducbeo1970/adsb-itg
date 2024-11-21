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
@XmlRootElement(name = "VVDs")
@XmlAccessorType(XmlAccessType.NONE)
public class VVDs extends com.attech.adsb.client.common.XmlSerializer {

    @XmlElement(name = "VVD")
    private List<VVD> lstVVD;    

    public VVDs() {
	lstVVD = new ArrayList<>();
    }

    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public static VVDs load(String fileName) {
	return (VVDs) deserialize(fileName, VVDs.class);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the VVD
     */
    public List<VVD> getVVD() {
	return lstVVD;
    }

    public void setVVD(List<VVD> lstVVD) {
	this.lstVVD = lstVVD;
    }

    public void addVVD(VVD VVD) {
	this.lstVVD.add(VVD);
    }
    //</editor-fold>
        

}
