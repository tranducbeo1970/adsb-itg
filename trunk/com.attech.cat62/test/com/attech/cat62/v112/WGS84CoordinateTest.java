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

/**
 *
 * @author andh
 */
public class WGS84CoordinateTest {
    
    public WGS84CoordinateTest() {
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
     * Test of decode method, of class WGS84Coordinate.
     */
    @Test
    public void testDecode() {
        
        WGS84Coordinate wgs84Coord = new WGS84Coordinate(29.259597349104734 , 107.70803442025193);
        byte[] bytes = wgs84Coord.encode();

        WGS84Coordinate result = new WGS84Coordinate();
        WGS84Coordinate.decode(bytes, 0, result);

        double delta = 180 / Math.pow(2, 25);
        Assert.assertEquals(wgs84Coord.lat, result.lat, delta);
        Assert.assertEquals(wgs84Coord.lng, result.lng, delta);
    }

    
}
