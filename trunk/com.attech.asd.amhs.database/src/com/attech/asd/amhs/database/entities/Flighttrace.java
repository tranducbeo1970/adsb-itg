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
@Table(name = "flighttrace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flighttrace.findAll", query = "SELECT f FROM Flighttrace f"),
    @NamedQuery(name = "Flighttrace.findById", query = "SELECT f FROM Flighttrace f WHERE f.id = :id"),
    @NamedQuery(name = "Flighttrace.findByFlightId", query = "SELECT f FROM Flighttrace f WHERE f.flightId = :flightId"),
    @NamedQuery(name = "Flighttrace.findByNo", query = "SELECT f FROM Flighttrace f WHERE f.no = :no"),
    @NamedQuery(name = "Flighttrace.findBySic", query = "SELECT f FROM Flighttrace f WHERE f.sic = :sic"),
    @NamedQuery(name = "Flighttrace.findByCallsign", query = "SELECT f FROM Flighttrace f WHERE f.callsign = :callsign"),
    @NamedQuery(name = "Flighttrace.findByFlightLevel", query = "SELECT f FROM Flighttrace f WHERE f.flightLevel = :flightLevel"),
    @NamedQuery(name = "Flighttrace.findByLatitude", query = "SELECT f FROM Flighttrace f WHERE f.latitude = :latitude"),
    @NamedQuery(name = "Flighttrace.findByLongitude", query = "SELECT f FROM Flighttrace f WHERE f.longitude = :longitude"),
    @NamedQuery(name = "Flighttrace.findByNic", query = "SELECT f FROM Flighttrace f WHERE f.nic = :nic"),
    @NamedQuery(name = "Flighttrace.findByNac", query = "SELECT f FROM Flighttrace f WHERE f.nac = :nac"),
    @NamedQuery(name = "Flighttrace.findBySil", query = "SELECT f FROM Flighttrace f WHERE f.sil = :sil"),
    @NamedQuery(name = "Flighttrace.findByFlightLevelAge", query = "SELECT f FROM Flighttrace f WHERE f.flightLevelAge = :flightLevelAge"),
    @NamedQuery(name = "Flighttrace.findByPTime", query = "SELECT f FROM Flighttrace f WHERE f.pTime = :pTime"),
    @NamedQuery(name = "Flighttrace.findByAmplitude", query = "SELECT f FROM Flighttrace f WHERE f.amplitude = :amplitude"),
    @NamedQuery(name = "Flighttrace.findByGroundSpeed", query = "SELECT f FROM Flighttrace f WHERE f.groundSpeed = :groundSpeed"),
    @NamedQuery(name = "Flighttrace.findByTrackNo", query = "SELECT f FROM Flighttrace f WHERE f.trackNo = :trackNo"),
    @NamedQuery(name = "Flighttrace.findByCode3A", query = "SELECT f FROM Flighttrace f WHERE f.code3A = :code3A"),
    @NamedQuery(name = "Flighttrace.findByRho", query = "SELECT f FROM Flighttrace f WHERE f.rho = :rho"),
    @NamedQuery(name = "Flighttrace.findByTheta", query = "SELECT f FROM Flighttrace f WHERE f.theta = :theta"),
    @NamedQuery(name = "Flighttrace.findByRTime", query = "SELECT f FROM Flighttrace f WHERE f.rTime = :rTime")})
public class Flighttrace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FlightId")
    private int flightId;
    @Basic(optional = false)
    @Column(name = "No")
    private int no;
    @Basic(optional = false)
    @Column(name = "Sic")
    private String sic;
    @Basic(optional = false)
    @Column(name = "Callsign")
    private String callsign;
    @Basic(optional = false)
    @Column(name = "FlightLevel")
    private float flightLevel;
    @Basic(optional = false)
    @Column(name = "Latitude")
    private float latitude;
    @Basic(optional = false)
    @Column(name = "Longitude")
    private float longitude;
    @Basic(optional = false)
    @Column(name = "NIC")
    private int nic;
    @Basic(optional = false)
    @Column(name = "NAC")
    private int nac;
    @Basic(optional = false)
    @Column(name = "SIL")
    private int sil;
    @Basic(optional = false)
    @Column(name = "FlightLevelAge")
    private float flightLevelAge;
    @Basic(optional = false)
    @Column(name = "PTime")
    private float pTime;
    @Basic(optional = false)
    @Column(name = "Amplitude")
    private int amplitude;
    @Basic(optional = false)
    @Column(name = "GroundSpeed")
    private float groundSpeed;
    @Basic(optional = false)
    @Column(name = "TrackNo")
    private int trackNo;
    @Basic(optional = false)
    @Column(name = "Code3A")
    private String code3A;
    @Basic(optional = false)
    @Column(name = "Rho")
    private float rho;
    @Basic(optional = false)
    @Column(name = "Theta")
    private float theta;
    @Basic(optional = false)
    @Column(name = "RTime")
    private long rTime;

    public Flighttrace() {
    }

    public Flighttrace(Integer id) {
        this.id = id;
    }

    public Flighttrace(Integer id, int flightId, int no, String sic, String callsign, float flightLevel, float latitude, float longitude, int nic, int nac, int sil, float flightLevelAge, float pTime, int amplitude, float groundSpeed, int trackNo, String code3A, float rho, float theta, long rTime) {
        this.id = id;
        this.flightId = flightId;
        this.no = no;
        this.sic = sic;
        this.callsign = callsign;
        this.flightLevel = flightLevel;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nic = nic;
        this.nac = nac;
        this.sil = sil;
        this.flightLevelAge = flightLevelAge;
        this.pTime = pTime;
        this.amplitude = amplitude;
        this.groundSpeed = groundSpeed;
        this.trackNo = trackNo;
        this.code3A = code3A;
        this.rho = rho;
        this.theta = theta;
        this.rTime = rTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getSic() {
        return sic;
    }

    public void setSic(String sic) {
        this.sic = sic;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public float getFlightLevel() {
        return flightLevel;
    }

    public void setFlightLevel(float flightLevel) {
        this.flightLevel = flightLevel;
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

    public int getNic() {
        return nic;
    }

    public void setNic(int nic) {
        this.nic = nic;
    }

    public int getNac() {
        return nac;
    }

    public void setNac(int nac) {
        this.nac = nac;
    }

    public int getSil() {
        return sil;
    }

    public void setSil(int sil) {
        this.sil = sil;
    }

    public float getFlightLevelAge() {
        return flightLevelAge;
    }

    public void setFlightLevelAge(float flightLevelAge) {
        this.flightLevelAge = flightLevelAge;
    }

    public float getPTime() {
        return pTime;
    }

    public void setPTime(float pTime) {
        this.pTime = pTime;
    }

    public int getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }

    public float getGroundSpeed() {
        return groundSpeed;
    }

    public void setGroundSpeed(float groundSpeed) {
        this.groundSpeed = groundSpeed;
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

    public float getRho() {
        return rho;
    }

    public void setRho(float rho) {
        this.rho = rho;
    }

    public float getTheta() {
        return theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public long getRTime() {
        return rTime;
    }

    public void setRTime(long rTime) {
        this.rTime = rTime;
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
        if (!(object instanceof Flighttrace)) {
            return false;
        }
        Flighttrace other = (Flighttrace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Flighttrace[ id=" + id + " ]";
    }
    
}
