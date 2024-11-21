/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author anhth
 */
@Entity
@Table(name = "stations")
public class Stations implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;
    @Column(name = "StationName")
    private String stationName;
    @Column(name = "StationDescription")
    private String stationDescription;
    @Column(name = "SortNumber")
    private Integer sortNumber;
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Sensors> sensors;
    
    public Stations(){
    
    }
    
    public Stations(String stationName, String stationDescription, int sortNumber){
        this();
        this.stationName = stationName;
        this.stationDescription = stationDescription;
        this.sortNumber = sortNumber;
    }
    
    public Integer getSic(Integer sensorId){
        for (Sensors s: this.sensors){
            if (s.getId().equals(sensorId))
                return s.getSic();
        }
        return -1;
    }
    
    public Sensors getSensor(Integer sensorId){
        for (Sensors s: this.sensors){
            if (s.getId().equals(sensorId))
                return s;
        }
        return null;
    }

    /**
     * @return the Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(Integer Id) {
        this.id = Id;
    }

    /**
     * @return the StationName
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * @param StationName the StationName to set
     */
    public void setStationName(String StationName) {
        this.stationName = StationName;
    }

    /**
     * @return the StationDescription
     */
    public String getStationDescription() {
        return stationDescription;
    }

    /**
     * @param StationDescription the StationDescription to set
     */
    public void setStationDescription(String StationDescription) {
        this.stationDescription = StationDescription;
    }

    /**
     * @return the SortNumber
     */
    public Integer getSortNumber() {
        return sortNumber;
    }

    /**
     * @param SortNumber the SortNumber to set
     */
    public void setSortNumber(Integer SortNumber) {
        this.sortNumber = SortNumber;
    }

    /**
     * @return the sensors
     */
    public Set<Sensors> getSensors() {
        return sensors;
    }

    /**
     * @param sensors the sensors to set
     */
    public void setSensors(Set<Sensors> sensors) {
        this.sensors = sensors;
    }

    @Override
    public String toString(){
        if (this == null) return "";
        return String.format("Station: Id: %s | Name: %s | Description: %s", (id == null) ? 0 : id, (stationName != null) ? stationName : "", (stationDescription != null) ? stationDescription : "");
    }

}
