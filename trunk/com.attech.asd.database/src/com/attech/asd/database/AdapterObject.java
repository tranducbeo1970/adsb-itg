/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;

import com.attech.asd.database.entities.*;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Tang Hai Anh
 */
public class AdapterObject extends Base {

    public synchronized boolean saveCrafts(List<Aircrafts> crafts) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Aircrafts craft : crafts) {
                session.saveOrUpdate(craft);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean saveCraft(Aircrafts craft) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(craft);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean removeCrafts(List<Aircrafts> crafts) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Aircrafts craft : crafts) {
                session.delete(craft);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean removeCraft(Aircrafts craft) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(craft);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public List<Client> getAllClient() {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List resultList = session.createQuery("FROM Client").list();
            session.getTransaction().commit();
            return resultList;
        } catch (HibernateException ex) {
            throw ex;
            //e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public Client getClientById(int id) {
        String query = String.format("FROM Client WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<Client> list = (List<Client>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public boolean isExistClient(int id) {
        String query = String.format("FROM Client WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<Client> list = (List<Client>) this.find(query, parameters);
        return !(list == null || list.isEmpty());
    }
    
    public boolean saveClient(Client client){
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(client);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex){
            return false;
        }
        finally {
            session.close();
        }
    }
    
    public synchronized boolean removeClient(Client client) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(client);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean removeSensor(Sensors sensor) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(sensor);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public Aircrafts getCraftById(int id) {
        String query = String.format("FROM Aircrafts WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<Aircrafts> list = (List<Aircrafts>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public Aircrafts getCraftByReg(String reg) {
        String query = String.format("FROM Aircrafts WHERE registrationNo = :reg ");
        Parameter[] parameters = new Parameter[]{new Parameter("reg", reg)};
        List<Aircrafts> list = (List<Aircrafts>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Aircrafts> getPagingCrafts(int pagenumber, int pagesize) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Aircrafts");
            query.setFirstResult((pagenumber - 1) * pagesize);
            query.setMaxResults(pagesize);
            List<Aircrafts> resultList = query.list();
            session.getTransaction().commit();
            if (resultList == null || resultList.isEmpty()) {
                return null;
            }
            return resultList;
        } catch (HibernateException ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public List<Aircrafts> getPagingCrafts(int pagenumber, int pagesize, String address, String regNo, String model, String operator) {
        Session session = getSessionFactory().openSession();
        try {
            address = '%' + address.trim() + '%';
            regNo = '%' + regNo.trim() + '%';
            model = '%' + model.trim() + '%';
            operator = '%' + operator.trim() + '%';
            session.beginTransaction();
            //Query query = session.createQuery("FROM Aircrafts");
            Query query = session.createQuery("FROM Aircrafts WHERE Icao24Address LIKE :address AND RegistrationNo LIKE :regNo AND Model LIKE :model AND Operator LIKE :operator ORDER BY Operator ASC");

            query.setParameter("address", address);
            query.setParameter("regNo", regNo);
            query.setParameter("model", model);
            query.setParameter("operator", operator);
            query.setFirstResult((pagenumber - 1) * pagesize);
            query.setMaxResults(pagesize);

            List<Aircrafts> resultList = query.list();
            session.getTransaction().commit();
            if (resultList == null || resultList.isEmpty()) {
                return null;
            }
            return resultList;
        } catch (HibernateException ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public int getAircraftSize(String address, String regNo, String model, String operator) {
        Session session = getSessionFactory().openSession();
        try {
            address = '%' + address.trim() + '%';
            regNo = '%' + regNo.trim() + '%';
            model = '%' + model.trim() + '%';
            operator = '%' + operator.trim() + '%';
            session.beginTransaction();
            //Query query = session.createQuery("FROM Aircrafts");
            Query query = session.createQuery("select count(*) FROM Aircrafts WHERE Icao24Address LIKE :address AND RegistrationNo LIKE :regNo AND Model LIKE :model AND Operator LIKE :operator");

            query.setParameter("address", address);
            query.setParameter("regNo", regNo);
            query.setParameter("model", model);
            query.setParameter("operator", operator);

            Long count = (Long) query.uniqueResult();
            session.getTransaction().commit();
            return count.intValue();
        } catch (HibernateException ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public synchronized boolean saveAirports(List<Airports> objs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Airports obj : objs) {
                session.saveOrUpdate(obj);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean saveAirport(Airports obj) {
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

    public synchronized boolean removeAirports(List<Airports> objs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Airports obj : objs) {
                session.delete(obj);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean removeAirport(Airports obj) {
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

    public synchronized boolean removeAirport(int id) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Airports obj = (Airports) session.load(Airports.class, id);
            session.delete(obj);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public int getAirportSize(String iata, String icao, String type) {
        Session session = getSessionFactory().openSession();
        try {
            iata = '%' + iata.trim() + '%';
            icao = '%' + icao.trim() + '%';
            type = '%' + type.trim() + '%';
            session.beginTransaction();
            Query query = session.createQuery("select count(*) FROM Airports WHERE Iata LIKE :iata AND Icao LIKE :icao AND Type LIKE :type");
            query.setParameter("iata", iata);
            query.setParameter("icao", icao);
            query.setParameter("type", type);

            Long count = (Long) query.uniqueResult();
            session.getTransaction().commit();
            return count.intValue();
        } catch (HibernateException ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public List<Airports> getPagingAirports(int pagenumber, int pagesize, String iata, String icao, String type) {
        Session session = getSessionFactory().openSession();
        try {
            iata = '%' + iata.trim() + '%';
            icao = '%' + icao.trim() + '%';
            type = '%' + type.trim() + '%';
            session.beginTransaction();
            Query query = session.createQuery("FROM Airports WHERE Iata LIKE :iata AND Icao LIKE :icao AND Type LIKE :type");

            query.setParameter("iata", iata);
            query.setParameter("icao", icao);
            query.setParameter("type", type);
            query.setFirstResult((pagenumber - 1) * pagesize);
            query.setMaxResults(pagesize);

            List<Airports> resultList = query.list();
            session.getTransaction().commit();
            if (resultList == null || resultList.isEmpty()) {
                return null;
            }
            return resultList;
        } catch (HibernateException ex) {
            throw ex;
        } finally {
            session.close();
        }
    }
    
    public Airports getAirportByIcao(String icao) {
        Session session = getSessionFactory().openSession();
        try {
            icao = '%' + icao.trim() + '%';
            session.beginTransaction();
            Query query = session.createQuery("FROM Airports WHERE Icao LIKE :icao");

            query.setParameter("icao", icao);

            List<Airports> resultList = query.list();
            session.getTransaction().commit();
            if (resultList == null || resultList.isEmpty()) {
                return null;
            }
            return resultList.get(0);
        } catch (HibernateException ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public boolean clearDailyAnalyzing() {
        return super.execute("DELETE FROM DailyAnalyzing;");
    }

    public synchronized boolean saveDailyAnalyzing(List<DailyAnalyzing> lstObjs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (DailyAnalyzing o : lstObjs) {
                session.saveOrUpdate(o);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean saveDailyAnalyzing(DailyAnalyzing obj) {
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

    public synchronized boolean removeDailyAnalyzing(List<DailyAnalyzing> lstObjs) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (DailyAnalyzing obj : lstObjs) {
                session.delete(obj);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public List<DailyAnalyzing> listAllDailyAnalyzing() {
        String query = "FROM DailyAnalyzing";
        List<DailyAnalyzing> list = (List<DailyAnalyzing>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public DailyAnalyzing getDailyAnalyzing(int id) {
        String query = String.format("FROM DailyAnalyzing WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<DailyAnalyzing> list = (List<DailyAnalyzing>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public DailyAnalyzing getDailyAnalyzing(String date, Sensors sensor) {
        String query = String.format("FROM DailyAnalyzing WHERE DateResult = :date AND SensorId = :sensor");
        Parameter[] parameters = new Parameter[]{new Parameter("date", date), new Parameter("sensor", sensor)};
        List<DailyAnalyzing> list = (List<DailyAnalyzing>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public synchronized boolean saveFileRecord(List<FileRecord> files) {
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

    public synchronized boolean saveFileRecord(FileRecord fileRecord) {
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

    public synchronized boolean saveFusedFileRecord(List<FusedFileRecord> files) {
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

    public synchronized boolean saveFusedFileRecord(FusedFileRecord fileRecord) {
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
    
    public void cleanFlightPlanBeforeDate(String date) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery("cleanFplBeforeDof");
            query.setParameter("dof", date);
            query.executeUpdate();
            //Long count = (Long) query.uniqueResult();
            session.getTransaction().commit();
            //return count.intValue();
        } catch (HibernateException ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

}
