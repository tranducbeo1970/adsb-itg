/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.dao;

import com.attech.asd.database.common.DBException;
import com.attech.asd.database.Base;
import com.attech.asd.database.Parameter;
import com.attech.asd.database.entities.Flightplan;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author ANDH
 */
public class FlightPlanDao extends Base{

    public void updateDailyFlightPlan(Flightplan flightPlan) throws DBException {
	Session session = getSessionFactory().openSession();

	try {

//            messageAccount.setUpdatedDate(new Date());
	    // Start Transaction.            
	    session.getTransaction().begin();
	    Query nativeQuery = session.createQuery("SELECT * FROM Flightplan m WHERE m.dof = :dof AND m.callsign = :callsign");
	    nativeQuery.setParameter("dof", flightPlan.getDof());
	    nativeQuery.setParameter("callsign", flightPlan.getCallSign());
	    Flightplan existedFlightPlan = (Flightplan) nativeQuery.uniqueResult();

	    if (existedFlightPlan == null) {
		Date date = new Date();
		flightPlan.setCreatedDate(date);
		flightPlan.setUpdatedDate(date);
		session.save(flightPlan);
	    } else {
		existedFlightPlan.setDailyFplID(flightPlan.getDailyFplID());
		existedFlightPlan.setUpdatedDate(new Date());
		session.update(flightPlan);
	    }

	    // Commit data.
	    session.getTransaction().commit();

	} catch (Exception e) {
	    // Rollback in case of an error occurred.
	    session.getTransaction().rollback();
	    throw new DBException("DB operation fail.", e);
	}
    }

    public void updateFlightPlan(Flightplan flightPlan) throws DBException {
	Session session = getSessionFactory().openSession();

	try {

	    // Start Transaction.            
	    session.getTransaction().begin();
	    Query nativeQuery = session.createQuery("SELECT * FROM Flightplan m WHERE m.dof = :dof AND m.callsign = :callsign");
	    nativeQuery.setParameter("dof", flightPlan.getDof());
	    nativeQuery.setParameter("callsign", flightPlan.getCallSign());
	    Flightplan existedFlightPlan = (Flightplan) nativeQuery.uniqueResult();
	    Date date = new Date();

	    if (existedFlightPlan == null) {

		flightPlan.setCreatedDate(date);
		flightPlan.setUpdatedDate(date);
		session.save(flightPlan);
	    } else {
		existedFlightPlan.setCraft(flightPlan.getCraft());
		existedFlightPlan.setDep(flightPlan.getDep());
		existedFlightPlan.setDest(flightPlan.getDest());
		existedFlightPlan.setDof(flightPlan.getDof());
		existedFlightPlan.setEet(flightPlan.getEet());
		existedFlightPlan.setEtd(flightPlan.getEtd());
		existedFlightPlan.setReg(flightPlan.getReg());
		existedFlightPlan.setFplID(flightPlan.getFplID());
		existedFlightPlan.setUpdatedDate(date);
		existedFlightPlan.setRoute(flightPlan.getRoute());
		existedFlightPlan.setRemark(flightPlan.getRemark());
		if (existedFlightPlan.getEet() != null && existedFlightPlan.getAtd() != null) {
		    existedFlightPlan.setEta(Common.plusTime(existedFlightPlan.getAtd(), existedFlightPlan.getEet()));
		}
		session.update(existedFlightPlan);
	    }

	    // Commit data.
	    session.getTransaction().commit();

	} catch (Exception e) {
	    // Rollback in case of an error occurred.
	    session.getTransaction().rollback();
	    throw new DBException("DB operation fail.", e);
	}
    }

    public void updateDeparture(Flightplan flightPlan) throws DBException {
	Session session = getSessionFactory().openSession();

	try {

	    // Start Transaction.            
	    session.getTransaction().begin();
	    Query nativeQuery = session.createQuery("SELECT * FROM Flightplan m WHERE m.dof = :dof AND m.callsign = :callsign");
	    nativeQuery.setParameter("dof", flightPlan.getDof());
	    nativeQuery.setParameter("callsign", flightPlan.getCallSign());
	    Flightplan existedFlightPlan = (Flightplan) nativeQuery.uniqueResult();
	    Date date = new Date();

	    if (existedFlightPlan == null) {
		flightPlan.setCreatedDate(date);
		flightPlan.setUpdatedDate(date);
		session.save(flightPlan);
	    } else {
		existedFlightPlan.setAtd(flightPlan.getAtd());
		existedFlightPlan.setDepID(flightPlan.getDepID());
		existedFlightPlan.setUpdatedDate(date);
		if (existedFlightPlan.getEet() != null && existedFlightPlan.getAtd() != null) {
		    existedFlightPlan.setEta(Common.plusTime(existedFlightPlan.getAtd(), existedFlightPlan.getEet()));
		}
		session.update(existedFlightPlan);
	    }

	    // Commit data.
	    session.getTransaction().commit();

	} catch (Exception e) {
	    // Rollback in case of an error occurred.
	    session.getTransaction().rollback();
	    throw new DBException("DB operation fail.", e);
	}
    }

    public void updateArrival(Flightplan flightPlan) throws DBException {
	Session session = getSessionFactory().openSession();

	try {

	    // Start Transaction.            
	    session.getTransaction().begin();
	    Query nativeQuery = session.createQuery("SELECT * FROM Flightplan m WHERE m.dof = :dof AND m.callsign = :callsign");
	    nativeQuery.setParameter("dof", flightPlan.getDof());
	    nativeQuery.setParameter("callsign", flightPlan.getCallSign());
	    Flightplan existedFlightPlan = (Flightplan) nativeQuery.uniqueResult();
	    Date date = new Date();

	    if (existedFlightPlan == null) {
		flightPlan.setCreatedDate(date);
		flightPlan.setUpdatedDate(date);
		session.save(flightPlan);
	    } else {
		existedFlightPlan.setAta(flightPlan.getAta());
		existedFlightPlan.setArrID(flightPlan.getArrID());
		existedFlightPlan.setUpdatedDate(date);
		session.update(existedFlightPlan);
	    }

	    // Commit data.
	    session.getTransaction().commit();

	} catch (Exception e) {
	    // Rollback in case of an error occurred.
	    session.getTransaction().rollback();
	    throw new DBException("DB operation fail.", e);
	}
    }
    
    public List<Flightplan> getFlightPlans(String dof, String callsign){
        callsign = '%' + callsign + '%';
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Flightplan WHERE dof = :dof AND callSign LIKE :callsign");

            query.setParameter("dof", dof);
            query.setParameter("callsign", callsign);

            List<Flightplan> resultList = query.list();
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
    
    public Flightplan getById(int id){
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Flightplan WHERE id = :id");

            query.setParameter("id", id);

            List<Flightplan> resultList = query.list();
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
}
