/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "BlackList")
@XmlAccessorType(XmlAccessType.NONE)
public class BlackList {
    
    @XmlAttribute(name = "file")
    private String file;
    
    @XmlElement(name="address")
    private List<String> address;
    
    public BlackList() {
        this.address = new ArrayList<>();
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return the address
     */
    public List<String> getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(List<String> address) {
        this.address = address;
    }

    public void add(String address) {
        this.address.add(address);
    }
}
