/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "FlightPlan")
@XmlAccessorType(XmlAccessType.NONE)
public class FlightPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "id")
    private Integer id;
    
    @XmlElement(name = "dof")
    private String dof;
    
    @XmlElement(name = "callSign")
    private String callSign;
    
    @XmlElement(name = "dep")
    private String dep;
    
    @XmlElement(name = "dest")
    private String dest;
    
    @XmlElement(name = "etd")
    private String etd;
    
    @XmlElement(name = "reg")
    private String reg;
    
    @XmlElement(name = "craft")
    private String craft;
    
    @XmlElement(name = "eta")
    private String eta;
    
    @XmlElement(name = "eet")
    private String eet;
    
    @XmlElement(name = "atd")
    private String atd;
    
    @XmlElement(name = "ata")
    private String ata;
   
    @XmlElement(name = "seq")
    private Integer seq;
    
    @XmlElement(name = "depSeq")
    private Integer depSeq;
    
    @XmlElement(name = "arrSeq")
    private Integer arrSeq;

    @XmlElement(name = "route")
    private String route;

    @XmlElement(name = "remark")
    private String remark;
    
    @XmlElement(name = "createdDate")
    private Date createdDate;
    
    @XmlElement(name = "updatedDate")
    private Date updatedDate;
    
    @XmlElement(name = "dailyFplID")
    private Integer dailyFplID;
    
    @XmlElement(name = "fplID")
    private Integer fplID;
    
    @XmlElement(name = "arrID")
    private Integer arrID;
    
    @XmlElement(name = "depID")
    private Integer depID;

    public FlightPlan() {
    }

    public FlightPlan(Integer id) {
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
        if (!(object instanceof FlightPlan)) {
            return false;
        }
        FlightPlan other = (FlightPlan) object;
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
