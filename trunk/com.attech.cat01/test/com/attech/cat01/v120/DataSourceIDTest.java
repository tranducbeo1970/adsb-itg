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
public class DataSourceIDTest {
    
    public DataSourceIDTest() {
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
     * Test of extract method, of class DataSourceID.
     */
    @Test
    public void testExtract() {
       test1();
    }
    
    private void test1() {
        byte[] bytes = new byte[]{(byte) 0x01, (byte) 0x00, (byte) 0xB2, (byte) 0xF9,
            (byte) 0x01, (byte) 0x02, (byte) 0x94, (byte) 0x01,
            (byte) 0x20, (byte) 0x6B, (byte) 0x6A, (byte) 0x76,
            (byte) 0xD2, (byte) 0x0C, (byte) 0x45};
        int index = 6;

        DataSourceID source = new DataSourceID();
        int count = DataSourceID.extract(bytes, index, source);
        index += count;

        int expIndex = 8;
        int expSAC = 148;
        int expSIC = 1;
        assertEquals(index, expIndex);
        assertEquals(source.getSac(), expSAC);
        assertEquals(source.getSic(), expSIC);
        
     }
    
}
