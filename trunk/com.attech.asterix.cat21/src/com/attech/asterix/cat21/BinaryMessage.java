/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21;

import java.nio.ByteBuffer;

/**
 *
 * @author andh
 */
public class BinaryMessage {
    private byte[][] bytes ;
    private boolean[] header;
    public BinaryMessage(byte[][] bytes, boolean[] header) {
        this.bytes = bytes;
        this.header = header;
    }
    
    public void merge(BinaryMessage message) {
        int length2 = message.getHeader().length;
        int length1 = this.header.length;
        byte[][] tempData1, tempData2;
        boolean [] tempHeader1, tempHeader2;
        
        if (length1 >= length2) {
            tempData1 = this.bytes;
            tempHeader1 = this.header;
            tempData2 = message.getBytes();
            tempHeader2 = message.getHeader();
        } else {
            tempData2 = this.bytes;
            tempHeader2 = this.header;
            tempData1 = message.getBytes();
            tempHeader1 = message.getHeader();
        }
        
        for (int i = 0; i < tempHeader1.length; i++) {
            if (i > tempHeader2.length) break;
            if (tempHeader1[i]) continue;
            if (!tempHeader2[i]) continue;
            tempHeader1[i] = true;
            tempData1[i] = tempData2[i];
        }
        
        this.header = tempHeader1;
        this.bytes = tempData1;
    }
    
    public byte[] toByteArray() {
       
        byte [] headByte = buildHeader();
        int counting = headByte.length;
        
        ByteBuffer byteBuffer = ByteBuffer.wrap(buildHeader());
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == null || bytes[i].length == 0) continue;
            byteBuffer.put(bytes[i]);
            counting += bytes[i].length;
        }
        
        counting +=3;
        byte[] bts = new byte[]{(byte) 21, (byte) (counting >> 8 & 0xFF), (byte) (counting & 0xFF)};
        byteBuffer.put(bts, 0, 3);
        return byteBuffer.array();
    }
    
    private byte[] buildHeader() {
        int length = this.header.length / 8;
        byte[] headByte = new byte[length];
        for (int i = 0; i < length; i++) {
            int j = i * 8;
            boolean[] bools = new boolean[]{this.header[j],
                                            this.header[j + 1],
                                            this.header[j + 2],
                                            this.header[j + 3],
                                            this.header[j + 4],
                                            this.header[j + 5],
                                            this.header[j + 6],
                                            this.header[j + 7]};
            headByte[i] = getByte(bools);
        }
        return headByte;
    }
    
    private byte getByte(boolean [] bools) {
        int i = (bools[0] ? 128 : 0) + (bools[1] ? 64 : 0) + (bools[2] ? 32 : 0) + (bools[3] ? 16 : 0) + (bools[4] ? 8 : 0) + (bools[5] ? 4 : 0) + (bools[6] ? 2 : 0) + (bools[7] ? 1 : 0);
        return (byte) i;
    }

    /**
     * @return the bytes
     */
    public byte[][] getBytes() {
        return bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setBytes(byte[][] bytes) {
        this.bytes = bytes;
    }

    /**
     * @return the header
     */
    public boolean[] getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(boolean[] header) {
        this.header = header;
    }
}
