/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.dao;

import com.attech.asd.database.common.DBException;
import com.attech.asd.database.Base;
import com.attech.asd.database.entities.Message;
import java.util.Date;
import org.hibernate.Session;

/**
 *
 * @author ANDH
 */
public class MessageDao extends Base{

    public void insertOrUpdate(Message message) throws DBException {
	Session session = getSessionFactory().openSession();

	try {
	    Date date = new Date();
	    if (message.getCreatedDate() == null) {
		message.setCreatedDate(date);
	    }

	    message.setUpdatedDate(date);

	    // Start Transaction.            
	    session.getTransaction().begin();
	    session.saveOrUpdate(message);
	    session.flush();

	    // Commit data.
	    session.getTransaction().commit();

	} catch (Exception e) {
	    // e.printStackTrace();
	    // Rollback in case of an error occurred.
	    session.getTransaction().rollback();
	    throw new DBException("DB operation fail.", e);
	}
    }

}
