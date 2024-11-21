/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;

import com.attech.asd.database.common.DBException;
import com.attech.asd.database.entities.Sensors;
import com.attech.asd.database.entities.Stations;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author anhth
 */
public class SensorsDao extends Base{
    
    public boolean clear() {
        return super.execute("DELETE FROM Sensors;");
    }
    
    public synchronized boolean save(List<Sensors> sensors) throws DBException {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Sensors sensor : sensors) {
                session.saveOrUpdate(sensor);
            }
            session.getTransaction().commit();
            return true;
        } catch (Exception ex){
            throw new DBException("DB operation fail.", ex);
        } finally {
            session.close();
        }
    }
    
    public int updateStatus(int sic, byte status){
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("UPDATE Sensors SET Status = :status where Sic = :sic");
        query.setParameter("status", status);
        query.setParameter("sic", sic);
        try {
            session.beginTransaction();
            int result = query.executeUpdate();
            session.getTransaction().commit();
            return result;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean save(Sensors sensor) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(sensor);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean remove(List<Sensors> sensors) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Sensors sensor : sensors) {
                session.delete(sensor);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean remove(Sensors sensors) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(sensors);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public List<Sensors> listAllSensors(){
        Session session = getSessionFactory().openSession();

        try {
            session.beginTransaction();
            List resultList = session.createQuery("SELECT s FROM Sensors s JOIN s.station AS station ORDER BY station.sortNumber ASC, s.sic ASC").list();
            session.getTransaction().commit();
            return resultList;
        } catch (HibernateException ex) {
            throw ex;
            //e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public List<Sensors> listSensors(){
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        String query ="SELECT s FROM Sensors s JOIN s.station AS station ORDER BY station.sortNumber ASC, s.sic ASC";
        return this.find(query);
    }
    
    public Sensors get(int id){
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Query q = session.createQuery("FROM Master LEFT JOIN Master.Detail WHERE Master.id = :id ");
            Query q = session.createQuery("FROM Sensors WHERE Id = :id ");
            q.setParameter("id", id);
            final List<Sensors> resultList = q.list();
            session.getTransaction().commit();
            return (resultList == null || resultList.isEmpty()) ? null : resultList.get(0);

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
    public boolean isExist(int id){
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Query q = session.createQuery("FROM Master LEFT JOIN Master.Detail WHERE Master.id = :id ");
            Query q = session.createQuery("FROM Sensors WHERE Id = :id ");
            q.setParameter("id", id);
            final List<Sensors> resultList = q.list();
            session.getTransaction().commit();
            return (resultList != null && !resultList.isEmpty());

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
    public List<Sensors> listBySensorMode(int mode){
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery("FROM Sensors WHERE SensorMode = :mode ");
            q.setParameter("mode", mode);
            List resultList = q.list();
            session.getTransaction().commit();
            return resultList;

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
    
    public List<Sensors> listByStation(Stations station){
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery("SELECT s FROM Sensors s WHERE s.station = :station ");
            q.setParameter("station", station);
            List resultList = q.list();
            session.getTransaction().commit();
            return resultList;

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
    public Sensors getSensorBySic(int Sic){
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Query q = session.createQuery("FROM Master LEFT JOIN Master.Detail WHERE Master.id = :id ");
            Query q = session.createQuery("FROM Sensors WHERE Sic = :sic ");
            q.setParameter("sic", Sic);
            final List<Sensors> resultList = q.list();
            session.getTransaction().commit();
            return (resultList == null || resultList.isEmpty()) ? null : resultList.get(0);

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
    public int getSensorStatus(int Sic){
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery("SELECT s.Status FROM Sensors s WHERE s.Sic = :Sic ");
            q.setParameter("Sic", Sic);
            List resultList = q.list();
            session.getTransaction().commit();
            if (resultList == null || resultList.size() <=0)
                return -1;
            else{
                return Integer.getInteger(resultList.get(0).toString());
            }
                

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
    
}
