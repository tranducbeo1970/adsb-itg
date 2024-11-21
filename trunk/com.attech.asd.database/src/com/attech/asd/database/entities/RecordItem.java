package com.attech.asd.database.entities;

import java.io.Serializable;

public class RecordItem implements Serializable{
    private long time;
    private byte[] bytes;
    
    public RecordItem(long time, byte [] bytes) {
        this.time = time;
        this.bytes = bytes;
    }
    
    @Override
    public String toString() {
        return " time: " + time  + " len: " + bytes.length; 
    }

    public long getTime() { return time; }
    public void setTime(long time) { this.time = time; }

    public byte[] getBytes() { return bytes; }
    public void setBytes(byte[] bytes) { this.bytes = bytes; }

}
