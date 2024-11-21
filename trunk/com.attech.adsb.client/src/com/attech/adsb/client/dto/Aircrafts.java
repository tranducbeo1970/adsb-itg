/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "aircrafts")
@XmlAccessorType(XmlAccessType.NONE)
public class Aircrafts implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "id")
    private Integer id;
    
    @XmlElement(name = "icao24Address")
    private String icao24Address;
    
    @XmlElement(name = "registrationNo")
    private String registrationNo;
    
    @XmlElement(name = "model")
    private String model;
    
    @XmlElement(name = "aircraftType")
    private String aircraftType;
    
    @XmlElement(name = "operator")
    private String operator;

    public Aircrafts() {
    }

    public Aircrafts(Integer id) {
	this.id = id;
    }

    public Aircrafts(Integer id, String icao24Address, String registrationNo) {
	this.id = id;
	this.icao24Address = icao24Address;
	this.registrationNo = registrationNo;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getIcao24Address() {
	return icao24Address;
    }

    public void setIcao24Address(String icao24Address) {
	this.icao24Address = icao24Address;
    }

    public String getRegistrationNo() {
	return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
	this.registrationNo = registrationNo;
    }

    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    public String getAircraftType() {
	return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
	this.aircraftType = aircraftType;
    }

    public String getOperator() {
	return operator;
    }

    public void setOperator(String operator) {
	this.operator = operator;
    }


    @Override
    public int hashCode() {
	int hash = 0;
	hash += (id != null ? id.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
 
        
	if (!(object instanceof Aircrafts)) {
	    return false;
	}
        
	Aircrafts other = (Aircrafts) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "entities.Aircrafts[ id=" + id + " ]";
    }
    
}
