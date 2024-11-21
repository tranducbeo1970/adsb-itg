/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

/**
 *
 * @author root
 */
public class BroadCastContentItem {
    private byte [] content;
    
    public BroadCastContentItem(byte [] bytes) {
        this.content = bytes;
    }
    
    public void add(byte[] bytes) {
        int length = content.length + bytes.length-3;
        byte[] buffer = buildHeader(length, content[0]);
        System.arraycopy(content, 3, buffer, 3, content.length-3);
        System.arraycopy(bytes, 3, buffer, content.length, bytes.length-3);
        this.content = buffer;
    }

    public byte [] buildHeader(int length, byte cat) {
       byte[] buffer = new byte[length];
       buffer[0] = cat;
       buffer[1] = (byte) (length >> 8 & 0xFF);
       buffer[2] = (byte) (length & 0xFF);
       return buffer;
    }
    
    public byte[] getByte() {
        return this.content;
    }
}
