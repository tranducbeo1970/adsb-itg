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
@Table(name = "dailyanalyzing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dailyanalyzing.findAll", query = "SELECT d FROM Dailyanalyzing d"),
    @NamedQuery(name = "Dailyanalyzing.findById", query = "SELECT d FROM Dailyanalyzing d WHERE d.id = :id"),
    @NamedQuery(name = "Dailyanalyzing.findBySensorId", query = "SELECT d FROM Dailyanalyzing d WHERE d.sensorId = :sensorId"),
    @NamedQuery(name = "Dailyanalyzing.findByDateResult", query = "SELECT d FROM Dailyanalyzing d WHERE d.dateResult = :dateResult"),
    @NamedQuery(name = "Dailyanalyzing.findByTotalPackage", query = "SELECT d FROM Dailyanalyzing d WHERE d.totalPackage = :totalPackage"),
    @NamedQuery(name = "Dailyanalyzing.findByTotalMessage", query = "SELECT d FROM Dailyanalyzing d WHERE d.totalMessage = :totalMessage"),
    @NamedQuery(name = "Dailyanalyzing.findByTotalCrafts", query = "SELECT d FROM Dailyanalyzing d WHERE d.totalCrafts = :totalCrafts"),
    @NamedQuery(name = "Dailyanalyzing.findByTotalFlights", query = "SELECT d FROM Dailyanalyzing d WHERE d.totalFlights = :totalFlights"),
    @NamedQuery(name = "Dailyanalyzing.findByStatus", query = "SELECT d FROM Dailyanalyzing d WHERE d.status = :status"),
    @NamedQuery(name = "Dailyanalyzing.findByTotalBadMsg", query = "SELECT d FROM Dailyanalyzing d WHERE d.totalBadMsg = :totalBadMsg"),
    @NamedQuery(name = "Dailyanalyzing.findByListBadFlights", query = "SELECT d FROM Dailyanalyzing d WHERE d.listBadFlights = :listBadFlights"),
    @NamedQuery(name = "Dailyanalyzing.findByDo260", query = "SELECT d FROM Dailyanalyzing d WHERE d.do260 = :do260"),
    @NamedQuery(name = "Dailyanalyzing.findByDo260A", query = "SELECT d FROM Dailyanalyzing d WHERE d.do260A = :do260A"),
    @NamedQuery(name = "Dailyanalyzing.findByDo260B", query = "SELECT d FROM Dailyanalyzing d WHERE d.do260B = :do260B"),
    @NamedQuery(name = "Dailyanalyzing.findByModeAC", query = "SELECT d FROM Dailyanalyzing d WHERE d.modeAC = :modeAC"),
    @NamedQuery(name = "Dailyanalyzing.findByModeS", query = "SELECT d FROM Dailyanalyzing d WHERE d.modeS = :modeS"),
    @NamedQuery(name = "Dailyanalyzing.findByModeSExt", query = "SELECT d FROM Dailyanalyzing d WHERE d.modeSExt = :modeSExt")})
public class Dailyanalyzing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "SensorId")
    private int sensorId;
    @Basic(optional = false)
    @Column(name = "DateResult")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateResult;
    @Column(name = "TotalPackage")
    private Integer totalPackage;
    @Column(name = "TotalMessage")
    private Integer totalMessage;
    @Column(name = "TotalCrafts")
    private Integer totalCrafts;
    @Column(name = "TotalFlights")
    private Integer totalFlights;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "TotalBadMsg")
    private Integer totalBadMsg;
    @Column(name = "ListBadFlights")
    private String listBadFlights;
    @Column(name = "Do260")
    private Integer do260;
    @Column(name = "Do260A")
    private Integer do260A;
    @Column(name = "Do260B")
    private Integer do260B;
    @Column(name = "ModeAC")
    private Integer modeAC;
    @Column(name = "ModeS")
    private Integer modeS;
    @Column(name = "ModeSExt")
    private Integer modeSExt;

    public Dailyanalyzing() {
    }

    public Dailyanalyzing(Integer id) {
        this.id = id;
    }

    public Dailyanalyzing(Integer id, int sensorId, Date dateResult) {
        this.id = id;
        this.sensorId = sensorId;
        this.dateResult = dateResult;
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

    public Date getDateResult() {
        return dateResult;
    }

    public void setDateResult(Date dateResult) {
        this.dateResult = dateResult;
    }

    public Integer getTotalPackage() {
        return totalPackage;
    }

    public void setTotalPackage(Integer totalPackage) {
        this.totalPackage = totalPackage;
    }

    public Integer getTotalMessage() {
        return totalMessage;
    }

    public void setTotalMessage(Integer totalMessage) {
        this.totalMessage = totalMessage;
    }

    public Integer getTotalCrafts() {
        return totalCrafts;
    }

    public void setTotalCrafts(Integer totalCrafts) {
        this.totalCrafts = totalCrafts;
    }

    public Integer getTotalFlights() {
        return totalFlights;
    }

    public void setTotalFlights(Integer totalFlights) {
        this.totalFlights = totalFlights;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalBadMsg() {
        return totalBadMsg;
    }

    public void setTotalBadMsg(Integer totalBadMsg) {
        this.totalBadMsg = totalBadMsg;
    }

    public String getListBadFlights() {
        return listBadFlights;
    }

    public void setListBadFlights(String listBadFlights) {
        this.listBadFlights = listBadFlights;
    }

    public Integer getDo260() {
        return do260;
    }

    public void setDo260(Integer do260) {
        this.do260 = do260;
    }

    public Integer getDo260A() {
        return do260A;
    }

    public void setDo260A(Integer do260A) {
        this.do260A = do260A;
    }

    public Integer getDo260B() {
        return do260B;
    }

    public void setDo260B(Integer do260B) {
        this.do260B = do260B;
    }

    public Integer getModeAC() {
        return modeAC;
    }

    public void setModeAC(Integer modeAC) {
        this.modeAC = modeAC;
    }

    public Integer getModeS() {
        return modeS;
    }

    public void setModeS(Integer modeS) {
        this.modeS = modeS;
    }

    public Integer getModeSExt() {
        return modeSExt;
    }

    public void setModeSExt(Integer modeSExt) {
        this.modeSExt = modeSExt;
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
        if (!(object instanceof Dailyanalyzing)) {
            return false;
        }
        Dailyanalyzing other = (Dailyanalyzing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Dailyanalyzing[ id=" + id + " ]";
    }
    
}
