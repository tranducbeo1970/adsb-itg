/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.database.dao;

import com.attech.asd.amhs.database.entities.Message;
import java.math.BigInteger;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

/**
 *
 * @author ANDH
 */
public class MessageDao {

    public void insertOrUpdate(Message message) throws DBException {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

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
    
    public boolean isExisted(int seq, int accountID) throws DBException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {

           // Start Transaction.            
	    session.getTransaction().begin();
	    NativeQuery query = session.createSQLQuery("SELECT COUNT(*) FROM message WHERE SEQ = :seq AND AccountID = :accountID");
	    query.setParameter("seq", seq);
	    query.setParameter("accountID", accountID);
	    final Long count = ((BigInteger) query.uniqueResult()).longValue();

	    // Commit data.
	    session.getTransaction().commit();
	    return count != 0;

        } catch (Exception e) {
            // e.printStackTrace();
            // Rollback in case of an error occurred.
            session.getTransaction().rollback();
            throw new DBException("DB operation fail.", e);
        }
    }
    
    public boolean isExisted(Message message) throws DBException {
        return isExisted(message.getSeq(), message.getAccountID());
    }

}
