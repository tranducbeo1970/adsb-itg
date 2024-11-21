/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

import com.attech.asd.amhs.database.entities.Flightplan;
import java.util.List;
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
public class DailyFPLTest {

    public DailyFPLTest() {
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
     * Test of getDailyFlightPlanList method, of class DailyFPL.
     */
    @Test
    public void testGetDailyFlightPlanList() throws Exception {
        String inputDailyFPL = "PART 3 OF 28\r\n"
                + "FPL ON:06-JUL-2020: LANDING FLIGHTS\r\n"
                + "54 A321 ASV521 RKSI VVNB 1340 1820 R474 CAR\r\n"
                + "55 A321 ASV512 VVDN RKSI 1920 0025+ A1 CAR\r\n"
                + "56 A321 ASV522 VVNB RKSI 1920 0140+ R474 CAR\r\n"
                + "57 A321 ASV525 RKSI VVCR 2305 0355+ N892/Q15 CAR\r\n"
                + "58 A320 BAV210 VVTS VVNB 0000 0210 Q2\r\n"
                + "59 A320 BAV1621 VVNB VVPQ 0000 0205 Q1/A202/M753/N639\r\n"
                + "60 A320 BAV215 VVNB VVTS 0000 0210 Q1\r\n"
                + "61 A320 BAV1421 VVNB VVDL 0000 0150 Q1/W7/W1\r\n"
                + "62 A320 BAV1122 VVTS VVPC 0100 0210 W12\r\n"
                + "63 A320 BAV1422 VVDL VVNB 0235 0425 W7/Q1/Q6/Q2\r\n"
                + "64 A320 BAV1641 VVCI VVPC 0250 0425 Q10/Q1/W2\r\n"
                + "65 A320 BAV211 VVNB VVTS 0255 0505 Q1\r\n"
                + "66 A320 BAV1415 VVNB VVCR 0455 0705 Q1/W2/W1\r\n"
                + "67 A320 BAV1172 VVTS VVTX 0500 0705 W12/Q2/W2/W24\r\n"
                + "68 A320 BAV1642 VVPC VVCI 0510 0645 W2/Q2/Q4/W20\r\n"
                + "69 A320 BAV1173 VVTX VVTS 0750 0950 W24/W2/Q1\r\n"
                + "70 A320 BAV1416 VVCR VVNB 0750 0940 W2/Q2\r\n"
                + "71 A320 BAV204 VVTS VVNB 0755 1005 Q2\r\n"
                + "72 A320 BAV1214 VVPC VVNB 0810 0950 W2/Q2\r\n"
                + "73 A320 BAV213 VVNB VVTS 0855 1110 Q1\r\n"
                + "74 A320 BAV1624 VVPQ VVNB 0950 1155 M753/A202/Q2/N639\r\n"
                + "75 A320 BAV216 VVTS VVNB 1000 1210 Q2\r\n"
                + "76 A320 BAV206 VVTS VVNB 1045 1255 Q2\r\n"
                + "77 A320 BAV1158 VVTS VVVH 1200 1355 Q2/W2\r\n"
                + "78 A320 BAV205 VVNB VVTS 1200 1405 Q1\r\n"
                + "79 A320 BAV150 VVTS VVDN 1300 1420 Q2\r\n"
                + "80 A320 BAV105 VVNB VVDN 1300 1420 Q1\r\n\r\n\r\n";

        
        List<Flightplan> result = DailyFPL.getDailyFlightPlanList(inputDailyFPL);
        assertEquals(result.size(), 27);
        
        // obj01
        Flightplan flightPlan01 = new Flightplan();
        flightPlan01.setCraft("A321");
        flightPlan01.setCallSign("ASV521");
        flightPlan01.setDep("RKSI");
        flightPlan01.setDest("VVNB");
        flightPlan01.setEtd("1340");
        flightPlan01.setEta("1820");
        flightPlan01.setRemark("R474 CAR");
        assertFlightPlan(flightPlan01, result.get(0));
        
//        assertEquals(expResult, result);
    }
    
    private void assertFlightPlan(Flightplan expected, Flightplan actual) {
        if (actual == null) {
            assertNull(expected);
            return;
        }
        
        assertEquals(expected.getArrID(), actual.getArrID());
        assertEquals(expected.getArrSeq(), actual.getArrSeq());
        assertEquals(expected.getAta(), actual.getAta());
        assertEquals(expected.getAtd(), actual.getAtd());
        assertEquals(expected.getCallSign(), actual.getCallSign());
        assertEquals(expected.getCraft(), actual.getCraft());
        assertEquals(expected.getDailyFplID(), actual.getDailyFplID());
        assertEquals(expected.getDep(), actual.getDep());
        assertEquals(expected.getDepID(), actual.getDepID());
        assertEquals(expected.getDepSeq(), actual.getDepSeq());
        assertEquals(expected.getDest(), actual.getDest());
        assertEquals(expected.getEet(), actual.getEet());
        assertEquals(expected.getEta(), actual.getEta());
        assertEquals(expected.getEtd(), actual.getEtd());
    }

}
