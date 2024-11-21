/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

/**
 *
 * @author anbk
 */
public class Test {
    public static void main(String [] args) {
        
        double value = Math.pow(2, 10);
        
        
        System.out.println("Value:  " + value);
        // int i = getInt((byte)0x0F, (byte)0xFF);
        // System.out.println(i);
        
        // System.out.println(Integer.toOctalString(i));
    }
    
    private static int getInt(byte byte1, byte byte2) {
        System.out.println("Byte1: " + Integer.toBinaryString(byte1));
        System.out.println("Byte2: " + Integer.toBinaryString(byte2));
        // return ((byte1 & 0x0F) << 8) | (byte2 | 0XFF);
        return ((byte1 & 0x0F) << 8 ) | (byte2 & 0xFF);
    }
    
    
    private static void test() {
        Test test = new Test();
        test.get((byte) 0xAA, 0);
        test.get((byte) 0xAA, 1);
        test.get((byte) 0xAA, 2);
        test.get((byte) 0xAA, 3);
        test.get((byte) 0xAA, 4);
        test.get((byte) 0xAA, 5);
        test.get((byte) 0xAA, 6);
        test.get((byte) 0xAA, 7);
        
        System.out.println("---------------------------------");
        test.testConvertToInt(1);
        test.testConvertToInt(2);
        test.testConvertToInt(3);
        test.testConvertToInt(4);
        test.testConvertToInt(5);
        test.testConvertToInt(6);
        test.testConvertToInt(7);
        test.testConvertToInt(8);
        test.testConvertToInt(9);
        test.testConvertToInt(10);
        
        System.out.println("---------------------------------");
        test.testConvertToInt2(1);
        test.testConvertToInt2(2);
        test.testConvertToInt2(3);
        test.testConvertToInt2(4);
        test.testConvertToInt2(5);
        test.testConvertToInt2(6);
        test.testConvertToInt2(7);
        test.testConvertToInt2(8);
        
        System.out.println("---------------------------------");
        test.testget((byte) 0xAA, 0, 1);
        test.testget((byte) 0xAA, 0, 2);
        test.testget((byte) 0xAA, 0, 3);
        test.testget((byte) 0xAA, 0, 4);
        test.testget((byte) 0xAA, 2, 6);
    }
    
    public void get(byte b, int index) {
        System.out.println(index + ": " + (b >> index & 0x01));
    }
    
//    public void get(byte b, int index, int length) {
//        System.out.println(index + ": " + (b >> index & 0x01));
//    }
    
    private int convertToByte(int i) {
        if (i < 0) return 0;
        if (i == 1) return (2*(i-1)) + 1;
        return 2 * convertToByte(i-1) + 1;
    }
    
    public void testConvertToInt(int length) {
        System.out.println(length + ": " + convertToByte(length));
    }
    
    public void testConvertToInt2(int length) {
        System.out.println("Length: " + length + ": " + (0xFF >> (8-length)));
    }
    
    private void testget(byte b, int index, int length) {
        int a = get ((byte) 0xAA, 0, 1);
        System.out.println(index +  "-->" + (index + length) + ": " + get(b, index, length));
    }
   
    private int get(byte b, int index, int length) {
        int len = lengthToByte(length);
        return b >> index & len;
    }
    
    private static int lengthToByte(int length) {
        if (length < 0 || length > 8) return 0;
        return 0xFF >> (8-length);
    }
}
