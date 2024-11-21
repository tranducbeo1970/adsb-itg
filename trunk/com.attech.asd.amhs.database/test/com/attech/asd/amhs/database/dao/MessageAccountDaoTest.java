/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.database.dao;

import com.attech.asd.amhs.database.entities.MessageAccount;
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
 * @author andh
 */
public class MessageAccountDaoTest {
    
    public MessageAccountDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	HibernateUtil.buildSessionFactory("database.xml");
    }
    
    @After
    public void tearDown() {
	HibernateUtil.shutdown();
    }

    /**
     * Test of getAll method, of class MessageAccountDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAll() throws Exception {
        System.out.println("getAll");
        MessageAccountDao instance = new MessageAccountDao();
        List<MessageAccount> expResult = new ArrayList<>();
        List<MessageAccount> result = instance.getAll();
        assertEquals(expResult, result);
    }
    
}
