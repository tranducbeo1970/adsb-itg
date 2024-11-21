/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.record;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class PackageBuilder {

    private int size = 3;
    private final List<byte[]> list;
    
    public PackageBuilder() {
        list = new ArrayList<>();
    }

    public void append(byte[] bytes) {
        list.add(bytes);
        size += (bytes.length - 3);
    }
    
    /**
     * Append all byte in buffer to array then clear the buffer to store new data
     * @return 
     */
    public byte[] toByteArray() {
        try {
            if (list.isEmpty()) return null;
            byte[] result = new byte[size];
            result[0] = (byte) 0x15;
            result[1] = (byte) (size >> 8 & 0xFF);
            result[2] = (byte) (size & 0xFF);
            int index = 3;
            for (int i = 0; i < list.size(); i++) {
                System.arraycopy(list.get(i), 3, result, index, list.get(i).length - 3);
                index += (list.get(i).length - 3);
            }
            return result;
        } finally {
            this.clear();
        }
    }

    private void clear() {
        size = 3;
        list.clear();
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
//    public int length () {
//        return this.list.size();
//    }
            
}
