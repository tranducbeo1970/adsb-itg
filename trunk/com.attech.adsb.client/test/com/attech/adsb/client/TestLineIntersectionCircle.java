/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client;

import com.attech.adsb.client.common.Distance;
import com.attech.adsb.client.config.Point2f;
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
public class TestLineIntersectionCircle {

    public TestLineIntersectionCircle() {
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
     * Test of main method, of class Run.
     */
    @Test
    public void testMain() {
        test(new Point2f(0, 0), new Point2f(1, 1), new Point2f(3, 3), 1.0d, false);
        test(new Point2f(0, 0), new Point2f(2, 2), new Point2f(3, 3), 1.0d, false);
        test(new Point2f(0, 0), new Point2f(3, 4), new Point2f(3, 3), 1.0d, true);
        test(new Point2f(0, 0), new Point2f(5, 5), new Point2f(3, 3), 1.0d, true);

    }

    private void test(Point2f s1, Point2f e1, Point2f s2, double radius, boolean expected) {
        boolean result = Distance.isLineIntersectionCircle(s1, e1, s2, radius);
        assertEquals(expected, result);
    }

}
