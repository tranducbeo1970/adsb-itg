/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import com.attech.asd.database.SensorsDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author anhth
 */
@Entity
@Table(name = "sensors")
public class Sensors implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    protected Integer id;
    
    @Column(name = "SensorMode")
    protected Byte sensorMode;
    
    @Column(name = "Sic")
    protected Integer sic;
    
    @Column(name = "Latitude")
    protected Float latitude;
    
    @Column(name = "Longitude")
    protected Float longitude;
    
    @ManyToOne
    @JoinColumn(name = "StationId")
    protected Stations station;
    
    @Column(name = "Status")
    protected Byte status;
    
    @Column(name = "Description")
    protected String description;
    
    @Column(name = "ReceivingMode")
    protected String ReceivingMode;
    
    @Column(name = "ReceivingMulticastAddress")
    protected String ReceivingMulticastAddress;
    
    @Column(name = "ReceivingPort")
    protected Integer ReceivingPort;
    
    @Column(name = "ReceivingBindIp")
    protected String ReceivingBindIp;
    
    @Column(name = "BufferSize")
    private Integer BufferSize;
    
    @Transient
    private boolean enable;
    
    public Sensors(){
        this.station = new Stations();
    }
    
    public Sensors(Stations station){
        this();
        setStation(station);
    }
    
    public Sensors(int sensormode, int sic, float lat, float lon, int status, String desc, Stations station){
        this.sensorMode = (byte) sensormode;
        this.sic = sic;
        this.latitude = lat;
        this.longitude = lon;
        this.status = (byte) status;
        this.description = desc;
        this.station = station;
    }
    
    protected Sensors(Sensors s){
        this();
        this.id = s.getId();
        this.sensorMode = s.getSensorMode();
        this.sic = s.getSic();
        this.latitude = s.getLatitude();
        this.longitude = s.getLongitude();
        this.station = s.getStation();
        this.status = s.getStatus();
        this.description = s.getDescription();
        this.ReceivingBindIp = s.getReceivingBindIp();
        this.ReceivingMode = s.getReceivingMode();
        this.ReceivingMulticastAddress = s.getReceivingMulticastAddress();
        this.ReceivingPort = s.getReceivingPort();
        
        /*
        this.ForwardAddress = s.getForwardAddress();
        this.ForwardBindIp = s.getForwardBindIp();
        this.ForwardMode = s.getForwardMode();
        this.ForwardPort = s.getForwardPort();
        this.ForwardingMultiCastTTL = s.getForwardingMultiCastTTL();
        this.EnableForwarding = s.getEnableForwarding();
        */
        this.BufferSize = s.getBufferSize();
    }
    
    public int save(){
        try{
            new SensorsDao().save(this);
            return 1;
        }
        catch (Exception ex){
            return 0;
        }
    }
    
    public int updateStatus(){
        try{
            new SensorsDao().updateStatus(this.getSic(), this.getStatus());
            return 1;
        }
        catch (Exception ex){
            return 0;
        }
    }
    /*
    public void add(List<Flights> list){
        for (Flights f: list){
            f.setSensorId(this.id);
            this.getFlights().add(f);
        }
    }
    
    public void add(Flights f){
        f.setSensorId(this.id);
        this.getFlights().add(f);
    }
    
    private Flights getFlight(String address){
        final Flights flight = new Flights(address);
        final int index = this.getFlights().indexOf(flight);
        if (index >= 0) {
            return this.getFlights().get(index);
        }
        this.getFlights().add(flight);
        return flight;
    }
    */
    
    @Override
    public String toString(){
        //return String.format("Sensor Mode: %s, Sic: %s, %s (flights)", this.getSensorMode(), getSic(), (this.flights==null) ? 0 :this.flights.size());
        //return String.format("Sensor Mode: %s, Sic: %s", this.getSensorMode(), getSic());
        if (this == null)
            return "";
        String s = "";
        for (Field field: this.getClass().getDeclaredFields()){
            try {
                s = s + String.format("%s: %s | ", field.getName(), (field.get(this) != null) ? field.get(this) : "");
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                return s;
            }
        }
        return s;
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
     * @return the SensorMode
     */
    public Byte getSensorMode() {
        return sensorMode;
    }

    /**
     * @param SensorMode the SensorMode to set
     */
    public void setSensorMode(Byte SensorMode) {
        this.sensorMode = SensorMode;
    }

    /**
     * @return the Sic
     */
    public Integer getSic() {
        return sic;
    }

    /**
     * @param Sic the Sic to set
     */
    public void setSic(Integer Sic) {
        this.sic = Sic;
    }

    /**
     * @return the Latitude
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @param Latitude the Latitude to set
     */
    public void setLatitude(Float Latitude) {
        this.latitude = Latitude;
    }

    /**
     * @return the Longitude
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * @param Longitude the Longitude to set
     */
    public void setLongitude(Float Longitude) {
        this.longitude = Longitude;
    }


    /**
     * @return the Status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(Byte Status) {
        this.status = Status;
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

    /**
     * @return the ReceivingMode
     */
    public String getReceivingMode() {
        return ReceivingMode;
    }

    /**
     * @param ReceivingMode the ReceivingMode to set
     */
    public void setReceivingMode(String ReceivingMode) {
        this.ReceivingMode = ReceivingMode;
    }

    /**
     * @return the ReceivingMulticastAddress
     */
    public String getReceivingMulticastAddress() {
        return ReceivingMulticastAddress;
    }

    /**
     * @param ReceivingMulticastAddress the ReceivingMulticastAddress to set
     */
    public void setReceivingMulticastAddress(String ReceivingMulticastAddress) {
        this.ReceivingMulticastAddress = ReceivingMulticastAddress;
    }

    /**
     * @return the ReceivingPort
     */
    public Integer getReceivingPort() {
        return ReceivingPort;
    }

    /**
     * @param ReceivingPort the ReceivingPort to set
     */
    public void setReceivingPort(Integer ReceivingPort) {
        this.ReceivingPort = ReceivingPort;
    }

    /**
     * @return the ReceivingBindIp
     */
    public String getReceivingBindIp() {
        return ReceivingBindIp;
    }

    /**
     * @param ReceivingBindIp the ReceivingBindIp to set
     */
    public void setReceivingBindIp(String ReceivingBindIp) {
        this.ReceivingBindIp = ReceivingBindIp;
    }
    
    /**
     * @return the BufferSize
     */
    public Integer getBufferSize() {
        return BufferSize;
    }

    /**
     * @param BufferSize the BufferSize to set
     */
    public void setBufferSize(Integer BufferSize) {
        this.BufferSize = BufferSize;
    }

    /**
     * @return the station
     */
    public Stations getStation() {
        return station;
    }

    /**
     * @param station the station to set
     */
    public void setStation(Stations station) {
        this.station = station;
    }
    
    /**
     * @return the flights
     */
    /*
    public List<Flights> getFlights() {
        return flights;
    }
    */
    /**
     * @param flights the flights to set
     */
    /*
    public void setFlights(List<Flights> flights) {
        this.flights = flights;
    }
    */
    /**
     * @return the enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
}
