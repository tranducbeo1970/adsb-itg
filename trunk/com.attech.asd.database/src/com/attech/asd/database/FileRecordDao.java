/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;

import com.attech.asd.database.entities.FileRecord;
import com.attech.asd.database.entities.Sensors;
import com.attech.asd.database.entities.Stations;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Tang Hai Anh
 */
public class FileRecordDao extends Base {

    public boolean clear() {
        return super.execute("DELETE FROM FileRecord;");
    }

    public synchronized boolean save(List<FileRecord> files) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (FileRecord f : files) {
                session.saveOrUpdate(f);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean save(FileRecord fileRecord) {
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

    public synchronized boolean remove(List<FileRecord> files) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (FileRecord f : files) {
                session.delete(f);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public List<FileRecord> listFileRecord() {
        String query = "FROM FileRecord";
        List<FileRecord> list = (List<FileRecord>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    public List<FileRecord> listCorrectionFile() {
        String query = "SELECT fr FROM FileRecord fr WHERE fr.status = 0";
        List<FileRecord> list = (List<FileRecord>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public FileRecord get(int id) {
        String query = String.format("FROM FileRecord WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<FileRecord> list = (List<FileRecord>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public List<FileRecord> listByDate(String from, String to) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query;
            if (!from.isEmpty() && !to.isEmpty()){
                query = session.createQuery("SELECT fr FROM FileRecord fr WHERE fr.fromtime >= :from AND fr.fromtime < :to ORDER BY fr.fromtime ASC");
                query.setParameter("from", from);
                query.setParameter("to", to);
            } else if (!from.isEmpty() && to.isEmpty()) {
                query = session.createQuery("SELECT fr FROM FileRecord fr WHERE fr.fromtime >= :from ORDER BY fr.fromtime ASC");
                query.setParameter("from", from);
            } else if (from.isEmpty() && !to.isEmpty()) {
                query = session.createQuery("SELECT fr FROM FileRecord fr WHERE fr.fromtime < :to ORDER BY fr.fromtime ASC");
                query.setParameter("to", to);
            } else
                query = session.createQuery("SELECT fr FROM FileRecord fr ORDER BY fr.fromtime ASC");
            
            
            List resultList = query.list();
            session.getTransaction().commit();
            return resultList;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    public List<FileRecord> listFileRecordBySensorAndDate(Sensors sensor, String from, String to) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("SELECT fr FROM FileRecord fr WHERE fr.sensor = :sensor AND fr.fromtime >= :from AND fr.fromtime < :to ORDER BY fr.fromtime ASC");
            query.setParameter("sensor", sensor);
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
    
    public List<FileRecord> listFileRecordByStationAndDate(Stations station, String from, String to) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("SELECT fr FROM FileRecord fr WHERE fr.sensor.station = :station AND fr.fromtime >= :from AND fr.fromtime < :to ORDER BY fr.fromtime ASC");
            query.setParameter("station", station);
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

    public List<FileRecord> listFileRecordsByIdAndDateTime(int id, Date fromtime, Date totime) throws ParseException {
        String query = String.format("FROM FileRecord Where SensorId =:id AND FromDatetime > :fromtime AND FromDatetime < :totime ORDER BY fr.fromtime ASC");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id), new Parameter("fromtime", fromtime), new Parameter("totime", totime)};
        List<FileRecord> listFileRecords = this.find(query, parameters);
        if (listFileRecords == null || listFileRecords.isEmpty()) {
            return null;
        }
        return listFileRecords;
    }
}
