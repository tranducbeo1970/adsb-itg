/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;
import com.attech.asd.database.entities.Config;
import java.io.File;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
/**
 *
 * @author Tang Hai Anh
 */
public class ConfigDao extends Base {
    public boolean clear() {
        return super.execute("DELETE FROM Config;");
    }

    public synchronized boolean save(List<Config> configs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Config f : configs) {
                session.saveOrUpdate(f);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public boolean save(Config config) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(config);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean remove(List<Config> configs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Config f : configs) {
                session.delete(f);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public List<Config> listAllConfig() {
        String query = "FROM Config";
        List<Config> list = (List<Config>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public Config get(int id) {
        String query = String.format("FROM Config WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<Config> list = (List<Config>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public Config get(String paramName) {
        String query = String.format("FROM Config WHERE ParamName = :paramName ");
        Parameter[] parameters = new Parameter[]{new Parameter("paramName", paramName)};
        List<Config> list = (List<Config>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public synchronized int updateProperty(String key, String value){ //AuthenticationCode
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("update Config set paramValue = :paramValue where paramName = :paramName");
            query.setParameter("paramName", key);
            query.setParameter("paramValue", value);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
}
