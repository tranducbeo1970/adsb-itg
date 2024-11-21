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
public class AircraftDerivedDataTest {
    
    public AircraftDerivedDataTest() {
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
     * Test of decode method, of class AircraftDerivedData.
     */
    @Test
    public void testDecode() {
        AircraftDerivedData instance = new AircraftDerivedData();
        instance.setAddress(77580);
        byte[] result = instance.encode();
        // 10000000 00000001 00101111 00001100
        byte[] expected = new byte[]{(byte) 0x80, (byte) 0x01, (byte) 0x2F, (byte) 0x0C};
        Assert.assertArrayEquals(expected, result);

        instance = new AircraftDerivedData();
        instance.setAddress(77580);
        instance.setTargetID("ABC123");
        instance.setMagneticHeading(100.0);
        instance.setTrueAirSpeed(345);
        instance.setTrackAngle(220.7);
        instance.setGroundSpeed(267.0);
        
        result = instance.encode();
        print(result);
        AircraftDerivedData resultObj = new AircraftDerivedData();
        AircraftDerivedData.decode(result, 0, resultObj);
        Assert.assertEquals(instance.getAddress(), resultObj.getAddress());
        Assert.assertEquals(instance.getTargetID(), resultObj.getTargetID());
        Assert.assertEquals(instance.getMagneticHeading(), resultObj.getMagneticHeading(), 0.0055);
        Assert.assertEquals(instance.getTrueAirSpeed(), resultObj.getTrueAirSpeed(), 0.0055);
        Assert.assertEquals(instance.getTrackAngle(), resultObj.getTrackAngle(), 0.0054931640625);
        Assert.assertEquals(instance.getGroundSpeed(), resultObj.getGroundSpeed(), 0.22);
        
    }
    
    private void print(byte[] bytes) {
        StringBuilder builder = new StringBuilder("");
        for (byte b : bytes) {
            builder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).append(' ');
        }
        System.out.println(builder.toString());
    }

    
}
