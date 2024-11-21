/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.receiver;

import com.attech.asd.daemon.AppContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Tang Hai Anh
 */
public class InformationManager implements Serializable {

    private static InformationManager instance;
    private HashMap<Integer, InformationItem> info;
    private String currentDate;
    private final SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");

    public InformationManager() {
        info = new HashMap<>();
        dateFormatGmt.setTimeZone(AppContext.utc);
    }
    
    public boolean isReset() {
        String date = dateFormatGmt.format(new Date());
        if (date.equalsIgnoreCase(currentDate)) {
            return false;
        }

        this.currentDate = date;
        return true;
    }

    public synchronized void put(Integer key, InformationItem value) {
        this.getInfo().put(key, value);
    }

    public synchronized InformationItem get(int key) {
        InformationItem item = this.getInfo().get(key);
        if (item == null) {
            item = new InformationItem(key);
        }
        this.getInfo().put(key, item);
        return item;
    }

    public synchronized void update(int no, int count, int transfer) {
        InformationItem item = get(no);
        item.setLastReceived(System.currentTimeMillis());
        item.setReceived(count);
        item.setTransfer(transfer);
        // Neu sang ngay moi thi reset bo dem
        if (isReset()){
            info.values().forEach((element) -> {element.reset();});
        }
    }
    
    public synchronized void remove(int no) {
        info.remove(no);
    }

    @Override
    public String toString() {
        String s = "";
        if (this.size() > 0) {
            for (int i = 0; i < size(); i++) {
                s += String.format("InformationItem#%d: %s\n", i, getByIndex(i));
            }

        }
        return s;

    }

    public synchronized void clear() {
        this.getInfo().clear();
    }

    public synchronized static InformationManager instance() {
        if (instance == null) {
            instance = new InformationManager();
        }
        return instance;
    }

    public synchronized void put(InformationItem value) {
        put(value.getNo(), value);
    }

    public synchronized int size() {
        return getInfo().size();
    }

    public synchronized InformationItem getByIndex(int index) {
        if (index < getInfo().size() && index >= 0) {
            return (InformationItem) getInfo().values().toArray()[index];
        } else {
            return null;
        }
    }

    /**
     * @return the info
     */
    public HashMap<Integer, InformationItem> getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(HashMap<Integer, InformationItem> info) {
        this.info = info;
    }
}
