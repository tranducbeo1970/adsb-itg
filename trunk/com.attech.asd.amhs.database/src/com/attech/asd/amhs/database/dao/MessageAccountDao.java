/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.database.dao;

import com.attech.asd.amhs.database.entities.MessageAccount;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author ANDH
 */
public class MessageAccountDao {

    public List<MessageAccount> getAll() throws DBException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {

            // Start Transaction.            
            session.getTransaction().begin();

            // Create Query object.
            Query<MessageAccount> query = session.createNamedQuery("MessageAccount.findAll", MessageAccount.class);

            List<MessageAccount> accounts = query.getResultList();

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

    public void update(MessageAccount messageAccount) throws DBException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        try {

            messageAccount.setUpdatedDate(new Date());
            // Start Transaction.            
            session.getTransaction().begin();

            // Create Query object.
            session.update(messageAccount);

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
