/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.common;

import java.util.*;

/**
 *
 * @author Tang Hai Anh
 */
public class RangeCallsign {
    private String callsign;
    private Set <Integer> index;
    
    public RangeCallsign(){
        this.index = new HashSet<>();
    }
    
    public RangeCallsign(String callsign){
        this();
        this.callsign = callsign;
    }
    
    public void addIndex(int index){
        this.getIndex().add(index);
    }
    
    public int getRangeValue(){
        return getIndex().size();
    }
    
    public int getLast(){
        return Collections.max(getIndex());
    }
    
    public int getFirst(){
        return Collections.min(getIndex());
    }
    
    /**
     * @return the callsign
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * @param callsign the callsign to set
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    /**
     * @return the index
     */
    public Set <Integer> getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(Set <Integer> index) {
        this.index = index;
    }

}
