/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.TrackStatus;
import com.attech.adsb.client.common.XmlSerializer;
import com.attech.adsb.client.common.enums.WarnType;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.FilterCondition;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.TargetLabelDisplay;
import com.jogamp.opengl.GL2;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hong
 */
public class TargetItemTest {
    
    public TargetItemTest() {
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
     * Test of setWarning method, of class TargetItem.
     */
    @Test
    public void testFilterCondition() {
        Target target = Target.load("filter/case01/AXM536.xml", Target.class);
        TargetItem item = new TargetItem(target);
        FilterCondition condition = XmlSerializer.load("filter/case01/filter.xml", FilterCondition.class);
        item.setFilterCondition(condition);
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), false);

        condition.setCallSign("AXM");
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), true);
        
        condition.setCallSign("536");
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), true);
        
        condition.setCallSign("XM5");
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), true);

        condition.setCallSign("");
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), true);

        condition.setCallSign(null);
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), true);

        condition.setAltitudeHigh(400);
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), true);

        condition.setAltitudeHigh(200);
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), false);

        condition.setAltitudeHigh(null);
        condition.setAltitudeLow(0);
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), true);

        condition.setAltitudeHigh(null);
        condition.setAltitudeLow(250);
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), false);

        condition.setAltitudeHigh(450);
        condition.setAltitudeLow(250);
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), false);

        condition.setAltitudeHigh(200);
        condition.setAltitudeLow(0);
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), false);

        condition.setAltitudeHigh(300);
        condition.setAltitudeLow(0);
        condition.setCallSign("XM5");
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), true);

        condition.setAltitudeHigh(300);
        condition.setAltitudeLow(0);
        condition.setCallSign("XM5");
        condition.setActive(false);
        item.applyFilterCondition();
        assertEquals(item.isFilterMatched(), true);

    }

}
