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
@Table(name = "clientarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientarea.findAll", query = "SELECT c FROM Clientarea c"),
    @NamedQuery(name = "Clientarea.findById", query = "SELECT c FROM Clientarea c WHERE c.id = :id"),
    @NamedQuery(name = "Clientarea.findByClientid", query = "SELECT c FROM Clientarea c WHERE c.clientid = :clientid"),
    @NamedQuery(name = "Clientarea.findByAreaid", query = "SELECT c FROM Clientarea c WHERE c.areaid = :areaid")})
public class Clientarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "clientid")
    private int clientid;
    @Column(name = "areaid")
    private Integer areaid;

    public Clientarea() {
    }

    public Clientarea(Integer id) {
        this.id = id;
    }

    public Clientarea(Integer id, int clientid) {
        this.id = id;
        this.clientid = clientid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
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
        if (!(object instanceof Clientarea)) {
            return false;
        }
        Clientarea other = (Clientarea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Clientarea[ id=" + id + " ]";
    }
    
}
