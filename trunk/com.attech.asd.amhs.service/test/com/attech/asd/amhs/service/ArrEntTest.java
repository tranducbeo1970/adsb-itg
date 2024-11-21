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
public class ArrEntTest {

    public ArrEntTest() {
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
     * Test of parses method, of class ArrEnt.
     */
    @Test
    public void testParses() {
	System.out.println("Case 01");
	String content = "(ARR-HVN291-VVNB1500-VVTS1705)";
	ArrEnt result = ArrEnt.parses(content);
	ArrEnt expResult = new ArrEnt();
	expResult.setArrivalTime("1705");
	expResult.setCallsign("HVN291");
	expResult.setDeparture("VVNB");
	expResult.setDestination("VVTS");
	expResult.setDepartureTime("1500");
//	expResult.setDof("");
//	expResult.setSsr("");
	assertArr(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
//	fail("The test case is a prototype.");
    }

    public void testParses2() {
	System.out.println("Case 02");
	String content = "(ARR-VJC212-VVTS-VVVH0057-DOF/200703)";
	ArrEnt result = ArrEnt.parses(content);
	ArrEnt expResult = new ArrEnt();
	expResult.setArrivalTime("0057");
	expResult.setCallsign("VJC212");
	expResult.setDeparture("VVTS");
	expResult.setDestination("VVVH");
	expResult.setDof("200703");
//	expResult.setSsr("");
	assertArr(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
//	fail("The test case is a prototype.");
    }

    public void testParses3() {
	System.out.println("Case 03");
	String content = "(ARR-VJC453-VVNB0404-VVPQ0543\r\n"
		+ "-DOF/200703)";
	ArrEnt result = ArrEnt.parses(content);
	ArrEnt expResult = new ArrEnt();
	expResult.setArrivalTime("0543");
	expResult.setCallsign("VJC453");
	expResult.setDeparture("VVNB");
	expResult.setDestination("VVPQ");
	expResult.setDepartureTime("0404");
	expResult.setDof("200703");
//	expResult.setSsr("");
	assertArr(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
//	fail("The test case is a prototype.");
    }

    private void assertArr(ArrEnt expected, ArrEnt actual) {
	if (expected == null) {
	    assertNull(actual);
	    return;
	}

	assertEquals(expected.getDepartureTime(), actual.getDepartureTime());
	assertEquals(expected.getArrivalTime(), actual.getArrivalTime());
	assertEquals(expected.getCallsign(), actual.getCallsign());
	assertEquals(expected.getDeparture(), actual.getDeparture());
	assertEquals(expected.getDestination(), actual.getDestination());
	assertEquals(expected.getDof(), actual.getDof());
	assertEquals(expected.getSsr(), actual.getSsr());
    }

}
