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
public class TargetReportDescriptorTest {
    
    public TargetReportDescriptorTest() {
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
    public void test(){
        test01();
    }
    
    private void test01() {
        byte[] bytes = new byte[]{
            (byte) 0x01, (byte) 0x00, (byte) 0xB2, (byte) 0xF9,
            (byte) 0x01, (byte) 0x02, (byte) 0x94, (byte) 0x01,
            (byte) 0x20, (byte) 0x6B, (byte) 0x6A, (byte) 0x76,
            (byte) 0xD2, (byte) 0x0C, (byte) 0x45};
        int index = 8;
        TargetReportDescriptor descriptor = new TargetReportDescriptor();
        int count = TargetReportDescriptor.extract(bytes, index, descriptor);
        index += count;
        
        int expIndex = 9;
        TargetReportDescriptor expDescriptor = new TargetReportDescriptor();
        expDescriptor.setType(2);
        assertEquals(expIndex, index);
        assertEquals(expDescriptor.getAntennaNo(), descriptor.getAntennaNo());
        assertEquals(expDescriptor.getStatus(), descriptor.getStatus());
        assertEquals(expDescriptor.getType(), descriptor.getType());
        assertEquals(expDescriptor.isFixedTransponder(), descriptor.isFixedTransponder());
        assertEquals(expDescriptor.isMilitaryEmergency(), descriptor.isMilitaryEmergency());
        assertEquals(expDescriptor.isMilitaryId(), descriptor.isMilitaryId());
        assertEquals(expDescriptor.isSimulate(), descriptor.isSimulate());
        assertEquals(expDescriptor.isSpecialPostionId(), descriptor.isSpecialPostionId());
        assertEquals(expDescriptor.isTested(), descriptor.isTested());
        assertEquals(expDescriptor.isTrack(), descriptor.isTrack());
    }
    
}
