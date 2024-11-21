/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database;

import com.attech.asd.database.entities.Aircrafts;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author anhth
 */
public class AircraftsDao extends Base {

    public boolean clear() {
        return super.execute("DELETE FROM aircrafts;");
    }

    public synchronized boolean save(List<Aircrafts> crafts) {
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

    public synchronized boolean remove(List<Aircrafts> crafts) {
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

    public List<Aircrafts> listCrafts() {
        String query = "FROM Aircrafts";
        List<Aircrafts> list = (List<Aircrafts>) this.find(query);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    public Aircrafts getAircraftByID(int id) {
        String query = String.format("FROM Aircrafts WHERE Id = :id ");
        Parameter[] parameters = new Parameter[]{new Parameter("id", id)};
        List<Aircrafts> list = (List<Aircrafts>) this.find(query, parameters);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<String> getAllModel() {
        String query = "SELECT distinct a.model FROM Aircrafts as a";
        return this.find(query);
    }

    public List<String> getOperatorByModel(String model) {
        String query = "SELECT distinct a.operator FROM Aircrafts as a where a.model=:model";
        Parameter[] parameters = new Parameter[]{new Parameter("model", model)};
        return this.find(query, parameters);
    }
     public List<String> getModelByOperator(String operator) {
        String query = "SELECT distinct a.model FROM Aircrafts as a where a.operator=:operator";
        Parameter[] parameters = new Parameter[]{new Parameter("operator", operator)};
        return this.find(query, parameters);
    }
    public List<String> getAllOperator() {
        String query = "SELECT distinct a.operator FROM Aircrafts as a ";
        return this.find(query);
    }
    
    public List<Aircrafts> getByRegistrationNo(String val) {
        String query = "FROM Aircrafts where RegistrationNo=:val";
        Parameter[] parameters = new Parameter[]{new Parameter("val", val)};
        return this.find(query, parameters);
    }
    
    public List<Aircrafts> getByAddress(String address) {
        String query = String.format("FROM Aircrafts WHERE Icao24Address = :address ");
        Parameter[] parameters = new Parameter[]{new Parameter("address", address)};
        return this.find(query, parameters);
    }

    public List<Aircrafts> filterModelOperator(String model, String operator) {
        String query = "";
        Parameter[] parameters;
        if (model.equals("") & operator.equals("")) {
            return listCrafts();
        } else {
            if (model.equals("")) {
                query = "FROM Aircrafts as a Where operator=:operator";
                parameters = new Parameter[]{new Parameter("operator", operator)};
            } else {
                if (operator.equals("")) {
                    query = "FROM Aircrafts as a Where model=:model";
                    parameters = new Parameter[]{new Parameter("model", model)};
                } else {
                    query = "FROM Aircrafts as a Where model=:model and operator=:operator";
                    parameters = new Parameter[]{new Parameter("model", model), new Parameter("operator", operator)};
                }
            }
        }
        return this.find(query, parameters);
    }
}
