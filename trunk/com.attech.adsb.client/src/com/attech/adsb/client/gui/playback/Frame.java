/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.playback;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ANDH
 */
public class Frame {

    private long second;
    private List<byte[]> items;
    
    public Frame(long second) {
        this.second = second;
        this.items = new ArrayList();
    }
    
    public void add(byte[] item) {
        this.items.add(item);
    }
    
    /**
     * @return the second
     */
    public long getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(long second) {
        this.second = second;
    }

    /**
     * @return the items
     */
    public List<byte[]> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<byte[]> items) {
        this.items = items;
    }
    
    public int getSize( ) {
        int i = 0;
         for (byte[] bytes :items) {
             i += bytes.length;
         }
         
         return i;
    }
    
//    public boolean add()
    
    
}
