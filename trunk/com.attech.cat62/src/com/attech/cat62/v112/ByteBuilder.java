/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

import java.util.BitSet;
import java.util.List;

/**
 *
 * @author andh
 */
public class ByteBuilder {
    
    protected final BitSet header;
    protected final byte cate = (byte) 0x3E;
    protected final byte [][] contents;
    protected int length;
    
    public ByteBuilder() {
        /*
        header = new BitSet();
        contents = new byte[35][];
        length = 0;
        */
        this(35);
    }
    
    public ByteBuilder(int count) {
        header = new BitSet();
        contents = new byte[count][];
        length = 0;
    }

    public void set(int index, byte[] bs) {
        setHeader(index);
        contents[index] = bs;
        length += bs.length;
    }
    
    public void set(int index, Byte[] bs) {
        setHeader(index);
        byte [] pbs = new byte[bs.length];
        for (int i = 0; i < bs.length; i++) pbs[i] = bs[i];
        contents[index] = pbs;
        length += bs.length;
    }
    
    public void set(int index, List<Byte> bs) {
        setHeader(index);
        final int len = bs.size();
        byte [] pbs = new byte[len];
        for (int i = 0; i < len; i++) pbs[i] = bs.get(i);
        contents[index] = pbs;
        length += len;
    }

    private void setHeader(int index) {
        header.set((7 - index) + (index / 7) * 15);
    }
    
    /**
     *
     * @return
     */
    public byte[] toByteArray() {
        final int headerLength = header.length() - 8;
        int index = 0;
        while (index < headerLength) {
            header.set(index);
            index += 8;
        }

        byte[] headbyte = header.toByteArray();
        int total = length + headbyte.length + 3;
        byte [] content = new byte[total];
        content[0] = cate;
        content[1] = (byte) (total >> 8 & 0xFF);
        content[2] = (byte) (total & 0xFF);
        System.arraycopy(headbyte, 0, content, 3, headbyte.length);
        int cIndex = 3 + headbyte.length;
        for (byte[] b : contents) {
            if (b == null || b.length == 0) continue;
            System.arraycopy(b, 0, content, cIndex, b.length);
            cIndex += b.length;
        }
        return content;
    }
    
    public byte[] getContentByte() {
        final int headerLength = header.length() - 8;
        int index = 0;
        while (index < headerLength) {
            header.set(index);
            index += 8;
        }

        byte[] headbyte = header.toByteArray();
        byte [] content = new byte[length + headbyte.length];
        System.arraycopy(headbyte, 0, content, 0, headbyte.length);
        int cIndex = headbyte.length;
        for (byte[] b : contents) {
            if (b == null || b.length == 0) continue;
            System.arraycopy(b, 0, content, cIndex, b.length);
            cIndex += b.length;
        }
        return content;
    }
}
