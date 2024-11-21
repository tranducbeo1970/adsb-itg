/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

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
public class ModeCTest {
    
    public ModeCTest() {
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
     * Test of extract method, of class ModeC.
     */
    @Test
    public void testExtract() {
         byte[] input = new byte [] { 
            (byte) 0x01, (byte) 0x00, (byte) 0xB2, (byte) 0xF9, (byte) 0x01,
            (byte) 0x02, (byte) 0x94, (byte) 0x01, (byte) 0x20, (byte) 0x6B,
            (byte) 0x6A, (byte) 0x76, (byte) 0xD2, (byte) 0x0C, (byte) 0x45,
            (byte) 0x06, (byte) 0x18, (byte) 0x01, (byte) 0x2A, (byte) 0xFA,
            (byte) 0x7A, (byte) 0x19, (byte) 0x55, (byte) 0x6B, (byte) 0x45,
            (byte) 0x04, (byte) 0x88, (byte) 0x09, (byte) 0xA1, (byte) 0xFA,
            (byte) 0xC8, (byte) 0x19, (byte) 0x63, (byte) 0x2C, (byte) 0x45,
            (byte) 0xAD, (byte) 0x9C, (byte) 0x40, (byte) 0x0E, (byte) 0xD4};
        int index = 15;
        ModeC modeC = new ModeC();
        index += ModeC.extract(input, index, modeC);
        
        int expIndex = 17;
        assertEquals(expIndex, index);
        assertEquals(39000, modeC.getValue());
        assertEquals(false, modeC.isNotValidated());
        assertEquals(false, modeC.isGarbedCode());
    }
}
