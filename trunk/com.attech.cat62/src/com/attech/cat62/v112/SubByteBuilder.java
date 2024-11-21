/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class SubByteBuilder extends ByteBuilder {
    @Override
    public byte[] toByteArray() {
        final int headerLength = header.length() - 8;
        int index = 0;
        while (index < headerLength) {
            header.set(index);
            index += 8;
        }

        byte[] headbyte = header.toByteArray();
        int total = length + headbyte.length;
        byte [] content = new byte[total];
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
