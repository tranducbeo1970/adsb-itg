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
@Table(name = "filerecord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filerecord.findAll", query = "SELECT f FROM Filerecord f"),
    @NamedQuery(name = "Filerecord.findById", query = "SELECT f FROM Filerecord f WHERE f.id = :id"),
    @NamedQuery(name = "Filerecord.findBySensorId", query = "SELECT f FROM Filerecord f WHERE f.sensorId = :sensorId"),
    @NamedQuery(name = "Filerecord.findByFromDatetime", query = "SELECT f FROM Filerecord f WHERE f.fromDatetime = :fromDatetime"),
    @NamedQuery(name = "Filerecord.findByToDatetime", query = "SELECT f FROM Filerecord f WHERE f.toDatetime = :toDatetime"),
    @NamedQuery(name = "Filerecord.findByAbsolutePath", query = "SELECT f FROM Filerecord f WHERE f.absolutePath = :absolutePath"),
    @NamedQuery(name = "Filerecord.findByFileName", query = "SELECT f FROM Filerecord f WHERE f.fileName = :fileName"),
    @NamedQuery(name = "Filerecord.findByStatus", query = "SELECT f FROM Filerecord f WHERE f.status = :status"),
    @NamedQuery(name = "Filerecord.findByCountPackage", query = "SELECT f FROM Filerecord f WHERE f.countPackage = :countPackage"),
    @NamedQuery(name = "Filerecord.findByCountMessage", query = "SELECT f FROM Filerecord f WHERE f.countMessage = :countMessage"),
    @NamedQuery(name = "Filerecord.findByCountFlight", query = "SELECT f FROM Filerecord f WHERE f.countFlight = :countFlight"),
    @NamedQuery(name = "Filerecord.findByCountCraft", query = "SELECT f FROM Filerecord f WHERE f.countCraft = :countCraft")})
public class Filerecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "SensorId")
    private int sensorId;
    @Column(name = "FromDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDatetime;
    @Column(name = "ToDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDatetime;
    @Column(name = "AbsolutePath")
    private String absolutePath;
    @Column(name = "FileName")
    private String fileName;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "CountPackage")
    private Integer countPackage;
    @Column(name = "CountMessage")
    private Integer countMessage;
    @Column(name = "CountFlight")
    private Integer countFlight;
    @Column(name = "CountCraft")
    private Integer countCraft;

    public Filerecord() {
    }

    public Filerecord(Integer id) {
        this.id = id;
    }

    public Filerecord(Integer id, int sensorId) {
        this.id = id;
        this.sensorId = sensorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public Date getFromDatetime() {
        return fromDatetime;
    }

    public void setFromDatetime(Date fromDatetime) {
        this.fromDatetime = fromDatetime;
    }

    public Date getToDatetime() {
        return toDatetime;
    }

    public void setToDatetime(Date toDatetime) {
        this.toDatetime = toDatetime;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCountPackage() {
        return countPackage;
    }

    public void setCountPackage(Integer countPackage) {
        this.countPackage = countPackage;
    }

    public Integer getCountMessage() {
        return countMessage;
    }

    public void setCountMessage(Integer countMessage) {
        this.countMessage = countMessage;
    }

    public Integer getCountFlight() {
        return countFlight;
    }

    public void setCountFlight(Integer countFlight) {
        this.countFlight = countFlight;
    }

    public Integer getCountCraft() {
        return countCraft;
    }

    public void setCountCraft(Integer countCraft) {
        this.countCraft = countCraft;
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
        if (!(object instanceof Filerecord)) {
            return false;
        }
        Filerecord other = (Filerecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Filerecord[ id=" + id + " ]";
    }
    
}
