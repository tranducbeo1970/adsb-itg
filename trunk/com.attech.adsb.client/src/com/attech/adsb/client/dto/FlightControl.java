/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.dto;

import com.attech.adsb.client.common.TrackStatus;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "FlightControl")
@XmlAccessorType(XmlAccessType.NONE)
public class FlightControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "callSign")
    private String callSign;

    @XmlElement(name = "address")
    private String address;

    @XmlElement(name = "controller")
    private String controller;

    @XmlElement(name = "assumTime")
    private Date assumTime;

    @XmlElement(name = "status")
    private Integer status;

    @XmlElement(name = "targetCotroller")
    private String targetCotroller;

    @XmlElement(name = "lastUpdate")
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
    
    public boolean isControlled(String sector) {
        return !(this.controller == null || this.controller.isEmpty() || !this.controller.equalsIgnoreCase(sector));
    }
    
    public TrackStatus getTrackStatus(String sector) {
        if (this.controller == null || this.controller.isEmpty()) return TrackStatus.NONE;
        if (this.controller.equalsIgnoreCase(sector)) {
//            if (this.targetCotroller != null && this.targeCotroller.isEmpty()) return TrackStatus.CONTROLED;
            return (this.targetCotroller != null && !this.targetCotroller.isEmpty()) ? TrackStatus.TRANSFER_FROM_THIS : TrackStatus.CONTROLED;
        }
        
        return (this.controller == null || this.controller.isEmpty() || !this.controller.equalsIgnoreCase(sector)) ? TrackStatus.NONE : TrackStatus.TRANSFER_TO_THIS;
    }

    public boolean isOsolete(long period) {
        long delta = System.currentTimeMillis() - this.lastUpdate.getTime();
        return delta > period * 1000;
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
