/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon;

import java.io.Serializable;

/**
 *
 * @author hong
 */
public class Data implements Serializable {
   private Object data;
   private String key;
    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }
    
    public String getDataString() {
        try {
            return (String) data;
        } catch (Exception ex) {
            return "";
        }
    }
    
    public Integer getDataInt() {
        try {
            return (Integer) data; 
        } catch (Exception ex){
            return 0;
        }
    }
    
    public Data(String key, Object value){
        this.key = key;
        this.data = value;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
}
