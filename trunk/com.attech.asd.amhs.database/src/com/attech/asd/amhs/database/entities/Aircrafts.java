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
@Table(name = "aircrafts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aircrafts.findAll", query = "SELECT a FROM Aircrafts a"),
    @NamedQuery(name = "Aircrafts.findById", query = "SELECT a FROM Aircrafts a WHERE a.id = :id"),
    @NamedQuery(name = "Aircrafts.findByIcao24Address", query = "SELECT a FROM Aircrafts a WHERE a.icao24Address = :icao24Address"),
    @NamedQuery(name = "Aircrafts.findByRegistrationNo", query = "SELECT a FROM Aircrafts a WHERE a.registrationNo = :registrationNo"),
    @NamedQuery(name = "Aircrafts.findByModel", query = "SELECT a FROM Aircrafts a WHERE a.model = :model"),
    @NamedQuery(name = "Aircrafts.findByAircraftType", query = "SELECT a FROM Aircrafts a WHERE a.aircraftType = :aircraftType"),
    @NamedQuery(name = "Aircrafts.findByOperator", query = "SELECT a FROM Aircrafts a WHERE a.operator = :operator"),
    @NamedQuery(name = "Aircrafts.findByCreatedDate", query = "SELECT a FROM Aircrafts a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "Aircrafts.findByCreatedUserID", query = "SELECT a FROM Aircrafts a WHERE a.createdUserID = :createdUserID"),
    @NamedQuery(name = "Aircrafts.findByUpdatedDate", query = "SELECT a FROM Aircrafts a WHERE a.updatedDate = :updatedDate"),
    @NamedQuery(name = "Aircrafts.findByUpdatedUserID", query = "SELECT a FROM Aircrafts a WHERE a.updatedUserID = :updatedUserID")})
public class Aircrafts implements Serializable {

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
    @Column(name = "RegistrationNo")
    private String registrationNo;
    @Column(name = "Model")
    private String model;
    @Column(name = "AircraftType")
    private String aircraftType;
    @Column(name = "Operator")
    private String operator;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "CreatedUserID")
    private Integer createdUserID;
    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "UpdatedUserID")
    private Integer updatedUserID;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(Integer createdUserID) {
        this.createdUserID = createdUserID;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedUserID() {
        return updatedUserID;
    }

    public void setUpdatedUserID(Integer updatedUserID) {
        this.updatedUserID = updatedUserID;
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
        return "com.attech.hibernation.entities.Aircrafts[ id=" + id + " ]";
    }
    
}
