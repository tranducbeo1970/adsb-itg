/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author andh
 */
public class ByterTest {
    
    public ByterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of extract method, of class Byter.
     */
    @Test
    public void testConvertComplementByte() {
        testConvertComplementByte(123);
        testConvertComplementByte(-123);
    }
    
    @Test
    public void testConvertOct() {
        int dec = 10;
        int oct = Byter.convertDec2Oct(dec);
        int rsl = Byter.convertOct2Dec(oct);
        Assert.assertEquals(dec, rsl);
        
        
        StringBuilder builder = new StringBuilder();
        System.out.println("BEFORE: " + builder);
        setString(builder);
        System.out.println("AFTER: " + builder);
    }
    
    private void setString(StringBuilder buider) {
        buider.append("HELLLLO");
    }
    
    private void testConvertComplementByte(int val) {
        System.out.println("Test value: " + val);
        byte[] bytes = Byter.getComplementNumberInByte(val);
        int result = Byter.getComplementNumber(bytes);
        print(bytes);
        Assert.assertEquals(val, result);
    }

    private void print(byte[] bytes) {
        StringBuilder builder = new StringBuilder("Mehod #2: ");
        for (byte b : bytes) {
            builder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).append(' ');
        }
        System.out.println(builder.toString());
    }
}
