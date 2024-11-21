/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

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
public class BlackListItem {
    
    @XmlAttribute(name = "file")
    private String file;
    
    @XmlElement(name = "address")
    private List<String> addresses;
    
    public BlackListItem() {
        this.addresses = new ArrayList<>();
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
     * @return the addresses
     */
    public List<String> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }
    
    public void setAddress(String address) {
        this.addresses.add(address);
    }
    
    public void add(String add) {
        this.addresses.add(add);
    }
}
