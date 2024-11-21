/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class MessageBuilder {

    private int size = 3;
    public boolean isBlock;
    public List<byte[]> list = new ArrayList<>();

    public void append(byte[] bytes) {
        if (isBlock) return;
        list.add(bytes);
        size += (bytes.length - 3);
    }
    
    public byte[] toByteArray() {
        this.isBlock = true;
        try {
            byte[] result = new byte[size];
            result[0] = (byte) 0x15;
            result[1] = (byte) (size >> 8 & 0xFF);
            result[2] = (byte) (size & 0xFF);
            int index = 3;
            // System.out.print(">> BUILD size " + size);
            for (int i = 0; i < list.size(); i++) {
                // System.out.print(" index " + index + " length " + (list.get(i).length - 3));
                System.arraycopy(list.get(i), 3, result, index, list.get(i).length - 3);
                index += (list.get(i).length - 3);
            }
            // System.out.println("  ");
            return result;
        } finally {
            this.clear();
            this.isBlock = false;
        }
    }

    public void clear() {
        size = 3;
        list.clear();
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public int length () {
        return this.list.size();
    }
            
}
