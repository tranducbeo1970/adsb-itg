/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Bang INBOX de luu ban tin
 * @author AnhTH
 */
@Entity
@Table(name = "messages")
public class Messages implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    protected Long id;    
    
    @Column(name = "ATS_Extention")
    protected String atsExtention;   
    
    @Column(name = "ATS_Filing_Time")
    protected Date atsFilingTime;
    
    @Column(name = "ATS_OHI")
    protected String atsOhi;
    
    @Column(name = "ATS_Priority")
    protected String atsPriority;
    
    @Column(name = "Category")
    protected String category;
    
    @Column(name = "Content")
    protected String content;
    
    @Column(name = "Created_Date")
    protected Date createdDate;
    
    @Column(name = "Date")
    protected Date date;
    
    @Column(name = "Delivery_Time")
    protected Date deliveryTime;
    
    @Column(name = "Last_Updated")
    protected Date lastUpdated;
          
    
    public Messages(){
        
    }
    
    /**
     * @return the Id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(Long Id) {
        this.id = Id;
    }
    
    /**
     * @return the ATS_Extention
     */
    public String getAtsExtention() {
        return atsExtention;
    }

    /**
     * @param AtsExtention the AtsExtention to set
     */
    public void setAtsExtention(String AtsExtention) {
        this.atsExtention = AtsExtention;
    }
    
    /**
     * @return the AtsFilingTime
     */
    public Date getAtsFilingTime() {
        return atsFilingTime;
    }

    /**
     * @param AtsFilingTime the AtsFilingTime to set
     */
    public void setAtsFilingTime(Date AtsFilingTime) {
        this.atsFilingTime = AtsFilingTime;
    }
    
    /**
     * @return the AtsOhi
     */
    public String getAtsOhi() {
        return atsOhi;
    }

    /**
     * @param AtsOhi the AtsOhi to set
     */
    public void setAtsOhi(String AtsOhi) {
        this.atsOhi = AtsOhi;
    }
    
    /**
     * @return the AtsPriority
     */
    public String getAtsPriority() {
        return atsPriority;
    }

    /**
     * @param AtsPriority the AtsPriority to set
     */
    public void setAtsPriority(String AtsPriority) {
        this.atsPriority = AtsPriority;
    }
    
    /**
     * @return the Category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param Category the Category to set
     */
    public void setCategory(String Category) {
        this.category = Category;
    }
    
    /**
     * @return the ATS_Extention
     */
    public String getContent() {
        return content;
    }

    /**
     * @param Content the Content to set
     */
    public void setContent(String Content) {
        this.content = Content;
    }
    
    /**
     * @return the CreatedDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param CreatedDate the CreatedDate to set
     */
    public void setCreatedDate(Date CreatedDate) {
        this.createdDate = CreatedDate;
    }
    
    /**
     * @return the Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param Date the Date to set
     */
    public void setDate(Date Date) {
        this.date = Date;
    }
    
    /**
     * @return the DeliveryTime
     */
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * @param DeliveryTime the DeliveryTime to set
     */
    public void setDeliveryTime(Date DeliveryTime) {
        this.deliveryTime = DeliveryTime;
    }
    
    /**
     * @return the LastUpdated
     */
    public Date getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param LastUpdated the LastUpdated to set
     */
    public void setLastUpdated(Date LastUpdated) {
        this.lastUpdated = LastUpdated;
    }
    
}
