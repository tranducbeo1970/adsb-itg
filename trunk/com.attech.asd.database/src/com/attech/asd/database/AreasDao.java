/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;

import com.attech.asd.database.entities.Areas;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Tang Hai Anh
 */
public class AreasDao  extends Base {
    public boolean clear() {
        return super.execute("DELETE FROM Areas;");
    }

    public synchronized boolean save(List<Areas> areas) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Areas area : areas) {
                session.saveOrUpdate(area);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean save(Areas area) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(area);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean remove(List<Areas> areas) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Areas area : areas) {
                session.delete(area);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public List<Areas> listAll() {
        String query = "FROM Areas";
        List<Areas> list = (List<Areas>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    public List<Areas> listArtificial() {
        String query = "FROM Areas WHERE Type = 1";
        List<Areas> list = (List<Areas>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public Areas get(int id) {
        String query = String.format("FROM Areas WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<Areas> list = (List<Areas>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
        
    public Areas getAreaByName(String name) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Areas WHERE AreaName = :name ");
            query.setParameter("name", name);
            final List<Areas> resultList = query.list();
            session.getTransaction().commit();
            return (resultList == null || resultList.isEmpty()) ? null : resultList.get(0);
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
    public Areas getCustomAreaByName(String name) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Areas WHERE Type = 1 AND AreaName = :name ");
            query.setParameter("name", name);
            final List<Areas> resultList = query.list();
            session.getTransaction().commit();
            return (resultList == null || resultList.isEmpty()) ? null : resultList.get(0);
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
    public Areas getMaxId() {
        String query = String.format("FROM Areas ORDER BY Id DESC LIMIT 1 ");
        List<Areas> list = (List<Areas>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    
    
}
