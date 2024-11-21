/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import com.attech.asd.database.AdapterObject;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author AnhTH
 */
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    protected Integer id;
    @Column(name = "Name")
    protected String name;
    @Column(name = "Latitude")
    protected Double latitude;
    @Column(name = "Longitude")
    protected Double longitude;
    @Column(name = "Description")
    protected String description;
    /*
    @JoinColumn(name = "CurrentStatus")
    private Integer currentStatus;
    */
    @Column(name = "ForwardMode")
    protected String forwardMode;
    @Column(name = "ForwardAddress")
    protected String forwardAddress;
    @Column(name = "ForwardPort")
    protected Integer forwardPort;
    @Column(name = "ForwardBindIp")
    protected String forwardBindIp;
    @Column(name = "ForwardingMultiCastTTL")
    protected Integer forwardingMultiCastTTL;
    @Column(name = "BufferSize")
    protected Integer bufferSize;
    
    @Column(name = "HeightMin")
    private Float heightMin;
    
    @Column(name = "HeightMax")
    private Float heightMax;
    
    @Column(name = "IsForwarding")
    private boolean forwarding;
    
    @Column(name = "SicFwd")
    private int sicFwd;
    
    @Column(name = "IdSensorFwd")
    private int idSensorFwd;
    
    @Column(name = "Status")
    protected Byte status;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "clientarea", 
        joinColumns = { @JoinColumn(name = "clientid",nullable = false, updatable = false) }, 
        inverseJoinColumns = { @JoinColumn(name = "areaid",nullable = false, updatable = false) }
    )
    private Set<Areas> areas = new HashSet<>();
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "clientcircular", 
        joinColumns = { @JoinColumn(name = "clientid",nullable = false, updatable = false) }, 
        inverseJoinColumns = { @JoinColumn(name = "circularid",nullable = false, updatable = false) }
    )
    private Set<Circulars> circulars = new HashSet<>();
    

    public Client() {
    }
    
    public boolean isHeightValidate(){
        if (heightMin == 0f && heightMax == 0f)
            return false;
        if (heightMax < heightMin)
            return false;
        if (heightMax < 0)
            return false;
        return true;
    }
    
    public boolean isAreaValidate(){
        if (areas == null || areas.isEmpty())
            return false;
        return true;
    }
    
    public boolean isCircularValidate(){
        if (circulars == null || circulars.isEmpty())
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("Id: %d | Name: %s | Coordinate: %s,%s | FwdMode: %s | FwdAddress: %s | FwdPort: %s | FwdBindIP: %s | FLMin: %s | FLMax: %s | Areas: %s | Circulars: %s", 
                id, name, latitude, longitude, forwardMode, forwardAddress, forwardPort, forwardBindIp, heightMin, heightMax, areas.size(), circulars.size());
    }
    
    public boolean save(){
        return new AdapterObject().saveClient(this);
    }
    
    public boolean addArea(Areas area){
        for (Areas a: areas){
            if (area.getId() - a.getId() == 0){
                return false;
            }
        }
        areas.add(area);
        return true;
    }
    
    public void removeArea(int areaId){
        for (Areas area: areas){
            if (area.getId() - areaId == 0){
                areas.remove(area);
                break;
            }
        }
    }
    
    public boolean addCircular(Circulars area){
        circulars.add(area);
        return true;
    }
    
    public void removeCircular(int areaId){
        for (Circulars area: circulars){
            if (area.getId() - areaId == 0){
                circulars.remove(area);
                break;
            }
        }
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return the ForwardMode
     */
    public String getForwardMode() {
        return forwardMode;
    }

    /**
     * @param ForwardMode the ForwardMode to set
     */
    public void setForwardMode(String ForwardMode) {
        this.forwardMode = ForwardMode;
    }

    /**
     * @return the ForwardAddress
     */
    public String getForwardAddress() {
        return forwardAddress;
    }

    /**
     * @param ForwardAddress the ForwardAddress to set
     */
    public void setForwardAddress(String ForwardAddress) {
        this.forwardAddress = ForwardAddress;
    }
  
    /**
     * @return the ForwardPort
     */
    public Integer getForwardPort() {
        return forwardPort;
    }

    /**
     * @param ForwardPort the ForwardPort to set
     */
    public void setForwardPort(Integer ForwardPort) {
        this.forwardPort = ForwardPort;
    }

    /**
     * @return the ForwardBindIp
     */
    public String getForwardBindIp() {
        return forwardBindIp;
    }

    /**
     * @param ForwardBindIp the ForwardBindIp to set
     */
    public void setForwardBindIp(String ForwardBindIp) {
        this.forwardBindIp = ForwardBindIp;
    }
    
    /**
     * @return the ForwardingMultiCastTTL
     */
    public Integer getForwardingMultiCastTTL() {
        return forwardingMultiCastTTL;
    }

    /**
     * @param ForwardingMultiCastTTL the ForwardingMultiCastTTL to set
     */
    public void setForwardingMultiCastTTL(Integer ForwardingMultiCastTTL) {
        this.forwardingMultiCastTTL = ForwardingMultiCastTTL;
    }
    
    /**
     * @return the BufferSize
     */
    public Integer getBufferSize() {
        return bufferSize;
    }

    /**
     * @param BufferSize the BufferSize to set
     */
    public void setBufferSize(Integer BufferSize) {
        this.bufferSize = BufferSize;
    }

    /**
     * @return the heightMin
     */
    public Float getHeightMin() {
        return heightMin;
    }

    /**
     * @param heightMin the heightMin to set
     */
    public void setHeightMin(Float heightMin) {
        this.heightMin = heightMin;
    }

    /**
     * @return the heightMax
     */
    public Float getHeightMax() {
        return heightMax;
    }

    /**
     * @param heightMax the heightMax to set
     */
    public void setHeightMax(Float heightMax) {
        this.heightMax = heightMax;
    }

    /**
     * @return the areas
     */
    public Set<Areas> getAreas() {
        return areas;
    }

    /**
     * @param areas the areas to set
     */
    public void setAreas(Set<Areas> areas) {
        this.areas = areas;
    }

    /**
     * @return the circulars
     */
    public Set<Circulars> getCirculars() {
        return circulars;
    }

    /**
     * @param circulars the circulars to set
     */
    public void setCirculars(Set<Circulars> circulars) {
        this.circulars = circulars;
    }

    /**
     * @return the forwarding
     */
    public boolean isForwarding() {
        return forwarding;
    }

    /**
     * @param forwarding the forwarding to set
     */
    public void setForwarding(boolean forwarding) {
        this.forwarding = forwarding;
    }

    /**
     * @return the sensorIdFwd
     */
    public int getSicFwd() {
        return sicFwd;
    }

    /**
     * @param sensorIdFwd the sensorIdFwd to set
     */
    public void setSicFwd(int sic) {
        this.sicFwd = sic;
    }

    /**
     * @return the idSensorFwd
     */
    public int getIdSensorFwd() {
        return idSensorFwd;
    }

    /**
     * @param idSensorFwd the idSensorFwd to set
     */
    public void setIdSensorFwd(int idSensorFwd) {
        this.idSensorFwd = idSensorFwd;
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
}
