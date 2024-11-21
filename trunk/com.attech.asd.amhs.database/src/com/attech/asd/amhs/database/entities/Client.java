/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.database.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@Entity
@Table(name = "client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id"),
    @NamedQuery(name = "Client.findByName", query = "SELECT c FROM Client c WHERE c.name = :name"),
    @NamedQuery(name = "Client.findByLatitude", query = "SELECT c FROM Client c WHERE c.latitude = :latitude"),
    @NamedQuery(name = "Client.findByLongitude", query = "SELECT c FROM Client c WHERE c.longitude = :longitude"),
    @NamedQuery(name = "Client.findByDescription", query = "SELECT c FROM Client c WHERE c.description = :description"),
    @NamedQuery(name = "Client.findByForwardMode", query = "SELECT c FROM Client c WHERE c.forwardMode = :forwardMode"),
    @NamedQuery(name = "Client.findByForwardAddress", query = "SELECT c FROM Client c WHERE c.forwardAddress = :forwardAddress"),
    @NamedQuery(name = "Client.findByForwardPort", query = "SELECT c FROM Client c WHERE c.forwardPort = :forwardPort"),
    @NamedQuery(name = "Client.findByForwardBindIp", query = "SELECT c FROM Client c WHERE c.forwardBindIp = :forwardBindIp"),
    @NamedQuery(name = "Client.findByForwardingMultiCastTTL", query = "SELECT c FROM Client c WHERE c.forwardingMultiCastTTL = :forwardingMultiCastTTL"),
    @NamedQuery(name = "Client.findByBufferSize", query = "SELECT c FROM Client c WHERE c.bufferSize = :bufferSize"),
    @NamedQuery(name = "Client.findByHeightMin", query = "SELECT c FROM Client c WHERE c.heightMin = :heightMin"),
    @NamedQuery(name = "Client.findByHeightMax", query = "SELECT c FROM Client c WHERE c.heightMax = :heightMax"),
    @NamedQuery(name = "Client.findByIsForwarding", query = "SELECT c FROM Client c WHERE c.isForwarding = :isForwarding"),
    @NamedQuery(name = "Client.findBySicFwd", query = "SELECT c FROM Client c WHERE c.sicFwd = :sicFwd")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Latitude")
    private float latitude;
    @Basic(optional = false)
    @Column(name = "Longitude")
    private float longitude;
    @Column(name = "Description")
    private String description;
    @Basic(optional = false)
    @Column(name = "ForwardMode")
    private String forwardMode;
    @Basic(optional = false)
    @Column(name = "ForwardAddress")
    private String forwardAddress;
    @Basic(optional = false)
    @Column(name = "ForwardPort")
    private int forwardPort;
    @Basic(optional = false)
    @Column(name = "ForwardBindIp")
    private String forwardBindIp;
    @Basic(optional = false)
    @Column(name = "ForwardingMultiCastTTL")
    private int forwardingMultiCastTTL;
    @Basic(optional = false)
    @Column(name = "BufferSize")
    private int bufferSize;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "HeightMin")
    private Float heightMin;
    @Column(name = "HeightMax")
    private Float heightMax;
    @Column(name = "IsForwarding")
    private Boolean isForwarding;
    @Column(name = "SicFwd")
    private Integer sicFwd;

    public Client() {
    }

    public Client(Integer id) {
        this.id = id;
    }

    public Client(Integer id, String name, float latitude, float longitude, String forwardMode, String forwardAddress, int forwardPort, String forwardBindIp, int forwardingMultiCastTTL, int bufferSize) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.forwardMode = forwardMode;
        this.forwardAddress = forwardAddress;
        this.forwardPort = forwardPort;
        this.forwardBindIp = forwardBindIp;
        this.forwardingMultiCastTTL = forwardingMultiCastTTL;
        this.bufferSize = bufferSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getForwardMode() {
        return forwardMode;
    }

    public void setForwardMode(String forwardMode) {
        this.forwardMode = forwardMode;
    }

    public String getForwardAddress() {
        return forwardAddress;
    }

    public void setForwardAddress(String forwardAddress) {
        this.forwardAddress = forwardAddress;
    }

    public int getForwardPort() {
        return forwardPort;
    }

    public void setForwardPort(int forwardPort) {
        this.forwardPort = forwardPort;
    }

    public String getForwardBindIp() {
        return forwardBindIp;
    }

    public void setForwardBindIp(String forwardBindIp) {
        this.forwardBindIp = forwardBindIp;
    }

    public int getForwardingMultiCastTTL() {
        return forwardingMultiCastTTL;
    }

    public void setForwardingMultiCastTTL(int forwardingMultiCastTTL) {
        this.forwardingMultiCastTTL = forwardingMultiCastTTL;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public Float getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(Float heightMin) {
        this.heightMin = heightMin;
    }

    public Float getHeightMax() {
        return heightMax;
    }

    public void setHeightMax(Float heightMax) {
        this.heightMax = heightMax;
    }

    public Boolean getIsForwarding() {
        return isForwarding;
    }

    public void setIsForwarding(Boolean isForwarding) {
        this.isForwarding = isForwarding;
    }

    public Integer getSicFwd() {
        return sicFwd;
    }

    public void setSicFwd(Integer sicFwd) {
        this.sicFwd = sicFwd;
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
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Client[ id=" + id + " ]";
    }
    
}
