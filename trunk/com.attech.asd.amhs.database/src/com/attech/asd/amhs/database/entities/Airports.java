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
@Table(name = "airports")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airports.findAll", query = "SELECT a FROM Airports a"),
    @NamedQuery(name = "Airports.findById", query = "SELECT a FROM Airports a WHERE a.id = :id"),
    @NamedQuery(name = "Airports.findByName", query = "SELECT a FROM Airports a WHERE a.name = :name"),
    @NamedQuery(name = "Airports.findByLongitude", query = "SELECT a FROM Airports a WHERE a.longitude = :longitude"),
    @NamedQuery(name = "Airports.findByLatitude", query = "SELECT a FROM Airports a WHERE a.latitude = :latitude"),
    @NamedQuery(name = "Airports.findByType", query = "SELECT a FROM Airports a WHERE a.type = :type"),
    @NamedQuery(name = "Airports.findByIata", query = "SELECT a FROM Airports a WHERE a.iata = :iata"),
    @NamedQuery(name = "Airports.findByIcao", query = "SELECT a FROM Airports a WHERE a.icao = :icao"),
    @NamedQuery(name = "Airports.findByAddress", query = "SELECT a FROM Airports a WHERE a.address = :address"),
    @NamedQuery(name = "Airports.findByDescription", query = "SELECT a FROM Airports a WHERE a.description = :description"),
    @NamedQuery(name = "Airports.findByCreatedDate", query = "SELECT a FROM Airports a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "Airports.findByCreatedUserID", query = "SELECT a FROM Airports a WHERE a.createdUserID = :createdUserID"),
    @NamedQuery(name = "Airports.findByUpdatedDate", query = "SELECT a FROM Airports a WHERE a.updatedDate = :updatedDate"),
    @NamedQuery(name = "Airports.findByUpdatedUserID", query = "SELECT a FROM Airports a WHERE a.updatedUserID = :updatedUserID")})
public class Airports implements Serializable {

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
    @Column(name = "Longitude")
    private float longitude;
    @Basic(optional = false)
    @Column(name = "Latitude")
    private float latitude;
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

    public Airports() {
    }

    public Airports(Integer id) {
        this.id = id;
    }

    public Airports(Integer id, String name, float longitude, float latitude) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Airports)) {
            return false;
        }
        Airports other = (Airports) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Airports[ id=" + id + " ]";
    }
    
}
