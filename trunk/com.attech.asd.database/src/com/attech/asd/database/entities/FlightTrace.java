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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 *
 * @author NhuongND
 */

@Entity
@Table(name = "flighttrace")
public class FlightTrace{
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;
    @ManyToOne ()
    @JoinColumn(name = "FlightId")
    private Flights flight;
    @Column(name = "No")
    private Integer no;
    @Column(name = "Sic")
    private Integer sic;
    @Column(name = "Callsign")
    private String callsign;
    @Column(name = "FlightLevel")
    private Float flightLevel;
    @Column(name = "Latitude")
    private Double latitude;
    @Column(name = "Longitude")
    private Double longitude;
    @Column(name = "Nic")
    private Integer nic;
    @Column(name = "Nac")
    private Integer nac;
    @Column(name = "Sil")
    private Integer sil;
    @Column(name = "FlightLevelAge")
    private Double flightLevelAge;
    @Column(name = "PTime")
    private Double pTime;
    @Column(name = "Amplitude")
    private Integer amplitude;
    @Column(name = "GroundSpeed")
    private Double groundSpeed;
    @Column(name = "TrackNo")
    private Integer trackNo;
    @Column(name = "Code3A")
    private Integer code3A;
    @Column(name = "Rho")
    private Double rho;
    @Column(name = "Theta")
    private Double theta;
    @Column(name = "RTime")
    private long rTime;
    
    
    
    public FlightTrace(){
    }
        
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the flight
     */
    public Flights getFlight() {
        return flight;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlight(Flights flight) {
        this.flight = flight;
    }
    
     /**
     * @return the no
     */
    public Integer getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(Integer no) {
        this.no = no;
    }
    
    /**
     * @return the sic
     */
    public Integer getSic() {
        return sic;
    }

    /**
     * @param sic the sic to set
     */
    public void setSic(Integer sic) {
        this.sic = sic;
    }

    /**
     * @return the Callsign
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * @param Callsign the Callsign to set
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }
    
    /**
     * @return the FlightLevel
     */
    public Float getFlightLevel() {
        return flightLevel;
    }

    /**
     * @param FlightLevel the FlightLevel to set
     */
    public void setLevel(Float FlightLevel) {
        this.flightLevel = FlightLevel;
    }
    
    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }      

    /**
     * @return the nic
     */
    public Integer getNic() {
        return nic;
    }

    /**
     * @param nic the nic to set
     */
    public void setNic(Integer nic) {
        this.nic = nic;
    }

    /**
     * @return the nac
     */
    public Integer getNac() {
        return nac;
    }

    /**
     * @param nac the nac to set
     */
    public void setNac(Integer nac) {
        this.nac = nac;
    }

    /**
     * @return the sil
     */
    public Integer getSil() {
        return sil;
    }

    /**
     * @param sil the sil to set
     */
    public void setSil(Integer sil) {
        this.sil = sil;
    }

    /**
     * @return the flightLevelAge
     */
    public Double getFlightLevelAge() {
        return flightLevelAge;
    }

    /**
     * @param flightLevelAge the flightLevelAge to set
     */
    public void setFlightLevelAge(Double flightLevelAge) {
        this.flightLevelAge = flightLevelAge;
    }

    /**
     * @return the pTime
     */
    public Double getPTime() {
        return pTime;
    }

    /**
     * @param pTime the pTime to set
     */
    public void setPositionTime(Double pTime) {
        this.pTime = pTime;
    }
 
    /**
     * @return the amplitude
     */
    public Integer getAmplitude() {
        return amplitude;
    }

    /**
     * @param amplitude the amplitude to set
     */
    public void setAmplitude(Integer amplitude) {
        this.amplitude = amplitude;
    }   
    
    /**
     * @return the groundSpeed
     */
    public Double getGroundSpeed() {
        return groundSpeed;
    }

    /**
     * @param groundSpeed the groundSpeed to set
     */
    public void setGroundSpeed(Double groundSpeed) {
        this.groundSpeed = groundSpeed;
    }

    /**
     * @return the trackNo
     */
    public Integer getTrackNo() {
        return trackNo;
    }

    /**
     * @param trackNo the trackNo to set
     */
    public void setTrackNo(Integer trackNo) {
        this.trackNo = trackNo;
    }

    /**
     * @return the code3A
     */
    public Integer getCode3A() {
        return code3A;
    }

    /**
     * @param code3A the code3A to set
     */
    public void setCode3A(Integer code3A) {
        this.code3A = code3A;
    }

    /**
     * @return the rho
     */
    public Double getRho() {
        return rho;
    }

    /**
     * @param rho the rho to set
     */
    public void setRho(Double rho) {
        this.rho = rho;
    }

    /**
     * @return the theta
     */
    public Double getTheta() {
        return theta;
    }

    /**
     * @param theta the theta to set
     */
    public void setTheta(Double theta) {
        this.theta = theta;
    }

    /**
     * @return the rTime
     */
    public long getRTime() {
        return rTime;
    }

    /**
     * @param rTime the rTime to set
     */
    public void setReceivedTime(long rTime) {
        this.rTime = rTime;
    }

}
