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
@Table(name = "aircrafts")
public class Aircrafts {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Icao24Address")
    private String icao24Address;
    @Column(name = "RegistrationNo")
    private String registrationNo;
    @Column(name = "Model")
    private String model;
    @Column(name = "AircraftType")
    private String aircraftType;
    @Column(name = "Operator")
    private String operator;
    
    public Aircrafts(){
    
    }
    
    public Aircrafts(String Icao24Address, String RegistrationNo, String AircraftType){
        this();
        this.aircraftType = AircraftType;
        this.icao24Address = Icao24Address;
        this.registrationNo = RegistrationNo;
    }
    
    @Override
    public String toString(){
        return String.format("Address: %s  |  RegistrationNo: %s  |  Model: %s  |  AircraftType: %s  |  Operator: %s", 
                this.icao24Address,
                this.registrationNo,
                this.model,
                this.aircraftType,
                this.operator
        );
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
     * @return the Icao24Address
     */
    public String getIcao24Address() {
        return icao24Address;
    }

    /**
     * @param Icao24Address the Icao24Address to set
     */
    public void setIcao24Address(String Icao24Address) {
        this.icao24Address = Icao24Address;
    }

    /**
     * @return the RegistrationNo
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * @param RegistrationNo the RegistrationNo to set
     */
    public void setRegistrationNo(String RegistrationNo) {
        this.registrationNo = RegistrationNo;
    }

    /**
     * @return the Model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param Model the Model to set
     */
    public void setModel(String Model) {
        this.model = Model;
    }

    /**
     * @return the AircraftType
     */
    public String getAircraftType() {
        return aircraftType;
    }

    /**
     * @param AircraftType the AircraftType to set
     */
    public void setAircraftType(String AircraftType) {
        this.aircraftType = AircraftType;
    }

    /**
     * @return the Operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param Operator the Operator to set
     */
    public void setOperator(String Operator) {
        this.operator = Operator;
    }   
    
}
