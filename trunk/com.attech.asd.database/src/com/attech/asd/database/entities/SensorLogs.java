/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import com.attech.asd.database.SensorLogsDao;
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
 * @author Tang Hai Anh
 */
@Entity
@Table(name = "sensorlogs")
public class SensorLogs {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "SensorId")
    private Sensors sensor;
    
    @Column(name = "WarningContent")
    private String warningContent;
    @Column(name = "Priority")
    private Integer priority;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "Note")
    private String note;
    
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "LastModifyDate")
    private Date lastModifyDate;
    
    public SensorLogs(){
        
    }
    
    public SensorLogs(Sensors s, String content, int priority){
        this.sensor = s;
        this.createdDate = new Date();
        this.lastModifyDate = new Date();
        this.note = "";
        this.status = priority;
        this.priority = priority;
        this.warningContent = content;
    }
    
    public void add(Sensors s, String content, int priority){
        this.sensor = s;
        this.createdDate = new Date();
        this.lastModifyDate = new Date();
        this.note = "";
        this.status = priority;
        this.priority = priority;
        this.warningContent = content;
    }
    
    public void save(){
        new SensorLogsDao().save(this);
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
     * @return the sensor
     */
    public Sensors getSensor() {
        return sensor;
    }

    /**
     * @param sensor the sensor to set
     */
    public void setSensor(Sensors sensor) {
        this.sensor = sensor;
    }

    /**
     * @return the warningContent
     */
    public String getWarningContent() {
        return warningContent;
    }

    /**
     * @param warningContent the warningContent to set
     */
    public void setWarningContent(String warningContent) {
        this.warningContent = warningContent;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the lastModifyDate
     */
    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    /**
     * @param lastModifyDate the lastModifyDate to set
     */
    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
    
    public void saveLog(Sensors sensor, String content, Integer priority, Integer status, String note, Date created){
        SensorLogs log = new SensorLogs();
        log.setSensor(sensor);
        log.setWarningContent(content);
        log.setPriority(priority);
        log.setStatus(status);
        log.setNote(note);
        log.setCreatedDate(created);
        new SensorLogsDao().save(log);
    }
}
