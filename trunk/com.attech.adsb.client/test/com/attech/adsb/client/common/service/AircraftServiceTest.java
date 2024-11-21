/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common.service;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.TrackStatus;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.dto.Aircrafts;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hong
 */
public class AircraftServiceTest {
    
    public AircraftServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        DOMConfigurator.configure(Common.CFG_LOG);
	Configuration.instance().load();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of update method, of class AircraftService.
     */
    @Test
    public void testUpdate() {
        System.out.println("Test update case 01");
        Target target = Target.load("test_data/control/AXM624.xml", Target.class);
        AircraftService instance = new AircraftService();
        instance.update(target);
        assertNotNull(target.getInfo());
        Aircrafts info = target.getInfo();
        assertEquals(info.getModel(), "A320");
        assertEquals(info.getRegistrationNo(), "9M-AQB");
        assertEquals(info.getAircraftType(), "Airbus A320-216");
        assertEquals(info.getOperator(), "AirAsia");
    }

}
