/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dhan
 */
public class Decryptor01Test {
    
    public Decryptor01Test() {
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

    @Test
    public void testExtractHeader() {
        test1();
    }
    
    private void test1() {
        byte[] input = new byte [] {(byte) 0x01, (byte) 0x00, (byte) 0xB2, (byte) 0xF9, 
                                    (byte) 0x01, (byte) 0x02, (byte) 0x94, (byte) 0x01, 
                                    (byte) 0x20, (byte) 0x6B, (byte) 0x6A, (byte) 0x76, 
                                    (byte) 0xD2, (byte) 0x0C, (byte)0x45};
        int index = 3;
        
        boolean [] header = new boolean[21];
        int count = Cat01Decoder.decodeHeader(input, index, header);
        index+=count;
        
        int expectedCount = 3;
        boolean [] expectedHeader = new boolean[21];
        expectedHeader[0] = true; // DataSource
        expectedHeader[1] = true; // Target descriptor
        expectedHeader[2] = true; // Polar coordinate
        expectedHeader[3] = true; // Mode 3A
        expectedHeader[4] = true; // Mode C 
        expectedHeader[20] = true; // 
        
        assertEquals(expectedCount, count);
        assertEquals(index, 6);
        for (int i = 0; i< 21; i++) {
            assertEquals(expectedHeader[i], header[i]);
        }
    }
}
