/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.database.dao;

import com.attech.asd.amhs.database.entities.Flightplan;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

/**
 *
 * @author ANDH
 */
public class FlightPlanDao {

    public void updateDailyFlightPlan(Flightplan flightPlan) throws DBException {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	try {

//            messageAccount.setUpdatedDate(new Date());
	    // Start Transaction.            
	    session.getTransaction().begin();
	    NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM flightplan m WHERE m.dof = :dof AND m.callsign = :callsign", Flightplan.class);
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

    public void updateFlightPlan(Flightplan flightPlan) throws DBException {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	try {

	    // Start Transaction.            
	    session.getTransaction().begin();
	    NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM flightplan m WHERE m.dof = :dof AND m.callsign = :callsign", Flightplan.class);
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
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	try {

	    // Start Transaction.            
	    session.getTransaction().begin();
	    NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM flightplan m WHERE m.dof = :dof AND m.callsign = :callsign", Flightplan.class);
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
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	try {

	    // Start Transaction.            
	    session.getTransaction().begin();
	    NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM flightplan m WHERE m.dof = :dof AND m.callsign = :callsign", Flightplan.class);
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
}
