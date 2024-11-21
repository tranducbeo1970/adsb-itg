/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.flights;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author andh
 */
public class AirCraftTest {
    
    public AirCraftTest() {
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
     * Test of add method, of class AirCraft.
     */
    @Test
    public void testAdd_Message() {
        AirCraft instance = new AirCraft();
        AirCraft aircraft = AirCraft.load(new File("E:\\works\\temp\\4C11A.xml"));
        System.out.println(aircraft.getMessages().size());
        
        
    }

    
    
}
