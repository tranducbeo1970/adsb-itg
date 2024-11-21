/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;
import com.attech.asd.database.AdapterObject;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 *
 * @author Tang Hai Anh
 */
@Entity
@Table(name = "filerecord")
public class FileRecord {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "SensorId")
    private Sensors sensor;
    @Column(name = "FromDatetime")
    //@Temporal(javax.persistence.TemporalType.DATE)
    private Date fromtime;
    
    @Column(name = "ToDatetime")
    //@Temporal(javax.persistence.TemporalType.DATE)
    private Date totime;
    @Column(name = "AbsolutePath")
    private String absolutePath;
    @Column(name = "FileName")
    private String fileName;
    @Column(name = "Status")
    private Integer status;
    
    @Column(name = "CountPackage")
    private Long countPackage;
    @Column(name = "CountMessage")
    private Long countMessage;
    @Column(name = "CountFlight")
    private Long countFlight;
    @Column(name = "CountCraft")
    private Long countCraft;
    
    @Transient
    private Map<String,Set> crafts;
    
    public FileRecord(){
        crafts = new HashMap<>();
    }
    
    public void putCraft(String address, String callsign){
        if (callsign==null) callsign = "";
        if (address == null) return;
        if (crafts.isEmpty() || !crafts.containsKey(address)){
            Set setA = new HashSet();
            setA.add(callsign);
            crafts.put(address, setA);
            this.countFlight ++;
        }
        else{
            if (!crafts.get(address).contains(callsign))
                this.countFlight ++;
            crafts.get(address).add(callsign);
        }
        this.countCraft = (crafts == null) ? 0 : (long) crafts.size();
    }
    
    public void save(){
        new AdapterObject().saveFileRecord(this);
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
     * @return the absolutePath
     */
    public String getAbsolutePath() {
        return absolutePath;
    }

    /**
     * @param absolutePath the absolutePath to set
     */
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
     * @return the countPackage
     */
    public Long getCountPackage() {
        return countPackage;
    }

    /**
     * @param countPackage the countPackage to set
     */
    public void setCountPackage(Long countPackage) {
        this.countPackage = countPackage;
    }

    /**
     * @return the countMessage
     */
    public Long getCountMessage() {
        return countMessage;
    }

    /**
     * @param countMessage the countMessage to set
     */
    public void setCountMessage(Long countMessage) {
        this.countMessage = countMessage;
    }

    /**
     * @return the countFlight
     */
    public Long getCountFlight() {
        return countFlight;
    }

    /**
     * @param countFlight the countFlight to set
     */
    public void setCountFlight(Long countFlight) {
        this.countFlight = countFlight;
    }

    /**
     * @return the countCraft
     */
    public Long getCountCraft() {
        return countCraft;
    }

    /**
     * @param countCraft the countCraft to set
     */
    public void setCountCraft(Long countCraft) {
        this.countCraft = countCraft;
    }

    /**
     * @return the crafts
     */
    public Map<String,Set> getCrafts() {
        return crafts;
    }

    /**
     * @param crafts the crafts to set
     */
    public void setCrafts(Map<String,Set> crafts) {
        this.crafts = crafts;
    }
    
    
}
