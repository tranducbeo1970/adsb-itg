/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

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
public class DepEntTest {

    public DepEntTest() {
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
    public void testParse() {

        String message = "(DEP-VJC781/A4134-VVNB0717-VVCR-DOF/200703)\r\n\r\n";
        DepEnt result = DepEnt.parse(message);
        DepEnt expResult = new DepEnt();
        expResult.setCallSign("VJC781");
        expResult.setDeparture("VVNB");
        expResult.setDepartureTime("0717");
        expResult.setDestination("VVCR");
        expResult.setDof("200703");
        expResult.setSsr("A4134");

        assertDepEnt(expResult, result);
    }

    @Test
    public void testParse2() {

        String message = "(DEP-VFC8072/A6003-VVCS0728-VVTS\r\n"
                + "-DOF/200703)\r\n";
        DepEnt result = DepEnt.parse(message);
        DepEnt expResult = new DepEnt();
        expResult.setCallSign("VFC8072");
        expResult.setDeparture("VVCS");
        expResult.setDepartureTime("0728");
        expResult.setDestination("VVTS");
        expResult.setDof("200703");
        expResult.setSsr("A6003");

        assertDepEnt(expResult, result);
    }

    private void assertDepEnt(DepEnt expected, DepEnt actual) {
        if (expected == null) {
            assertNull(actual);
            return;
        }

        assertEquals(expected.getCallSign(), actual.getCallSign());
        assertEquals(expected.getDeparture(), actual.getDeparture());
        assertEquals(expected.getDepartureTime(), actual.getDepartureTime());
        assertEquals(expected.getDestination(), actual.getDestination());
        assertEquals(expected.getDof(), actual.getDof());
        assertEquals(expected.getSsr(), actual.getSsr());
    }

}
