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
@Table(name = "clientcircular")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientcircular.findAll", query = "SELECT c FROM Clientcircular c"),
    @NamedQuery(name = "Clientcircular.findById", query = "SELECT c FROM Clientcircular c WHERE c.id = :id"),
    @NamedQuery(name = "Clientcircular.findByClientid", query = "SELECT c FROM Clientcircular c WHERE c.clientid = :clientid"),
    @NamedQuery(name = "Clientcircular.findByCircularid", query = "SELECT c FROM Clientcircular c WHERE c.circularid = :circularid")})
public class Clientcircular implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "clientid")
    private int clientid;
    @Basic(optional = false)
    @Column(name = "circularid")
    private int circularid;

    public Clientcircular() {
    }

    public Clientcircular(Integer id) {
        this.id = id;
    }

    public Clientcircular(Integer id, int clientid, int circularid) {
        this.id = id;
        this.clientid = clientid;
        this.circularid = circularid;
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

    public int getCircularid() {
        return circularid;
    }

    public void setCircularid(int circularid) {
        this.circularid = circularid;
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
        if (!(object instanceof Clientcircular)) {
            return false;
        }
        Clientcircular other = (Clientcircular) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Clientcircular[ id=" + id + " ]";
    }
    
}
