/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;
import com.attech.asd.database.entities.SensorLogs;
import com.attech.asd.database.entities.Sensors;
import java.util.List;
import org.hibernate.Session;
/**
 *
 * @author Tang Hai Anh
 */
public class SensorLogsDao  extends Base  {
    public boolean clear() {
        return super.execute("DELETE FROM SensorLogs;");
    }

    public synchronized boolean save(List<SensorLogs> lstObjs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (SensorLogs o : lstObjs) {
                session.saveOrUpdate(o);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean save(SensorLogs obj) {
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

    public synchronized boolean remove(List<SensorLogs> lstObjs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (SensorLogs obj : lstObjs) {
                session.delete(obj);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public List<SensorLogs> list() {
        String query = "FROM SensorLogs";
        List<SensorLogs> list = (List<SensorLogs>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public SensorLogs get(int id) {
        String query = String.format("FROM SensorLogs WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<SensorLogs> list = (List<SensorLogs>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public List<SensorLogs> getByDateAndStation(String from, String to, Sensors sensor) {
        String query = String.format("FROM SensorLogs WHERE CreatedDate >= :from AND CreatedDate <= :to AND SensorId.station = :station ORDER BY CreatedDate ASC");
        Parameter[] parameters = new Parameter[]{new Parameter("date", from), new Parameter("to", to), new Parameter("station", sensor.getStation())};
        List<SensorLogs> list = (List<SensorLogs>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    public List<SensorLogs> getByDateAndSensor(String from, String to, Sensors sensor) {
        String query = String.format("FROM SensorLogs WHERE CreatedDate >= :from AND CreatedDate <= :to AND SensorId = :sensor ORDER BY CreatedDate ASC");
        Parameter[] parameters = new Parameter[]{new Parameter("from", from), new Parameter("to", to),  new Parameter("sensor", sensor)};
        List<SensorLogs> list = (List<SensorLogs>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    public List<SensorLogs> listByDateAndSensor( Sensors sensor, String from, String to) {
        String query = String.format("FROM SensorLogs WHERE CreatedDate >= :from AND CreatedDate <= :to AND SensorId = :sensor ORDER BY CreatedDate ASC");
        Parameter[] parameters = new Parameter[]{new Parameter("sensor", sensor), new Parameter("from", from), new Parameter("to", to)};
        List<SensorLogs> list = (List<SensorLogs>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
}
