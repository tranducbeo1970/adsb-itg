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
 * @author hong
 */
@Entity
@Table(name = "flightplan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flightplan.findAll", query = "SELECT f FROM Flightplan f"),
    @NamedQuery(name = "Flightplan.findById", query = "SELECT f FROM Flightplan f WHERE f.id = :id"),
    @NamedQuery(name = "Flightplan.findByDof", query = "SELECT f FROM Flightplan f WHERE f.dof = :dof"),
    @NamedQuery(name = "Flightplan.findByCallSign", query = "SELECT f FROM Flightplan f WHERE f.callSign = :callSign"),
    @NamedQuery(name = "Flightplan.findByDep", query = "SELECT f FROM Flightplan f WHERE f.dep = :dep"),
    @NamedQuery(name = "Flightplan.findByDest", query = "SELECT f FROM Flightplan f WHERE f.dest = :dest"),
    @NamedQuery(name = "Flightplan.findByEtd", query = "SELECT f FROM Flightplan f WHERE f.etd = :etd"),
    @NamedQuery(name = "Flightplan.findByReg", query = "SELECT f FROM Flightplan f WHERE f.reg = :reg"),
    @NamedQuery(name = "Flightplan.findByCraft", query = "SELECT f FROM Flightplan f WHERE f.craft = :craft"),
    @NamedQuery(name = "Flightplan.findByEta", query = "SELECT f FROM Flightplan f WHERE f.eta = :eta"),
    @NamedQuery(name = "Flightplan.findByEet", query = "SELECT f FROM Flightplan f WHERE f.eet = :eet"),
    @NamedQuery(name = "Flightplan.findByAtd", query = "SELECT f FROM Flightplan f WHERE f.atd = :atd"),
    @NamedQuery(name = "Flightplan.findByAta", query = "SELECT f FROM Flightplan f WHERE f.ata = :ata"),
    @NamedQuery(name = "Flightplan.findBySeq", query = "SELECT f FROM Flightplan f WHERE f.seq = :seq"),
    @NamedQuery(name = "Flightplan.findByDepSeq", query = "SELECT f FROM Flightplan f WHERE f.depSeq = :depSeq"),
    @NamedQuery(name = "Flightplan.findByArrSeq", query = "SELECT f FROM Flightplan f WHERE f.arrSeq = :arrSeq"),
    @NamedQuery(name = "Flightplan.findByRoute", query = "SELECT f FROM Flightplan f WHERE f.route = :route"),
    @NamedQuery(name = "Flightplan.findByRemark", query = "SELECT f FROM Flightplan f WHERE f.remark = :remark"),
    @NamedQuery(name = "Flightplan.findByCreatedDate", query = "SELECT f FROM Flightplan f WHERE f.createdDate = :createdDate"),
    @NamedQuery(name = "Flightplan.findByUpdatedDate", query = "SELECT f FROM Flightplan f WHERE f.updatedDate = :updatedDate"),
    @NamedQuery(name = "Flightplan.findByDailyFplID", query = "SELECT f FROM Flightplan f WHERE f.dailyFplID = :dailyFplID"),
    @NamedQuery(name = "Flightplan.findByFplID", query = "SELECT f FROM Flightplan f WHERE f.fplID = :fplID"),
    @NamedQuery(name = "Flightplan.findByArrID", query = "SELECT f FROM Flightplan f WHERE f.arrID = :arrID"),
    @NamedQuery(name = "Flightplan.findByDepID", query = "SELECT f FROM Flightplan f WHERE f.depID = :depID")})
public class Flightplan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Dof")
    private String dof;
    @Column(name = "CallSign")
    private String callSign;
    @Column(name = "Dep")
    private String dep;
    @Column(name = "Dest")
    private String dest;
    @Column(name = "Etd")
    private String etd;
    @Column(name = "Reg")
    private String reg;
    @Column(name = "Craft")
    private String craft;
    @Column(name = "Eta")
    private String eta;
    @Column(name = "Eet")
    private String eet;
    @Column(name = "Atd")
    private String atd;
    @Column(name = "Ata")
    private String ata;
    @Column(name = "Seq")
    private Integer seq;
    @Column(name = "DepSeq")
    private Integer depSeq;
    @Column(name = "ArrSeq")
    private Integer arrSeq;
    @Column(name = "Route")
    private String route;
    @Column(name = "Remark")
    private String remark;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "DailyFplID")
    private Integer dailyFplID;
    @Column(name = "FplID")
    private Integer fplID;
    @Column(name = "ArrID")
    private Integer arrID;
    @Column(name = "DepID")
    private Integer depID;

    public Flightplan() {
    }

    public Flightplan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDof() {
        return dof;
    }

    public void setDof(String dof) {
        this.dof = dof;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getEtd() {
        return etd;
    }

    public void setEtd(String etd) {
        this.etd = etd;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getEet() {
        return eet;
    }

    public void setEet(String eet) {
        this.eet = eet;
    }

    public String getAtd() {
        return atd;
    }

    public void setAtd(String atd) {
        this.atd = atd;
    }

    public String getAta() {
        return ata;
    }

    public void setAta(String ata) {
        this.ata = ata;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getDepSeq() {
        return depSeq;
    }

    public void setDepSeq(Integer depSeq) {
        this.depSeq = depSeq;
    }

    public Integer getArrSeq() {
        return arrSeq;
    }

    public void setArrSeq(Integer arrSeq) {
        this.arrSeq = arrSeq;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getDailyFplID() {
        return dailyFplID;
    }

    public void setDailyFplID(Integer dailyFplID) {
        this.dailyFplID = dailyFplID;
    }

    public Integer getFplID() {
        return fplID;
    }

    public void setFplID(Integer fplID) {
        this.fplID = fplID;
    }

    public Integer getArrID() {
        return arrID;
    }

    public void setArrID(Integer arrID) {
        this.arrID = arrID;
    }

    public Integer getDepID() {
        return depID;
    }

    public void setDepID(Integer depID) {
        this.depID = depID;
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
        if (!(object instanceof Flightplan)) {
            return false;
        }
        Flightplan other = (Flightplan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Flightplan[ id=" + id + " ]";
    }
    
    public void appendRemark(String remark) {
        if (this.remark == null) {
            this.remark = remark;
            return;
}
        
        this.remark += (" " + remark );
    }
}
