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
public class TrackStatusTest {
    
    public TrackStatusTest() {
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
     * Test of decode method, of class TrackStatus.
     */
    @Test
    public void testDecode() {
        TrackStatus expected = new TrackStatus();
        expected.setMonoSensorTrack(true);
        expected.setSpiPresent(true);
        expected.setMostReliableHeightl(true);
        expected.setCalculatedAltitudeSource(1);
        expected.setTentativeTrack(true);
        byte [] bytes = expected.encode2();
        
        TrackStatus result = new TrackStatus();
        TrackStatus.decode(bytes, 0, result);
        assertTrackStatus(expected, result);
        
         // Ext1
        expected.setSimulatedTrack(true);
        expected.setLastMessage(true);
        expected.setFirstMessage(true);
        expected.setFlightPlanCorrelated(true);
        expected.setAdsbInconsistentWithOtherSource(true);
        expected.setSlaveTrackPromotion(true);
        expected.setBackgroundService(true);
        
        bytes = expected.encode2();
        result = new TrackStatus();
        TrackStatus.decode(bytes, 0, result);
        assertTrackStatus(expected, result);
        
         // Ext2
        expected.setTrackResultingOfAmalgamationProcess(true);
        expected.setMode4(1);
        expected.setMilitaryEmergency(true);
        expected.setMilitaryIdentification(true);
        expected.setMode5(1);
        bytes = expected.encode2();
        result = new TrackStatus();
        TrackStatus.decode(bytes, 0, result);
        assertTrackStatus(expected, result);

        // Ext3
        expected.setTrackUpdatedAgeHigherSysThreshold(true);
        expected.setPsrUpdatedAgeHigherSysThreshold(true);
        expected.setSsrUpdatedAgeHigherSysThreshold(true);
        expected.setModeSUpdatedAgeHigherSysThreshold(true);
        expected.setAdsbUpdatedAgeHigherSysThreshold(true);
        expected.setUsedSpecialCode(true);
        expected.setAssignCodeConflict(true);
        bytes = expected.encode2();
        result = new TrackStatus();
        TrackStatus.decode(bytes, 0, result);
        assertTrackStatus(expected, result);
    }

    /**
     * Test of encode method, of class TrackStatus.
     */
    @Test
    public void testEncode() {
        System.out.println("encode");
        TrackStatus instance = new TrackStatus();
        instance.setMonoSensorTrack(true);
        instance.setSpiPresent(true);
        instance.setMostReliableHeightl(true);
        instance.setCalculatedAltitudeSource(1);
        instance.setTentativeTrack(true);
        
        // 11100110
        byte [] result = instance.encode2();
        byte [] expected = new byte[] { (byte) 0xE6 };
        Assert.assertArrayEquals(expected, result);
        
        // 01100110
        instance.setMonoSensorTrack(false);
        result = instance.encode2();
        expected = new byte[] { (byte) 0x66 };
        Assert.assertArrayEquals(expected, result);
        
        // 00100110
        instance.setSpiPresent(false);
        result = instance.encode2();
        expected = new byte[] { (byte) 0x26 };
        Assert.assertArrayEquals(expected, result);
        
        // 00000110
        instance.setMostReliableHeightl(false);
        result = instance.encode2();
        expected = new byte[] { (byte) 0x06 };
        Assert.assertArrayEquals(expected, result);
        
        // 00001010
        instance.setCalculatedAltitudeSource(2);
        result = instance.encode2();
        expected = new byte[] { (byte) 0x0A };
        Assert.assertArrayEquals(expected, result);
        
        // 00001110
        instance.setCalculatedAltitudeSource(3);
        result = instance.encode2();
        expected = new byte[] { (byte) 0x0E };
        Assert.assertArrayEquals(expected, result);
    }
    
    @Test
    public void testEncode2() {
        System.out.println("encode");
        TrackStatus instance = new TrackStatus();
        instance.setMonoSensorTrack(true);
        instance.setSpiPresent(true);
        instance.setMostReliableHeightl(true);
        instance.setCalculatedAltitudeSource(1);
        instance.setTentativeTrack(true);

        // Ext1
        instance.setSimulatedTrack(true);
        instance.setLastMessage(true);
        instance.setFirstMessage(true);
        instance.setFlightPlanCorrelated(true);
        instance.setAdsbInconsistentWithOtherSource(true);
        instance.setSlaveTrackPromotion(true);
        instance.setBackgroundService(true);
        
        // 11100111 11111110
        byte [] result = instance.encode2();
        byte [] expected = new byte[] { (byte) 0xE7, (byte) 0xFE };
        Assert.assertArrayEquals(expected, result);
        
        // Ext2
        instance.setTrackResultingOfAmalgamationProcess(true);
        instance.setMode4(1);
        instance.setMilitaryEmergency(true);
        instance.setMilitaryIdentification(true);
        instance.setMode5(1);
        
        // 11100111 11111111 10111010
        result = instance.encode2();
        expected = new byte[] { (byte) 0xE7, (byte) 0xFF, (byte) 0xBA };
        Assert.assertArrayEquals(expected, result);
     
        // Ext3
        instance.setTrackUpdatedAgeHigherSysThreshold(true);
        instance.setPsrUpdatedAgeHigherSysThreshold(true);
        instance.setSsrUpdatedAgeHigherSysThreshold(true);
        instance.setModeSUpdatedAgeHigherSysThreshold(true);
        instance.setAdsbUpdatedAgeHigherSysThreshold(true);
        instance.setUsedSpecialCode(true);
        instance.setAssignCodeConflict(true);
        
        // 11100110 11111111 10111011 11111110
        result = instance.encode2();
        expected = new byte[] { (byte) 0xE7, (byte) 0xFF, (byte) 0xBB, (byte) 0xFE };
        Assert.assertArrayEquals(expected, result);
    }
    
    public static void assertTrackStatus(TrackStatus expected, TrackStatus result) {
        // Assert.assertEquals(expected, result);

        Assert.assertEquals(expected.isMonoSensorTrack(), result.isMonoSensorTrack());
        Assert.assertEquals(expected.isSpiPresent(), result.isSpiPresent());
        Assert.assertEquals(expected.isMostReliableHeightl(), result.isMostReliableHeightl());
        Assert.assertEquals(expected.getCalculatedAltitudeSource(), result.getCalculatedAltitudeSource());
        Assert.assertEquals(expected.isTentativeTrack(), result.isTentativeTrack());

        // Exention #1
        Assert.assertEquals(expected.isSimulatedTrack(), result.isSimulatedTrack());
        Assert.assertEquals(expected.isLastMessage(), result.isLastMessage());
        Assert.assertEquals(expected.isFirstMessage(), result.isFirstMessage());
        Assert.assertEquals(expected.isFlightPlanCorrelated(), result.isFlightPlanCorrelated());
        Assert.assertEquals(expected.isAdsbInconsistentWithOtherSource(), result.isAdsbInconsistentWithOtherSource());
        Assert.assertEquals(expected.isSlaveTrackPromotion(), result.isSlaveTrackPromotion());
        Assert.assertEquals(expected.isBackgroundService(), result.isBackgroundService());

        // Exention #2
        Assert.assertEquals(expected.isTrackResultingOfAmalgamationProcess(), result.isTrackResultingOfAmalgamationProcess());
        Assert.assertEquals(expected.getMode4(), result.getMode4());
        Assert.assertEquals(expected.isMilitaryEmergency(), result.isMilitaryEmergency());
        Assert.assertEquals(expected.isMilitaryIdentification(), result.isMilitaryIdentification());
        Assert.assertEquals(expected.getMode5(), result.getMode5());

        // Exention #3
        Assert.assertEquals(expected.isTrackUpdatedAgeHigherSysThreshold(), result.isTrackUpdatedAgeHigherSysThreshold());
        Assert.assertEquals(expected.isPsrUpdatedAgeHigherSysThreshold(), result.isPsrUpdatedAgeHigherSysThreshold());
        Assert.assertEquals(expected.isSsrUpdatedAgeHigherSysThreshold(), result.isSsrUpdatedAgeHigherSysThreshold());
        Assert.assertEquals(expected.isModeSUpdatedAgeHigherSysThreshold(), result.isModeSUpdatedAgeHigherSysThreshold());
        Assert.assertEquals(expected.isAdsbUpdatedAgeHigherSysThreshold(), result.isAdsbUpdatedAgeHigherSysThreshold());
        Assert.assertEquals(expected.isUsedSpecialCode(), result.isUsedSpecialCode());
        Assert.assertEquals(expected.isAssignCodeConflict(), result.isAssignCodeConflict());

    }
}
