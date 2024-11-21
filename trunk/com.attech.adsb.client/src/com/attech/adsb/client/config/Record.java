/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "Record")
@XmlAccessorType(XmlAccessType.NONE)
public class Record {

    @XmlAttribute(name="enable")
    private boolean enable;
    
    @XmlElement(name="Location")
    private String recordLocation;
    
    @XmlElement(name="Length")
    private int recordLength;
    
    public Record() {
        
    }
    
    public Record(boolean enable, String location, int length) {
        this.enable = enable;
        this.recordLocation = location;
        this.recordLength = length;
    }
    
    /**
     * @return the recordLocation
     */
    public String getRecordLocation() {
        return recordLocation;
    }

    /**
     * @param recordLocation the recordLocation to set
     */
    public void setRecordLocation(String recordLocation) {
        this.recordLocation = recordLocation;
    }

    /**
     * @return the recordLength
     */
    public int getRecordLength() {
        return recordLength;
    }

    /**
     * @param recordLength the recordLength to set
     */
    public void setRecordLength(int recordLength) {
        this.recordLength = recordLength;
    }

    /**
     * @return the enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
