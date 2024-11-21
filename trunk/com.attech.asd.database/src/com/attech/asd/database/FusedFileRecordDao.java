/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;

import com.attech.asd.database.entities.FusedFileRecord;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Tang Hai Anh
 */
public class FusedFileRecordDao extends Base {

    public boolean clear() {
        return super.execute("DELETE FROM FileRecord;");
    }

    public synchronized boolean save(List<FusedFileRecord> files) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (FusedFileRecord f : files) {
                session.saveOrUpdate(f);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean save(FusedFileRecord fileRecord) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(fileRecord);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean remove(List<FusedFileRecord> files) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (FusedFileRecord f : files) {
                session.delete(f);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public List<FusedFileRecord> listFileRecord() {
        String query = "FROM FusedFileRecord";
        List<FusedFileRecord> list = (List<FusedFileRecord>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    public List<FusedFileRecord> listCorrectionFile() {
        String query = "SELECT fr FROM FusedFileRecord fr WHERE fr.status = 0";
        List<FusedFileRecord> list = (List<FusedFileRecord>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public FusedFileRecord get(int id) {
        String query = String.format("FROM FusedFileRecord WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<FusedFileRecord> list = (List<FusedFileRecord>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<FusedFileRecord> listFusedFileRecordByDate(String from, String to) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("SELECT fr FROM FusedFileRecord fr WHERE fr.fromtime >= :from AND fr.fromtime < :to ORDER BY fr.fromtime ASC");
            
            query.setParameter("from", from);
            query.setParameter("to", to);
            List resultList = query.list();
            session.getTransaction().commit();
            return resultList;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }
    
    public List<FusedFileRecord> listByDate(String from, String to) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query;
            if (!from.isEmpty() && !to.isEmpty()){
                query = session.createQuery("SELECT fr FROM FusedFileRecord fr WHERE fr.fromtime >= :from AND fr.fromtime < :to ORDER BY fr.fromtime ASC");
                query.setParameter("from", from);
                query.setParameter("to", to);
            } else if (!from.isEmpty() && to.isEmpty()) {
                query = session.createQuery("SELECT fr FROM FusedFileRecord fr WHERE fr.fromtime >= :from ORDER BY fr.fromtime ASC");
                query.setParameter("from", from);
            } else if (from.isEmpty() && !to.isEmpty()) {
                query = session.createQuery("SELECT fr FROM FusedFileRecord fr WHERE fr.fromtime < :to ORDER BY fr.fromtime ASC");
                query.setParameter("to", to);
            } else
                query = session.createQuery("SELECT fr FROM FusedFileRecord fr ORDER BY fr.fromtime ASC");
            
            List resultList = query.list();
            session.getTransaction().commit();
            return resultList;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }
    
}
