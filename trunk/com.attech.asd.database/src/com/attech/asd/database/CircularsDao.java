/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;

import com.attech.asd.database.entities.Circulars;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Tang Hai Anh
 */
public class CircularsDao extends Base  {
    public boolean clear() {
        return super.execute("DELETE FROM Circulars;");
    }

    public synchronized boolean save(List<Circulars> lstObjs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Circulars o : lstObjs) {
                session.saveOrUpdate(o);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean save(Circulars obj) {
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

    public synchronized boolean remove(List<Circulars> lstObjs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Circulars obj : lstObjs) {
                session.delete(obj);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public List<Circulars> list() {
        String query = "FROM DailyAnalyzing";
        List<Circulars> list = (List<Circulars>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public Circulars get(int id) {
        String query = String.format("FROM DailyAnalyzing WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<Circulars> list = (List<Circulars>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
}
