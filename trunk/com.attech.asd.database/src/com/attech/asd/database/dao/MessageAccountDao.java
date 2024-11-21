/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.dao;

import com.attech.asd.database.common.DBException;
import com.attech.asd.database.Base;
import com.attech.asd.database.Parameter;
import com.attech.asd.database.entities.MessageAccount;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 *
 * @author ANDH
 */
public class MessageAccountDao  extends Base  {

    public List<MessageAccount> getAll() throws DBException {
        Session session = getSessionFactory().openSession();
        try {

            // Start Transaction.            
            session.getTransaction().begin();

            // Create Query object.
            Query query = session.createQuery("FROM MessageAccount");
            List<MessageAccount> accounts = query.list();

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
    
    public MessageAccount get(int id){
        String query = String.format("FROM MessageAccount WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<MessageAccount> list = (List<MessageAccount>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public boolean isExist(int id){
        String query = String.format("FROM MessageAccount WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<MessageAccount> list = (List<MessageAccount>) this.find(query, parameters);
        return !(list == null || list.isEmpty());
    }

    public void update(MessageAccount messageAccount) throws DBException {
        Session session = getSessionFactory().openSession();

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
    
    public synchronized boolean save(MessageAccount obj) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(obj);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean remove(MessageAccount obj) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(obj);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

}
