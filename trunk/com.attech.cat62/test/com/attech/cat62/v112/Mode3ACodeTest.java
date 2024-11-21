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
import static org.junit.Assert.*;

/**
 *
 * @author andh
 */
public class Mode3ACodeTest {
    
    public Mode3ACodeTest() {
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
     * Test of decode method, of class Mode3ACode.
     */
    @Test
    public void testEncode() {
        Mode3ACode mode3A = new Mode3ACode();
        mode3A.setChanged(true);
        mode3A.setValue(1);
        // 00100000 00000001
        byte [] result = mode3A.encode();
        byte [] expected = new byte [] {(byte) 0x20, (byte) 0x01};
        Assert.assertArrayEquals(expected, result);
        
        mode3A = new Mode3ACode();
        mode3A.setChanged(true);
        mode3A.setValue(7777);
        // 00101111 11111111
        result = mode3A.encode();
        expected = new byte [] {(byte) 0x2F, (byte) 0xFF};
        Assert.assertArrayEquals(expected, result);
        
         mode3A.setChanged(false);
        mode3A.setValue(7777);
        // 00001111 11111111
        result = mode3A.encode();
        expected = new byte [] {(byte) 0x0F, (byte) 0xFF};
        Assert.assertArrayEquals(expected, result);
    }
    
}
