/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

import com.attech.asd.amhs.database.dao.Common;
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
public class CommonTest {

    public CommonTest() {
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
     * Test of plusTime method, of class Common.
     */
    @Test
    public void testPlusTime() {
	test("0000", "0030", "0030");
	test("0000", "0000", "0000");
	test("0030", "0030", "0100");
	test("0140", "0130", "0310");
	test("2350", "0230", "0220");
	test("235-", "0230", "----");
    }

    private void test(String time01, String time02, String expected) {
	System.out.println("plusTime");
	String result = Common.plusTime(time01, time02);
	assertEquals(expected, result);
    }

}
