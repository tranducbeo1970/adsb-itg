/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author NhuongND
 */
@Entity
@Table(name = "airports")
public class Airports {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Longitude")
    private Double longitude;
    @Column(name = "Latitude")
    private Double latitude;
    @Column(name = "Type")
    private String type;
    @Column(name = "Iata")
    private String iata;
    @Column(name = "Icao")
    private String icao;    
    @Column(name = "Address")
    private String address;    
    @Column(name = "Description")
    private String description;
    
    public Airports(){
    
    }
    
    public Airports(String type, String iata, String icao, String name,  Double lat, Double lon){
        this();
        this.type = type;
        this.iata = iata;
        this.icao = icao;
        this.name = name;
        this.longitude = lon;
        this.latitude = lat;
    }
    
    @Override
    public String toString(){
        return String.format("NAME: %s | ICAO: %s | IATA: %s | TYPE: %s | Coordinate: %s,%s", name, icao, iata, type, latitude, longitude);
    }
    
  
    /**
     * @return the Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(Integer Id) {
        this.id = Id;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.name = Name;
    }

    /**
     * @return the Longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param Longitude the Longitude to set
     */
    public void setLongitude(Double Longitude) {
        this.longitude = Longitude;
    }
    
     /**
     * @return the Latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param Latitude the Latitude to set
     */
    public void setLatitude(Double Latitude) {
        this.latitude = Latitude;
    }

    /**
     * @return the Type
     */
    public String getType() {
        return type;
    }

    /**
     * @param Type the Type to set
     */
    public void setType(String Type) {
        this.type = Type;
    }

    /**
     * @return the Iata
     */
    public String getIata() {
        return iata;
    }

    /**
     * @param Iata the Iata to set
     */
    public void setIata(String Iata) {
        this.iata = Iata;
    }

    /**
     * @return the Icao
     */
    public String getIcao() {
        return icao;
    }

    /**
     * @param Icao the Icao to set
     */
    public void setIcao(String Icao) {
        this.icao = Icao;
    }
    
    /**
     * @return the Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param Address the Address to set
     */
    public void setAddress(String Address) {
        this.address = Address;
    }
    
    /**
     * @return the Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.description = Description;
    }
    
}
