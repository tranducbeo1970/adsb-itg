/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.nio.ByteBuffer;

/**
 *
 * @author root
 */
public class TestByteShiff {
    public double getInteger(byte[] bytes) {
        byte[] latBytes = new byte[]{bytes[0], bytes[1], bytes[2], bytes[3]};
        ByteBuffer bytBuffer = ByteBuffer.wrap(latBytes);
        int value = bytBuffer.getInt();
        return (value * 0.00000016764);
    }
    
    public double getInteger2(byte[] bytes) {
        int value = (bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF) ;
        return (value * 0.00000016764);
    }
    
    public static void main(String [] args )
    {
        TestByteShiff object = new TestByteShiff();
        byte[] bytes = new byte[]{(byte) 0x20, (byte) 0x4F, (byte) 0x44, (byte) 0XFF};
        for (int i = 0; i< 99999; i++) {
            // object.getInteger(bytes);
            // object.getInteger2(bytes);
            System.out.println("----" + i + "-------------------------------------------");
            System.out.println("[1]" + object.getInteger(bytes));
            System.out.println("[2]" + object.getInteger2(bytes));
        }
    }
}
