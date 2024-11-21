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
@Table(name = "flights")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flights.findAll", query = "SELECT f FROM Flights f"),
    @NamedQuery(name = "Flights.findById", query = "SELECT f FROM Flights f WHERE f.id = :id"),
    @NamedQuery(name = "Flights.findByIcao24Address", query = "SELECT f FROM Flights f WHERE f.icao24Address = :icao24Address"),
    @NamedQuery(name = "Flights.findByCallsign", query = "SELECT f FROM Flights f WHERE f.callsign = :callsign"),
    @NamedQuery(name = "Flights.findByFromtime", query = "SELECT f FROM Flights f WHERE f.fromtime = :fromtime"),
    @NamedQuery(name = "Flights.findByTotime", query = "SELECT f FROM Flights f WHERE f.totime = :totime"),
    @NamedQuery(name = "Flights.findBySensorId", query = "SELECT f FROM Flights f WHERE f.sensorId = :sensorId"),
    @NamedQuery(name = "Flights.findByTotalMessage", query = "SELECT f FROM Flights f WHERE f.totalMessage = :totalMessage"),
    @NamedQuery(name = "Flights.findByTotalFrame", query = "SELECT f FROM Flights f WHERE f.totalFrame = :totalFrame"),
    @NamedQuery(name = "Flights.findByTrackNo", query = "SELECT f FROM Flights f WHERE f.trackNo = :trackNo"),
    @NamedQuery(name = "Flights.findByCode3A", query = "SELECT f FROM Flights f WHERE f.code3A = :code3A")})
public class Flights implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Icao24Address")
    private String icao24Address;
    @Basic(optional = false)
    @Column(name = "Callsign")
    private String callsign;
    @Basic(optional = false)
    @Column(name = "Fromtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromtime;
    @Basic(optional = false)
    @Column(name = "Totime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date totime;
    @Basic(optional = false)
    @Column(name = "SensorId")
    private int sensorId;
    @Basic(optional = false)
    @Column(name = "TotalMessage")
    private int totalMessage;
    @Basic(optional = false)
    @Column(name = "TotalFrame")
    private int totalFrame;
    @Basic(optional = false)
    @Column(name = "TrackNo")
    private int trackNo;
    @Basic(optional = false)
    @Column(name = "Code3A")
    private String code3A;

    public Flights() {
    }

    public Flights(Integer id) {
        this.id = id;
    }

    public Flights(Integer id, String icao24Address, String callsign, Date fromtime, Date totime, int sensorId, int totalMessage, int totalFrame, int trackNo, String code3A) {
        this.id = id;
        this.icao24Address = icao24Address;
        this.callsign = callsign;
        this.fromtime = fromtime;
        this.totime = totime;
        this.sensorId = sensorId;
        this.totalMessage = totalMessage;
        this.totalFrame = totalFrame;
        this.trackNo = trackNo;
        this.code3A = code3A;
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

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public Date getFromtime() {
        return fromtime;
    }

    public void setFromtime(Date fromtime) {
        this.fromtime = fromtime;
    }

    public Date getTotime() {
        return totime;
    }

    public void setTotime(Date totime) {
        this.totime = totime;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getTotalMessage() {
        return totalMessage;
    }

    public void setTotalMessage(int totalMessage) {
        this.totalMessage = totalMessage;
    }

    public int getTotalFrame() {
        return totalFrame;
    }

    public void setTotalFrame(int totalFrame) {
        this.totalFrame = totalFrame;
    }

    public int getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(int trackNo) {
        this.trackNo = trackNo;
    }

    public String getCode3A() {
        return code3A;
    }

    public void setCode3A(String code3A) {
        this.code3A = code3A;
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
        if (!(object instanceof Flights)) {
            return false;
        }
        Flights other = (Flights) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Flights[ id=" + id + " ]";
    }
    
}
