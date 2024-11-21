/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "flightplandetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flightplandetail.findAll", query = "SELECT f FROM Flightplandetail f"),
    @NamedQuery(name = "Flightplandetail.findById", query = "SELECT f FROM Flightplandetail f WHERE f.id = :id"),
    @NamedQuery(name = "Flightplandetail.findByPlanID", query = "SELECT f FROM Flightplandetail f WHERE f.planID = :planID")})
public class Flightplandetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PlanID")
    private Integer planID;

    public Flightplandetail() {
    }

    public Flightplandetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanID() {
        return planID;
    }

    public void setPlanID(Integer planID) {
        this.planID = planID;
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
        if (!(object instanceof Flightplandetail)) {
            return false;
        }
        Flightplandetail other = (Flightplandetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Flightplandetail[ id=" + id + " ]";
    }
    
}
