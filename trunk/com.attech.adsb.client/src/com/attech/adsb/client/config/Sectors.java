/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.XmlSerializer;
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
@XmlRootElement(name = "Sectors")
@XmlAccessorType(XmlAccessType.NONE)
public class Sectors extends com.attech.adsb.client.common.XmlSerializer {

    @XmlElement(name = "Sector")
    private List<Sector> sectors;    

    public Sectors() {
	sectors = new ArrayList<>();
    }

    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public static Sectors load(String fileName) {
	return (Sectors) deserialize(fileName, Sectors.class);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the sectors
     */
    public List<Sector> getSectors() {
	return sectors;
    }

    public void setSectors(List<Sector> sectors) {
	this.sectors = sectors;
    }

    public void addSector(Sector sector) {
	this.sectors.add(sector);
    }
    //</editor-fold>
    
    public static void main(String[] args) {
        Sectors st = XmlSerializer.load(Common.RES_SECTORS, Sectors.class);
	System.out.println("Load completed");
	System.out.println("Sector number: " + st.getSectors().size());
    }

}
