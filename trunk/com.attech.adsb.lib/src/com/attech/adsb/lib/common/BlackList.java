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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "BlackList")
@XmlAccessorType(XmlAccessType.NONE)
public class BlackList {
    
    @XmlElement(name="items")
    private List<BlackListItem> items;
    
    public BlackList() {
        this.items = new ArrayList<>();
    }

    /**
     * @return the items
     */
    public List<BlackListItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<BlackListItem> items) {
        this.items = items;
    }
    
    public void add(String file, String address) {
        BlackListItem item = new BlackListItem();
        item.setFile(file);
        
    }
    
    
}
