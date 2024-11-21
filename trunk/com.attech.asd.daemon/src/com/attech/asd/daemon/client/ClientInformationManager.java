/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.client;

import com.attech.asd.daemon.AppContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author AnhTH
 */
public class ClientInformationManager implements Serializable {
    
    private static ClientInformationManager instance;
    private Map<Integer, ClientInformationItem> info;
    private String currentDate;
    private final SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");
    
    public ClientInformationManager() {
        info = new HashMap<>();
        dateFormatGmt.setTimeZone(AppContext.utc);
    }
    
    private boolean isReset() {
        String date = dateFormatGmt.format(new Date());
        if (date.equalsIgnoreCase(currentDate)) {
            return false;
        }

        this.currentDate = date;
        return true;
    }
    
    public synchronized void update(int no, int count, boolean active) {
        ClientInformationItem item = info.get(no);
        if (item == null) {
            item = new ClientInformationItem();
            item.setNo(no);
            info.put(no, item);
        }
        
        item.setCount(count);
        item.setActive(active);
        // Neu sang ngay moi thi reset bo dem
        if (isReset()){
            info.values().forEach((element) -> {element.reset();});
        }
    }
    
    public synchronized ClientInformationItem get(int no) {
        return this.getInfo().get(no);
    }
    
    public synchronized void remove(int no) {
        this.info.remove(no);
    }
    
    public static ClientInformationManager getInstance() {
        if (instance == null) instance = new ClientInformationManager();
        return instance;
    }

    /**
     * @return the info
     */
    public Map<Integer, ClientInformationItem> getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(Map<Integer, ClientInformationItem> info) {
        this.info = info;
    }
}
