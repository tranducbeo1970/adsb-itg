/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andh
 */
public class FlightPlanTest {
    
    public FlightPlanTest() {
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
     * Test of extract method, of class FlightPlan.
     */
    @Test
    public void testExtract() {
        /*
        System.out.println("extract");
        byte[] bytes = null;
        int index = 0;
        FlightPlan code = null;
        int expResult = 0;
        int result = FlightPlan.decode(bytes, index, code);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of encode method, of class FlightPlan.
     */
    @Test
    public void testEncode() {
        testSF1();
        testSF2();
    }

    private void testSF1() {
        FlightPlan plan = new FlightPlan();
        plan.setSac(150);
        plan.setSic(90);
        plan.setIdentificationTagAvailable(true);
        byte [] result = plan.encode();
        
        // 10000000 10010110 01011010 
        byte[] expected = new byte [] {(byte) 0x80, (byte) 0x96, (byte) 0x5A};
        assertArrayEquals(expected, result);
    }
    
    private void testSF2() {
        FlightPlan plan = new FlightPlan();
        plan.setCallSign("ABC123");
        plan.setCallsignAvailable(true);
        byte [] result = plan.encode();
         // 01000000 ‭01000001‬ ‭01000010‬ ‭01000011‬
        byte[] expected = new byte [] {(byte) 0x40, (byte) 0x41, (byte) 0x42, (byte) 0x43, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x20 };
        assertArrayEquals(expected, result);
    }
}
