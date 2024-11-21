/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

/**
 *
 * @author Saitama
 */
public class DataWrapper {

    
    private String key;
    private Object data;
    
    public DataWrapper() {
    }
    
    public DataWrapper(String key, Object data) {
        this.data = data;
        this.key = key;
    }
    
    
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }
}
