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
 * @author ANDH
 */
public class FplEntTest {

    public FplEntTest() {
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
     * Test of validate method, of class FplEnt.
     */
    @Test
    public void testValidate() {
	System.out.println("validate");
	String message = "(FPL-ETH3687-IS\r\n"
		+ "-B77L/H-SDE1E3FGHIJ2J3J4J5J7LOP1P2RVWXYZ/HB1D1\r\n"
		+ "-ZGGG2005\r\n"
		+ "-N0513F280 VIBOS R473 SIERA DCT MULET DCT ALLEY V31 \r\n"
		+ "SURFA/N0506F300 V31 IDOSI/M083F300 P901 IKELA A1 DAN/N0507F300 A1 \r\n"
		+ "UBL W1 BKK M502 LALIT/N0500F320 P762 PPB P761 MMV L875 \r\n"
		+ "BEDIL/N0494F340 L875 GOLEM DCT 1143N06431E 1128N06150E DCT ORLID \r\n"
		+ "UT382G HARGA UW885G ARSHI W885 DWA UT667 MIWAS MIWAS7A \r\n"
		+ "-HAAB0918 HDAM\r\n"
		+ "-PBN/A1B1C1D1L1O1S2 DAT/CPDLCX SUR/EGPW S WXRD RSP180 DOF/200702 \r\n"
		+ "REG/ETAVN EET/VHHK0015 ZJSA0042 VVTS0105 VLVT0121 VTBB0132 \r\n"
		+ "VYYF0218 VOMF0251 VABF0526 HCSM0648 HAAA0844 SEL/ABDK \r\n"
		+ "OPR/ETHIOPIAN AIRLINES PER/D RALT/VYYY VOMM VOHS OOSA HDAM \r\n"
		+ "RMK/TCAS CARGO FLIGHT RVR 075)\r\n";
	FplEnt instance = new FplEnt(message);
	FplEnt expected = new FplEnt();
	expected.setCallSign("ETH3687");

	assertEquals(expected.getCallSign(), instance.getCallSign());
    }

}
