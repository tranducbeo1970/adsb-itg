/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.database.dao;

import com.attech.asd.amhs.database.entities.Config;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

/**
 *
 * @author ANDH
 */
public class ConfigDao {

    public String getConfig(String name) throws DBException {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	try {

	    // Start Transaction.            
	    session.getTransaction().begin();

	    // Create Query object.
	    NativeQuery<Config> query = session.createNativeQuery("SELECT * FROM config WHERE ParamName = :name", Config.class);
	    query.setParameter("name", name);

	    Config config = query.uniqueResult();

	    // Commit data.
	    session.getTransaction().commit();
	    return config == null ? null : config.getParamValue();

	} catch (Exception e) {
	    session.getTransaction().rollback();
	    throw new DBException("DB operation fail.", e);
	}
    }
}
