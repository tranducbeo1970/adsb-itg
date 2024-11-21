/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client;

import com.attech.adsb.client.common.Distance;
import com.attech.adsb.client.config.Point2f;
import java.util.ArrayList;
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
public class DistanceTest {
    
    public DistanceTest() {
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
        test(new Point2f(0, 0), new Point2f(1, 1), new Point2f(1, 2), new Point2f(2, 1), false);
        test(new Point2f(0, 0), new Point2f(2, 2), new Point2f(1, 2), new Point2f(2, 1), true);
        
        List<Point2f> polygon = new ArrayList<>();
        polygon.add(new Point2f(1, 1));
        polygon.add(new Point2f(1, 2));
        polygon.add(new Point2f(2, 2));
        polygon.add(new Point2f(2, 1));
        testPolygon(new Point2f(-1, 0), new Point2f(0, 0), polygon, false);
         testPolygon(new Point2f(-1, 0), new Point2f(3, 2), polygon, true);
        
    }

    private void test(Point2f s1, Point2f e1, Point2f s2, Point2f e2, boolean expected) {
        boolean result = Distance.isLineIntersection(s1, e1, s2, e2);
        assertEquals(expected, result);
    }
    
    private void testPolygon(Point2f s1, Point2f e1, List<Point2f> s2, boolean expected) {
        boolean result = Distance.isLineCrossPolygon(s1, e1, s2);
        assertEquals(expected, result);
    }
    
    
}
