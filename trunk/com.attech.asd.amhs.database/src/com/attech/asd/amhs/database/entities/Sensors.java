/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.database.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@Entity
@Table(name = "sensors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sensors.findAll", query = "SELECT s FROM Sensors s"),
    @NamedQuery(name = "Sensors.findById", query = "SELECT s FROM Sensors s WHERE s.id = :id"),
    @NamedQuery(name = "Sensors.findBySensorMode", query = "SELECT s FROM Sensors s WHERE s.sensorMode = :sensorMode"),
    @NamedQuery(name = "Sensors.findBySic", query = "SELECT s FROM Sensors s WHERE s.sic = :sic"),
    @NamedQuery(name = "Sensors.findByLatitude", query = "SELECT s FROM Sensors s WHERE s.latitude = :latitude"),
    @NamedQuery(name = "Sensors.findByLongitude", query = "SELECT s FROM Sensors s WHERE s.longitude = :longitude"),
    @NamedQuery(name = "Sensors.findByStationId", query = "SELECT s FROM Sensors s WHERE s.stationId = :stationId"),
    @NamedQuery(name = "Sensors.findByStatus", query = "SELECT s FROM Sensors s WHERE s.status = :status"),
    @NamedQuery(name = "Sensors.findByDescription", query = "SELECT s FROM Sensors s WHERE s.description = :description"),
    @NamedQuery(name = "Sensors.findByReceivingMode", query = "SELECT s FROM Sensors s WHERE s.receivingMode = :receivingMode"),
    @NamedQuery(name = "Sensors.findByReceivingMulticastAddress", query = "SELECT s FROM Sensors s WHERE s.receivingMulticastAddress = :receivingMulticastAddress"),
    @NamedQuery(name = "Sensors.findByReceivingPort", query = "SELECT s FROM Sensors s WHERE s.receivingPort = :receivingPort"),
    @NamedQuery(name = "Sensors.findByReceivingBindIp", query = "SELECT s FROM Sensors s WHERE s.receivingBindIp = :receivingBindIp"),
    @NamedQuery(name = "Sensors.findByBufferSize", query = "SELECT s FROM Sensors s WHERE s.bufferSize = :bufferSize"),
    @NamedQuery(name = "Sensors.findByStartAnalyzingTime", query = "SELECT s FROM Sensors s WHERE s.startAnalyzingTime = :startAnalyzingTime")})
public class Sensors implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "SensorMode")
    private short sensorMode;
    @Basic(optional = false)
    @Column(name = "Sic")
    private int sic;
    @Basic(optional = false)
    @Column(name = "Latitude")
    private float latitude;
    @Basic(optional = false)
    @Column(name = "Longitude")
    private float longitude;
    @Basic(optional = false)
    @Column(name = "StationId")
    private int stationId;
    @Basic(optional = false)
    @Column(name = "Status")
    private short status;
    @Column(name = "Description")
    private String description;
    @Column(name = "ReceivingMode")
    private String receivingMode;
    @Column(name = "ReceivingMulticastAddress")
    private String receivingMulticastAddress;
    @Basic(optional = false)
    @Column(name = "ReceivingPort")
    private int receivingPort;
    @Column(name = "ReceivingBindIp")
    private String receivingBindIp;
    @Column(name = "BufferSize")
    private Integer bufferSize;
    @Column(name = "StartAnalyzingTime")
    @Temporal(TemporalType.TIME)
    private Date startAnalyzingTime;

    public Sensors() {
    }

    public Sensors(Integer id) {
        this.id = id;
    }

    public Sensors(Integer id, short sensorMode, int sic, float latitude, float longitude, int stationId, short status, int receivingPort) {
        this.id = id;
        this.sensorMode = sensorMode;
        this.sic = sic;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stationId = stationId;
        this.status = status;
        this.receivingPort = receivingPort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getSensorMode() {
        return sensorMode;
    }

    public void setSensorMode(short sensorMode) {
        this.sensorMode = sensorMode;
    }

    public int getSic() {
        return sic;
    }

    public void setSic(int sic) {
        this.sic = sic;
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

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceivingMode() {
        return receivingMode;
    }

    public void setReceivingMode(String receivingMode) {
        this.receivingMode = receivingMode;
    }

    public String getReceivingMulticastAddress() {
        return receivingMulticastAddress;
    }

    public void setReceivingMulticastAddress(String receivingMulticastAddress) {
        this.receivingMulticastAddress = receivingMulticastAddress;
    }

    public int getReceivingPort() {
        return receivingPort;
    }

    public void setReceivingPort(int receivingPort) {
        this.receivingPort = receivingPort;
    }

    public String getReceivingBindIp() {
        return receivingBindIp;
    }

    public void setReceivingBindIp(String receivingBindIp) {
        this.receivingBindIp = receivingBindIp;
    }

    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    public Date getStartAnalyzingTime() {
        return startAnalyzingTime;
    }

    public void setStartAnalyzingTime(Date startAnalyzingTime) {
        this.startAnalyzingTime = startAnalyzingTime;
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
        if (!(object instanceof Sensors)) {
            return false;
        }
        Sensors other = (Sensors) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Sensors[ id=" + id + " ]";
    }
    
}
