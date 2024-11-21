/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

import java.nio.ByteBuffer;
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
public class PolarCoordinateTest {
    
    public PolarCoordinateTest() {
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
     * Test of getRho method, of class PolarCoordinate.
     */
    @Test
    public void testGetRho() {
        test01();
    }
    
    private void test01() {
        byte[] input = new byte [] {(byte) 0x01, (byte) 0x00, (byte) 0xB2, (byte) 0xF9, 
                                    (byte) 0x01, (byte) 0x02, (byte) 0x94, (byte) 0x01, 
                                    (byte) 0x20, (byte) 0x6B, (byte) 0x6A, (byte) 0x76, 
                                    (byte) 0xD2, (byte) 0x0C, (byte)0x45};
        int index = 9;
        PolarCoordinate polar = new PolarCoordinate();
        index += PolarCoordinate.extract(input, index, polar);
        
        
        int expIndex = 13;
        assertEquals(expIndex, index);
        int ivalue = ByteBuffer.wrap(new byte[] { 0x00, 0x00, (byte)0x6B, (byte)0x6A }).getInt();
        System.out.println("value RHO: " + ivalue);
        double expRHO = (double)  ivalue/ 128;
        
        ivalue = ByteBuffer.wrap(new byte[] { 0x00, 0x00, (byte)0x76, (byte)0xD2 }).getInt();
        System.out.println("value THETA: " + ivalue);
        double expTheta = (double) ivalue * 0.0055;
        assertEquals(expRHO, polar.getRho(), (double)0.00001);
        assertEquals(expTheta, polar.getTheta(), (double)0.00001);
        
    }
}
