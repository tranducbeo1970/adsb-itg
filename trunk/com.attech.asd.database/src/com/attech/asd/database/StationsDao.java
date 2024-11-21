/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;

import com.attech.asd.database.entities.Sensors;
import com.attech.asd.database.entities.Stations;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author anhth
 */
public class StationsDao extends Base{
    
    public boolean clear() {
        return super.execute("DELETE FROM Sensors;");
    }
    
    public synchronized boolean save(List<Stations> stations) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Stations station : stations) {
                session.saveOrUpdate(station);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
        
    public synchronized boolean save(Stations station) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(station);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean remove(List<Stations> stations) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Stations station : stations) {
                session.delete(station);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    public List<Stations> listStations(){
        Session session = getSessionFactory().openSession();

        try {
            session.beginTransaction();
            List resultList = session.createQuery("FROM Stations ORDER BY sortNumber ASC").list();
            session.getTransaction().commit();
            return resultList;
        } catch (HibernateException ex) {
            throw ex;
            //e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public Stations get(int id) {
        String query = String.format("FROM Stations WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<Stations> list = (List<Stations>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public Stations getStationByName(String name) {
        String query = String.format("FROM Stations WHERE StationName = :name ");
        Parameter[] parameters = new Parameter[]{new Parameter("name", name)};
        List<Stations> list = (List<Stations>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public Stations getStationByDescription(String description) {
        String query = String.format("FROM Stations WHERE StationDescription = :description ");
        Parameter[] parameters = new Parameter[]{new Parameter("description", description)};
        List<Stations> list = (List<Stations>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


}
