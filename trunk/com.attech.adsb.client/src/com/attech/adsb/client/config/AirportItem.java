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
@XmlRootElement(name = "Airport")
@XmlAccessorType(XmlAccessType.NONE)
public class AirportItem {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "enable")
    private Boolean enable;

    @XmlAttribute(name = "data-path")
    private String dataPath;

    public AirportItem() {
    }
    
    public AirportItem(String name, Boolean enable, String path) {
	this.name = name;
	this.enable = enable;
	this.dataPath = path;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Boolean getEnable() {
	return enable;
    }

    public void setEnable(Boolean enable) {
	this.enable = enable;
    }

    public String getDataPath() {
	return dataPath;
    }

    public void setDataPath(String dataPath) {
	this.dataPath = dataPath;
    }
}
