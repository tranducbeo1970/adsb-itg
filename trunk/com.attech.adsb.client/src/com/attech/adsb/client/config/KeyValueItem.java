/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.NONE)
public class KeyValueItem {

    @XmlAttribute(name = "key")
    private String key;

    @XmlAttribute(name = "value")
    private String value;

    public KeyValueItem() {
    }

    public KeyValueItem(String key, String val) {
        this.key = key;
        this.value = val;
    }

    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public void setBoolean(Boolean value) {
        this.value = value.toString();
    }
    
    public Boolean getBoolean() {
        return Boolean.valueOf(this.value);
    }
    
    public void setInteger(Integer value) {
        this.value = value.toString();
    }
    
    public Integer getInteger() {
        return Integer.parseInt(this.value);
    }
    
    public void setDouble(Double value) {
        this.value = value.toString();
    }
    
    public Double getDouble() {
        return Double.parseDouble(this.value);
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeyValueItem)) {
            return false;
        }
        KeyValueItem other = (KeyValueItem) object;
        if ((this.key == null && other.key != null) || (this.key != null && !this.key.equalsIgnoreCase(other.key))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", this.key, this.value);
    }
}
