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
@Table(name = "stations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stations.findAll", query = "SELECT s FROM Stations s"),
    @NamedQuery(name = "Stations.findById", query = "SELECT s FROM Stations s WHERE s.id = :id"),
    @NamedQuery(name = "Stations.findByStationName", query = "SELECT s FROM Stations s WHERE s.stationName = :stationName"),
    @NamedQuery(name = "Stations.findByStationDescription", query = "SELECT s FROM Stations s WHERE s.stationDescription = :stationDescription"),
    @NamedQuery(name = "Stations.findBySortNumber", query = "SELECT s FROM Stations s WHERE s.sortNumber = :sortNumber")})
public class Stations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "StationName")
    private String stationName;
    @Column(name = "StationDescription")
    private String stationDescription;
    @Column(name = "SortNumber")
    private Short sortNumber;

    public Stations() {
    }

    public Stations(Integer id) {
        this.id = id;
    }

    public Stations(Integer id, String stationName) {
        this.id = id;
        this.stationName = stationName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationDescription() {
        return stationDescription;
    }

    public void setStationDescription(String stationDescription) {
        this.stationDescription = stationDescription;
    }

    public Short getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Short sortNumber) {
        this.sortNumber = sortNumber;
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
        if (!(object instanceof Stations)) {
            return false;
        }
        Stations other = (Stations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Stations[ id=" + id + " ]";
    }
    
}
