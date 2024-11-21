/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;

import com.attech.asd.database.entities.AreaCoordinates;
import com.attech.asd.database.entities.Areas;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author NhuongND
 */
public class AreaCoordinatesDao  extends Base {
    public boolean clear() {
        return super.execute("DELETE FROM Areacoordinates;");
    }

    public synchronized boolean save(List<AreaCoordinates> areaCoordinates) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (AreaCoordinates coordinates : areaCoordinates) {
                session.saveOrUpdate(coordinates);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }
    
    public synchronized boolean save(AreaCoordinates areaCoordinates) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(areaCoordinates);
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public synchronized boolean remove(List<AreaCoordinates> areaCoordinates) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (AreaCoordinates areaCoordinate : areaCoordinates) {
                session.delete(areaCoordinate);
            }
            session.getTransaction().commit();
            return true;
        } finally {
            session.close();
        }
    }

    public List<AreaCoordinates> listAll() {
        String query = "FROM Areacoordinates";
        List<AreaCoordinates> list = (List<AreaCoordinates>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public AreaCoordinates get(int id) {
        String query = String.format("FROM AreaCoordinates WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<AreaCoordinates> list = (List<AreaCoordinates>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public List<AreaCoordinates> getCoordinateByAreaId(int AreaId) {
        String query = "FROM AreaCoordinates WHERE AreaId = :AreaId ";
        Parameter[] parameters = new Parameter[]{new Parameter("AreaId", AreaId)};
        List<AreaCoordinates> list = (List<AreaCoordinates>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
        
}
