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
@Table(name = "areacoordinates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Areacoordinates.findAll", query = "SELECT a FROM Areacoordinates a"),
    @NamedQuery(name = "Areacoordinates.findById", query = "SELECT a FROM Areacoordinates a WHERE a.id = :id"),
    @NamedQuery(name = "Areacoordinates.findByLongitude", query = "SELECT a FROM Areacoordinates a WHERE a.longitude = :longitude"),
    @NamedQuery(name = "Areacoordinates.findByLatitude", query = "SELECT a FROM Areacoordinates a WHERE a.latitude = :latitude"),
    @NamedQuery(name = "Areacoordinates.findByAreaId", query = "SELECT a FROM Areacoordinates a WHERE a.areaId = :areaId")})
public class Areacoordinates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Longitude")
    private float longitude;
    @Basic(optional = false)
    @Column(name = "Latitude")
    private float latitude;
    @Basic(optional = false)
    @Column(name = "AreaId")
    private int areaId;

    public Areacoordinates() {
    }

    public Areacoordinates(Integer id) {
        this.id = id;
    }

    public Areacoordinates(Integer id, float longitude, float latitude, int areaId) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.areaId = areaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
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
        if (!(object instanceof Areacoordinates)) {
            return false;
        }
        Areacoordinates other = (Areacoordinates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Areacoordinates[ id=" + id + " ]";
    }
    
}
