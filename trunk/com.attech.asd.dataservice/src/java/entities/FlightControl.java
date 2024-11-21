/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@Entity
@Table(name = "flight_control")
@XmlRootElement(name="FlightControl")
@NamedQueries({
    @NamedQuery(name = "FlightControl.findAll", query = "SELECT f FROM FlightControl f"),
    @NamedQuery(name = "FlightControl.findByCallSign", query = "SELECT f FROM FlightControl f WHERE f.callSign = :callSign"),
    @NamedQuery(name = "FlightControl.findByAddress", query = "SELECT f FROM FlightControl f WHERE f.address = :address"),
    @NamedQuery(name = "FlightControl.findByController", query = "SELECT f FROM FlightControl f WHERE f.controller = :controller"),
    @NamedQuery(name = "FlightControl.findByAssumTime", query = "SELECT f FROM FlightControl f WHERE f.assumTime = :assumTime"),
    @NamedQuery(name = "FlightControl.findByStatus", query = "SELECT f FROM FlightControl f WHERE f.status = :status"),
    @NamedQuery(name = "FlightControl.findByTargetCotroller", query = "SELECT f FROM FlightControl f WHERE f.targetCotroller = :targetCotroller"),
    @NamedQuery(name = "FlightControl.findByLastUpdate", query = "SELECT f FROM FlightControl f WHERE f.lastUpdate = :lastUpdate")})
public class FlightControl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CallSign")
    private String callSign;
    @Size(max = 10)
    @Column(name = "Address")
    private String address;
    @Size(max = 24)
    @Column(name = "Controller")
    private String controller;
    @Column(name = "AssumTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assumTime;
    @Column(name = "Status")
    private Integer status;
    @Size(max = 24)
    @Column(name = "TargetCotroller")
    private String targetCotroller;
    @Column(name = "LastUpdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    public FlightControl() {
    }

    public FlightControl(String callSign) {
	this.callSign = callSign;
    }

    public String getCallSign() {
	return callSign;
    }

    public void setCallSign(String callSign) {
	this.callSign = callSign;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getController() {
	return controller;
    }

    public void setController(String controller) {
	this.controller = controller;
    }

    public Date getAssumTime() {
	return assumTime;
    }

    public void setAssumTime(Date assumTime) {
	this.assumTime = assumTime;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public String getTargetCotroller() {
	return targetCotroller;
    }

    public void setTargetCotroller(String targetCotroller) {
	this.targetCotroller = targetCotroller;
    }

    public Date getLastUpdate() {
	return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
	this.lastUpdate = lastUpdate;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (callSign != null ? callSign.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof FlightControl)) {
	    return false;
	}
	FlightControl other = (FlightControl) object;
	if ((this.callSign == null && other.callSign != null) || (this.callSign != null && !this.callSign.equals(other.callSign))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "entities.FlightControl[ callSign=" + callSign + " ]";
    }
    
}
