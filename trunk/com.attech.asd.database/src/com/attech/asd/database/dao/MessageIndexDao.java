/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.dao;

import com.attech.asd.database.common.DBException;
import com.attech.asd.database.Base;
import com.attech.asd.database.entities.MessageIndex;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 *
 * @author andh
 */
public class MessageIndexDao  extends Base{

    public void insert(MessageIndex messageIndex) throws DBException {
        Session session = getSessionFactory().openSession();

        try {
            Date date = new Date();
            messageIndex.setCreatedDate(date);
            messageIndex.setLastUpdatedDate(date);

            // Start Transaction.            
            session.getTransaction().begin();
            session.save(messageIndex);
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
        Session session = getSessionFactory().openSession();

        try {

           // Start Transaction.            
	    session.getTransaction().begin();
	    Query query = session.createSQLQuery("SELECT COUNT(*) FROM MESSAGE_INDEX WHERE SEQ = :seq AND ACCOUNTID = :accountID");
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

    public boolean isExisted(MessageIndex index) throws DBException {
        return isExisted(index.getSeq(), index.getAccountID());
    }

    public List<MessageIndex> getUpdateList(int accountID) throws DBException {
        Session session = getSessionFactory().openSession();

        try {

            // Start Transaction.            
            session.getTransaction().begin();

            // Create Query object.
            Query query = session.createQuery("SELECT * FROM Message_Index m WHERE m.accountID = :accountID AND m.retryCount < 10 ORDER BY m.id");
            query.setParameter("accountID", accountID);

            List<MessageIndex> accounts = query.list();

            // Commit data.
            session.getTransaction().commit();
            return accounts;

        } catch (Exception e) {
            // e.printStackTrace();
            // Rollback in case of an error occurred.
            session.getTransaction().rollback();
            throw new DBException("DB operation fail.", e);
        }
    }
    
    public void remove(MessageIndex messageIndex) throws DBException{
        Session session = getSessionFactory().openSession();

        try {

            // Start Transaction.            
            session.getTransaction().begin();

            session.delete(messageIndex);

            // Commit data.
            session.getTransaction().commit();

        } catch (Exception e) {
            // e.printStackTrace();
            // Rollback in case of an error occurred.
            session.getTransaction().rollback();
            throw new DBException("DB operation fail.", e);
        }
    }
    
     public void update(MessageIndex messageIndex) throws DBException{
        Session session = getSessionFactory().openSession();

        try {

	    messageIndex.setLastUpdatedDate(new Date());
            // Start Transaction.            
            session.getTransaction().begin();

            session.update(messageIndex);

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
