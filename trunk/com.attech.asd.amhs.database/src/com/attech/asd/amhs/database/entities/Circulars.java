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
@Table(name = "circulars")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Circulars.findAll", query = "SELECT c FROM Circulars c"),
    @NamedQuery(name = "Circulars.findById", query = "SELECT c FROM Circulars c WHERE c.id = :id"),
    @NamedQuery(name = "Circulars.findByRadius", query = "SELECT c FROM Circulars c WHERE c.radius = :radius"),
    @NamedQuery(name = "Circulars.findByLatitude", query = "SELECT c FROM Circulars c WHERE c.latitude = :latitude"),
    @NamedQuery(name = "Circulars.findByLongitude", query = "SELECT c FROM Circulars c WHERE c.longitude = :longitude")})
public class Circulars implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Radius")
    private Float radius;
    @Column(name = "Latitude")
    private Float latitude;
    @Column(name = "Longitude")
    private Float longitude;

    public Circulars() {
    }

    public Circulars(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
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
        if (!(object instanceof Circulars)) {
            return false;
        }
        Circulars other = (Circulars) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Circulars[ id=" + id + " ]";
    }
    
}
