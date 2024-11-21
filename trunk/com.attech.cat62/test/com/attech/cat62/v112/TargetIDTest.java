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
public class TargetIDTest {
    
    public TargetIDTest() {
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
     * Test of decode method, of class TargetID.
     */
    @Test
    public void testDecode() {
        int max = (int) Math.pow(2, 6);
        for (int i = 0; i < max; i++) {
            char c = TargetID.getChar(i);
            System.out.printf("%s %d %d %n", c,(int) c, i );
        }
       
    }

    @Test
    public void testGetCharCode() {
        TargetID targetID = new TargetID(1, "ABCD123");
        Assert.assertEquals(1, targetID.getCharCode(0));
        Assert.assertEquals(2, targetID.getCharCode(1));
        Assert.assertEquals(3, targetID.getCharCode(2));
        Assert.assertEquals(4, targetID.getCharCode(3));
        Assert.assertEquals(32, targetID.getCharCode(7));
    }
    
    @Test
    public void testEncode() {
        TargetID expected = new TargetID(1, "ABCD123 ");
        byte [] bytes = expected.encode();
        print(bytes);
        TargetID result = new TargetID();
        TargetID.decode(bytes, 0, result);
        Assert.assertEquals(result.mode, expected.mode);
        Assert.assertEquals(result.value, expected.value);
    }
    
    private void print(byte[] bytes) {
        StringBuilder builder = new StringBuilder("");
        for (byte b : bytes) {
            builder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')).append(' ');
        }
        System.out.println(builder.toString());
    }
}
