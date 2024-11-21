package com.attech.asd.daemon;

import java.io.Serializable;

public class RecordItem implements Serializable{
    private long time;
    private int category;
    private float version;
    private int address;
    private byte[] bytes;
    
    public RecordItem(long time, byte [] bytes) {
        this.time = time;
        this.bytes = bytes;
    }
    
    public RecordItem(long time, int category, float version, int address, byte [] bytes) {
        this.time = time; 
        this.address = address;
        this.bytes = bytes;
        this.category = category;
        this.version = version;
    }
    
    @Override
    public String toString() {
        return " time: " + time + " cat: " + category + " ver: " + version + " add: " + address + " len: " + bytes.length; 
    }

    public long getTime() { return time; }
    public void setTime(long time) { this.time = time; }

    public int getAddress() { return address; }
    public void setAddress(int address) { this.address = address; }

    public byte[] getBytes() { return bytes; }
    public void setBytes(byte[] bytes) { this.bytes = bytes; }

    public int getCategory() { return category; }
    public void setCategory(int category) { this.category = category; }

    public float getVersion() { return version; }
    public void setVersion(float version) { this.version = version; }
}
