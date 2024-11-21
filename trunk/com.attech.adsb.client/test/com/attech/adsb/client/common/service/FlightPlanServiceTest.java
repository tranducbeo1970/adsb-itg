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
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author hong
 */
public class FlightPlanServiceTest {

    public FlightPlanServiceTest() {
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

    @Test
    public void testAssumCase01() {
	System.out.println("Test assumption case 01");
	Target target = Target.load("test_data/control/AXM624.xml", Target.class);
	FlightControlService instance = new FlightControlService("SECTOR01");
	instance.assum(target);
        Assert.assertEquals("SECTOR01", target.getController());
        Assert.assertEquals(target.getTrackStatus(), TrackStatus.CONTROLED);
        
    }
    
    @Test
    public void testTransferCase01() {
	System.out.println("Test transfer case 01");
	Target target = Target.load("test_data/control/AXM624.xml", Target.class);
	FlightControlService instance = new FlightControlService("SECTOR01");
	instance.transfer(target, "SECTOR02");
        Assert.assertEquals("SECTOR01", target.getController());
        Assert.assertEquals(target.getTrackStatus(), TrackStatus.CONTROLED);
        
    }
    
    @Test
    public void testCancelCase01() {
	System.out.println("Test transfer case 01");
	Target target = Target.load("test_data/control/AXM624.xml", Target.class);
	FlightControlService instance = new FlightControlService("SECTOR01");
	instance.cancel(target);
        Assert.assertEquals("SECTOR01", target.getController());
        Assert.assertEquals(target.getTrackStatus(), TrackStatus.CONTROLED);
        
    }

}
