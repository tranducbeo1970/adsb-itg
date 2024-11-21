/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;
import com.attech.adsb.lib.common.Calculator;
import com.attech.asd.database.CircularsDao;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *
 * @author Tang Hai Anh
 */
@Entity
@Table(name = "circulars")
public class Circulars {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Radius")
    private Float radius;
    @Column(name = "Latitude")
    private Float latitude;
    @Column(name = "Longitude")
    private Float longitude;
    
    public Circulars(){
        
    }
    
    public Circulars(Float r, Float latitude, Float longitude){
        this.radius = r;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public boolean isIn(Float lon, Float lat){
        Double distance = Calculator.distanceBetween(this.longitude, this.latitude, lon, lat);
        return distance.floatValue() <= this.radius;
    }
    
    public boolean isIn(double lon, double lat){
        Double distance = Calculator.distanceBetween(this.longitude, this.latitude, lon, lat);
        return distance.floatValue() <= this.radius;
    }
    
    public void save(){
        new CircularsDao().save(this);
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
     * @return the radius
     */
    public Float getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(Float radius) {
        this.radius = radius;
    }

    /**
     * @return the latitude
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
