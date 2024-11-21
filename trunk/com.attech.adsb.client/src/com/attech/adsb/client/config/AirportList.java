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
 * @author andh
 */
@XmlRootElement(name = "Airports")
@XmlAccessorType(XmlAccessType.NONE)
public class AirportList extends com.attech.adsb.client.common.XmlSerializer{

    @XmlElement(name = "Airport")
    private List<AirportItem> airportList;

    public AirportList() {
	airportList = new ArrayList<>();
    }

    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public static AirportList load(String fileName) {
	return load(fileName, AirportList.class);
    }
    
    public void addAirport(AirportItem airportItem) {
	this.airportList.add(airportItem);
    }
    
    public List<AirportItem> getAirportList() {
	return airportList;
    }
    
    public AirportItem getAirportItem (String key) {
        for (AirportItem airportitem : this.airportList) {
            if (airportitem.getName().equalsIgnoreCase(key)) {
                return airportitem;
            }
        }
        return null;
    } 
    
    public String getResourcePath(String key) {
        for (AirportItem airportitem : this.airportList) {
            if (airportitem.getName().equalsIgnoreCase(key)) {
                return airportitem.getDataPath();
            }
        }
        return "none";
    }

    public void setAirportList(List<AirportItem> airportList) {
	this.airportList = airportList;
    }

    public static void main(String [] args) {
	AirportList airportList = new AirportList();
	airportList.addAirport(new AirportItem("CAM RANH", true, "CRA"));
	airportList.addAirport(new AirportItem("DA NANG", true, "DNA"));
	airportList.addAirport(new AirportItem("TAN SON NHAT", true, "TNS"));
	airportList.addAirport(new AirportItem("NOI BAI", true, "NBA"));
	airportList.save("airports.xml");
	System.out.println("Completed");
    }
    
    
}
