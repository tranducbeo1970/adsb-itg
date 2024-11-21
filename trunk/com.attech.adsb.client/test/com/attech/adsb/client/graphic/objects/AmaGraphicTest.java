/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.WarnLevel;
import com.attech.adsb.client.common.enums.WarnType;
import com.attech.adsb.client.config.Configuration;
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
public class AmaGraphicTest {

    public AmaGraphicTest() {
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
     * Test of draw method, of class AmaGraphic.
     */
    @Test
    public void testValidationCase01() {
        AmaGraphic amaGraphic = new AmaGraphic(Configuration.instance().getAma());
        amaGraphic.setEnable(true);
        amaGraphic.setEnableValidator(true);

        Target target = Target.load("test_data/ama/case01/AXM624.xml", Target.class);
        target.calculate();

        amaGraphic.validate(target);
        assertEquals(target.getWarningList().size(), 1);
        target.getWarningList().containsKey(WarnType.AMA);
        assertEquals(target.getWarningList().get(WarnType.AMA), WarnLevel.DANGER);
    }

    @Test
    public void testValidationCase02() {
        AmaGraphic amaGraphic = new AmaGraphic(Configuration.instance().getAma());
        amaGraphic.setEnable(true);
        amaGraphic.setEnableValidator(true);

        Target target = Target.load("test_data/ama/case01/CPA650.xml", Target.class);
        target.calculate();

        amaGraphic.validate(target);
        assertEquals(target.getWarningList().size(), 0);
    }

    @Test
    public void testValidationCase03() {
        AmaGraphic amaGraphic = new AmaGraphic(Configuration.instance().getAma());
        amaGraphic.setEnable(true);
        amaGraphic.setEnableValidator(true);

        Target target = Target.load("test_data/ama/case01/JSA555.xml", Target.class);
        target.calculate();

        amaGraphic.validate(target);
        assertEquals(target.getWarningList().size(), 1);
        target.getWarningList().containsKey(WarnType.AMA);
        assertEquals(target.getWarningList().get(WarnType.AMA), WarnLevel.ALARM);
    }
    
    @Test
    public void testValidationCase04() {
        AmaGraphic amaGraphic = new AmaGraphic(Configuration.instance().getAma());
        amaGraphic.setEnable(true);
        amaGraphic.setEnableValidator(true);

        Target target = Target.load("test_data/ama/case01/JSA555_1.xml", Target.class);
        target.calculate();

        amaGraphic.validate(target);
        assertEquals(target.getWarningList().size(), 1);
        target.getWarningList().containsKey(WarnType.AMA);
        assertEquals(target.getWarningList().get(WarnType.AMA), WarnLevel.WARN);
    }

}
