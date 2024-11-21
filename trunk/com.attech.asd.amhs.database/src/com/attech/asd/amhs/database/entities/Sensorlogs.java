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
@Table(name = "sensorlogs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sensorlogs.findAll", query = "SELECT s FROM Sensorlogs s"),
    @NamedQuery(name = "Sensorlogs.findById", query = "SELECT s FROM Sensorlogs s WHERE s.id = :id"),
    @NamedQuery(name = "Sensorlogs.findBySensorId", query = "SELECT s FROM Sensorlogs s WHERE s.sensorId = :sensorId"),
    @NamedQuery(name = "Sensorlogs.findByWarningContent", query = "SELECT s FROM Sensorlogs s WHERE s.warningContent = :warningContent"),
    @NamedQuery(name = "Sensorlogs.findByPriority", query = "SELECT s FROM Sensorlogs s WHERE s.priority = :priority"),
    @NamedQuery(name = "Sensorlogs.findByStatus", query = "SELECT s FROM Sensorlogs s WHERE s.status = :status"),
    @NamedQuery(name = "Sensorlogs.findByNote", query = "SELECT s FROM Sensorlogs s WHERE s.note = :note"),
    @NamedQuery(name = "Sensorlogs.findByCreatedDate", query = "SELECT s FROM Sensorlogs s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "Sensorlogs.findByLastModifyDate", query = "SELECT s FROM Sensorlogs s WHERE s.lastModifyDate = :lastModifyDate")})
public class Sensorlogs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "SensorId")
    private Integer sensorId;
    @Column(name = "WarningContent")
    private String warningContent;
    @Column(name = "Priority")
    private Integer priority;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "Note")
    private String note;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "LastModifyDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifyDate;

    public Sensorlogs() {
    }

    public Sensorlogs(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getWarningContent() {
        return warningContent;
    }

    public void setWarningContent(String warningContent) {
        this.warningContent = warningContent;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
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
        if (!(object instanceof Sensorlogs)) {
            return false;
        }
        Sensorlogs other = (Sensorlogs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Sensorlogs[ id=" + id + " ]";
    }
    
}
