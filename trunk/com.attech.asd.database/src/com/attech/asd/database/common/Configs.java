/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.common;

import com.attech.asd.database.ConfigDao;
import com.attech.asd.database.entities.Config;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author AnhTH
 */
public final class Configs {
    
    private final Map<String, Config> properties = new HashMap<>();

    /**
     * LOAD FROM DATABASE
     */
    public Configs() {
        List<Config> lstTmp = new ConfigDao().listAllConfig();
        lstTmp.stream().forEach((c) -> {this.setProperty(c.getParamName(), c);});
    }
    
    public String getParamValue(String paramName) {
        return getProperties().get(paramName).getParamValue();
    }
    
    public Config getConfig(String key) {
        return getProperties().get(key);
    }
    
    public void setConfig(String key, String value, String description) {
        if (getProperties().containsKey(key)){
            Config c = getProperties().get(key);
            c.setParamValue(value);
            c.setDescription(description);
            getProperties().put(key, c);
        }
    }
    
    public String getProperty(String key) {
        return getProperties().get(key).getParamValue();
    }

    public void setProperty(String key, Config value) {
        getProperties().put(key, value);
    }
    
    public void setProperty(String key, String value) {
        if (getProperties().containsKey(key)){
            Config c = getProperties().get(key);
            c.setParamValue(value);
            getProperties().put(key, c);
        }
        else{
            Config c = new Config();
            c.setParamName(key);
            c.setParamValue(value);
            c.setDescription("");
            getProperties().put(key, c);
        }
    }
    
    public void saveAllProperties(){
        ConfigDao dao = new ConfigDao();
        getProperties().values().forEach((config) -> {dao.save(config);});
    }
    
    public void saveProperties(String key){
        new ConfigDao().save(getProperties().get(key));
    }

    /**
     * @return the properties
     */
    public Map<String, Config> getProperties() {
        return properties;
    }
}
