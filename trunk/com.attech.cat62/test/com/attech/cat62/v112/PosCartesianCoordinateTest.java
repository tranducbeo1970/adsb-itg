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
public class PosCartesianCoordinateTest {
    
    public PosCartesianCoordinateTest() {
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
     * Test of toString method, of class PosCartesianCoordinate.
     */
    @Test
    public void testToString() {
        
        testComplementNumber(1);
        testComplementNumber(-1);
        testComplementNumber(101);
        testComplementNumber(-101);
                
        Coordinate test1 = new Coordinate(2,4);
        test(test1);
        
        test1 = new Coordinate(101,103);
        test(test1);
        
        test1 = new Coordinate(-101,101);
        test(test1);
        
        test1 = new Coordinate(101,-101);
        test(test1);
        
        test1 = new Coordinate(-101,-101);
        test(test1);
    }
    
    
    private void test(Coordinate expected) {
        byte [] bs = PosCartesianCoordinate.encode(expected);
        print(bs);
        Coordinate result = new Coordinate();
        PosCartesianCoordinate.decode(bs, 0, result);
        Assert.assertEquals(expected.x, result.x, 0.5);
        Assert.assertEquals(expected.y, result.y, 0.5);
    }
    
    private void testComplementNumber(int val) {
        byte [] bs = PosCartesianCoordinate.getComplementNumberInByte(val);
        System.out.println("Number: " + val);
        print(bs);
        int result = PosCartesianCoordinate.getComplementNumber(bs[0], bs[1], bs[2]);
        Assert.assertEquals(result, val);
    }
    
    private void print(byte[] bytes) {
        StringBuilder builder = new StringBuilder("");
        for (byte b : bytes) {
            builder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).append(' ');
        }
        System.out.println(builder.toString());
    }
}
