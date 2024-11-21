/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author NhuongND
 */
@Entity
@Table(name = "flights")
public class Flights {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    protected Integer id;
    @Column(name = "Icao24Address")
    protected String icao24Address;
    @Column(name = "Callsign")
    protected String callsign;
    @Column(name = "FromTime")
    protected Date fromtime;
    @Column(name = "ToTime")
    protected Date totime;
    @ManyToOne
    @JoinColumn(name = "SensorId")
    private Sensors sensor;
    @Column(name = "TotalMessage")
    protected Integer totalMessage;
    @Column(name = "TotalFrame")
    protected Integer totalFrame;
    @Column(name = "TrackNo")
    protected Integer trackNo;
    @Column(name = "Code3A")
    protected String code3A;

    public Flights() {
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the Icao24Address
     */
    public String getIcao24Address() {
        return icao24Address;
    }

    /**
     * @param Icao24Address the Icao24Address to set
     */
    public void setIcao24Address(String Icao24Address) {
        this.icao24Address = Icao24Address;
    }

    /**
     * @return the callsign
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * @param callsign the callsign to set
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    /**
     * @return the fromtime
     */
    public Date getFromtime() {
        return fromtime;
    }

    /**
     * @param fromtime the fromtime to set
     */
    public void setFromtime(Date fromtime) {
        this.fromtime = fromtime;
    }

    /**
     * @return the totime
     */
    public Date getTotime() {
        return totime;
    }

    /**
     * @param totime the totime to set
     */
    public void setTotime(Date totime) {
        this.totime = totime;
    }

    /**
     * @return the Sensor
     */
    public Sensors getSensor() {
        return sensor;
    }

    /**
     * @param Sensor the Sensor to set
     */
    public void setSensor(Sensors sensor) {
        this.sensor = sensor;
    }

    /**
     * @return the totalMessage
     */
    public Integer getTotalMessage() {
        return totalMessage;
    }

    /**
     * @param totalMessage the totalMessage to set
     */
    public void setTotalMessage(Integer totalMessage) {
        this.totalMessage = totalMessage;
    }

    /**
     * @return the totalFrame
     */
    public Integer getTotalFrame() {
        return totalFrame;
    }

    /**
     * @param totalFrame the totalFrame to set
     */
    public void setTotalFrame(Integer totalFrame) {
        this.totalFrame = totalFrame;
    }
  
    /**
     * @return the TrackNo
     */
    public Integer getTrackNo() {
        return trackNo;
    }

    /**
     * @param trackNo the trackNo to set
     */
    public void setTrackNo(Integer trackNo) {
        this.trackNo = trackNo;
    }

    /**
     * @return the code3A
     */
    public String getCode3A() {
        return code3A;
    }

    /**
     * @param code3A the code3A to set
     */
    public void setCode3A(String code3A) {
        this.code3A = code3A;
    }
}
