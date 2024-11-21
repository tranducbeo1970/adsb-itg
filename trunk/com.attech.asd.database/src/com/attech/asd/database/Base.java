package com.attech.asd.database;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Base {

    static final Logger logger = Logger.getLogger(Base.class);

    private static SessionFactory sessionFactory;
    protected static Configuration configuration;

    /**
     * Set configure to connect to DB
     *
     * @param file
     */
    public static void configure(String file) {
        configuration = new Configuration();
        configuration.configure(new File(file));
    }

    public synchronized Session openSession() {
        return getSessionFactory(false).openSession();
    }

    public static void printOutProperties() {
        configuration.getProperties().forEach((k, v) -> {
            System.out.println(String.format("%s \t %s", k.toString(), v.toString()));
        });
    }

    public static String getDbUser() {
        return configuration.getProperty("hibernate.connection.username");
    }

    public static String getDbPassword() {
        return configuration.getProperty("hibernate.connection.password");
    }

    public static String getDbConnectString() {
        return configuration.getProperty("hibernate.connection.url");
        //jdbc:mysql://localhost:3306/aviation_surveillance_db?useSSL=false&useUnicode=yes&characterEncoding=UTF-8
    }

    public static void backupDb(String pathToSave) {
        try {
            Properties properties = new Properties();
            //properties.setProperty(MysqlExportService.DB_NAME, Base.getDbName());
            properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, Base.getDbConnectString());
            properties.setProperty(MysqlExportService.DB_USERNAME, Base.getDbUser());
            properties.setProperty(MysqlExportService.DB_PASSWORD, Base.getDbPassword());
            properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
            //set the outputs temp dir
            properties.setProperty(MysqlExportService.TEMP_DIR, pathToSave);
            MysqlExportService mysqlExportService = new MysqlExportService(properties);
            mysqlExportService.clearTempFiles(false);
            mysqlExportService.exportToFile();
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            //java.util.logging.Logger.getLogger(Base.class.getName()).log(Level.SEVERE, null, ex);
            logger.error(ex);
        }
    }

    /**
     * Create session factory to connect to DB
     *
     * @param refresh to create a new connection
     * @return
     */
    protected synchronized static SessionFactory getSessionFactory(boolean refresh) {
        if (refresh || sessionFactory == null) {
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                sessionFactory.close();
            }
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            /*
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).build();// buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
             */
        }

        return sessionFactory;
    }

    /**
     * Create session factory
     *
     * @return
     */
    protected synchronized SessionFactory getSessionFactory() {
        return getSessionFactory(false);
    }

    /**
     * Fetching database
     *
     * @param sql query clause
     * @return
     */
    protected List find(String sql) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery(sql);
            List resultList = q.list();
            session.getTransaction().commit();
            return resultList;

        } catch (HibernateException ex) {
            throw ex;
            // ExceptionHandler.handle(ex);
            // logger.error(ex);
            // return null;
        } finally {
            session.close();
        }
    }

    protected Long count(String sql) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery(sql);
            Long count = (Long) q.uniqueResult();
            session.getTransaction().commit();
            return count;
        } catch (HibernateException ex) {
            throw ex;
            // ExceptionHandler.handle(ex);
            // logger.error(ex);
            // return null;
        } finally {
            session.close();
        }
    }

    /**
     * Fetching database
     *
     * @param sql query clause
     * @param start
     * @return
     */
    protected List pagination(String sql, Integer start, Integer max) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery(sql);
            q.setFirstResult(start);
            q.setMaxResults(max);
            List resultList = q.list();
            session.getTransaction().commit();
            return resultList;

        } catch (HibernateException ex) {
            throw ex;
            // ExceptionHandler.handle(ex);
            // logger.error(ex);
            // return null;
        } finally {
            session.close();
        }
    }

    protected boolean execute(String sql) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query q = session.createSQLQuery(sql);
            q.executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (HibernateException ex) {
            throw ex;
            // ExceptionHandler.handle(ex);
            // logger.error(ex);
            // return null;
        } finally {
            session.close();
        }
    }

    /**
     * Fetching with limited result
     *
     * @param sql
     * @param maxResult
     * @return
     */
    protected List find(String sql, int maxResult) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(maxResult);
            List resultList = query.list();
            session.getTransaction().commit();
            return resultList;

        } catch (HibernateException ex) {
            throw ex;
            // ExceptionHandler.handle(ex);
            // logger.error(ex);
            // return null;
        } finally {
            session.close();
        }
    }

    /**
     * Fetching with parameter
     *
     * @param sql
     * @param parameters
     * @return
     */
    protected List find(String sql, Parameter[] parameters) {
        Session session = getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery(sql);

            if (parameters != null && parameters.length > 0) {
                for (Parameter p : parameters) {
                    q.setParameter(p.getKey(), p.getValue());
                }
            }

            List resultList = q.list();
            session.getTransaction().commit();
            return resultList;

        } catch (HibernateException ex) {
            // ExceptionHandler.handle(ex);
            // logger.error(ex);
            // return null;
            throw ex;
        } finally {
            session.close();
        }
    }

    /**
     * Query database with share session
     *
     * @param sql
     * @param session
     * @return
     */
    /*
    protected List find(String sql, Session session) {
        try {
            Query q = session.createQuery(sql);
            List resultList = q.list();
            return resultList;
        } catch (HibernateException ex) {
            ExceptionHandler.handle(ex);
            logger.error(ex);
            return null;
        }
    }
     */
    /**
     * Write object to database
     *
     * @param object
     * @return
     */
    public boolean save(Object object) {
        Session session = getSessionFactory().openSession();
        try {

            session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
            return true;

        } finally {
            session.close();
        }
    }
    
    public synchronized boolean remove(Object obj) {
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

    /**
     * Update object in database
     *
     * @param object
     */
    /*
    protected void udpate(Object object) {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            ExceptionHandler.handle(ex);
            logger.error(ex);
        }
    }
     */
    /**
     * Delete object from database
     *
     * @param object
     */
    protected boolean delete(Object object) {
        Session session = getSessionFactory().openSession();
        try {

            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    /**
     * Insert or delete object
     *
     * @param object
     */
    /*
    protected void saveOrUpdate(Object object) {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            ExceptionHandler.handle(ex);
            logger.error(ex);
        }
    }
     */
}
