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
@XmlRootElement(name = "Airports")
@XmlAccessorType(XmlAccessType.NONE)
public class Airports extends com.attech.adsb.client.common.XmlSerializer {   
    
    @XmlElement(name = "Ref")
    private List<String> ref;
    
    private List<Airport> lstAirport;    

    public Airports() {
	lstAirport = new ArrayList<>();
    }

    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public static Airports load(String fileName) {
	return (Airports) deserialize(fileName, Airports.class);
    }
    
    public void loadAirport() {
	for (String refFile : this.ref) {
	    Airport airport = Airport.load(refFile, Airport.class);
            lstAirport.add(airport);	    
        }
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties "> 
    public List<Airport> getAirport() {
	return lstAirport;
    }

    public void setAirport(List<Airport> lstAirport) {
	this.lstAirport = lstAirport;
    }

    public void addAirport(Airport Airport) {
	this.lstAirport.add(Airport);
    }
    //</editor-fold>
    
    public static void main(String[] args) {
        Airports lstPoint = Airports.load("res/airports.xml", Airports.class);
        lstPoint.loadAirport();
        List<Airport> fix = lstPoint.getAirport();
        for (Airport p : fix) {
            System.out.println("Name: " + p.getName());
        }
    }
}
